package com.koalog.jcs.solver;

import org.apache.log4j.Category;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import com.koalog.jcs.TimeOutException;
import com.koalog.jcs.constraint.AbstractRelation;
import com.koalog.jcs.constraint.Problem;

/**
 * An abstract class for adaptive local search.
 *
 * <P>The algorithm used here is based on the one of Codognet & Diaz.
 * It consists in optimizing a global error function. 
 * At any moment, all the problem variables are instantiated.
 * For each constraint, an error is computed. 
 * Constraints errors are then projected on variables. 
 * The variable with the greatest error will be a candidate for a move 
 * (modification of the values of the variables). 
 * In a min-conflict spirit, 
 * the best move (according to the global error) is selected.
 * If not improving move is possible (local minimum), 
 * then the variable is marked tabu for <CODE>tabuTenure</CODE> iterations.
 * When <CODE>tabuMaxSize</CODE> variables are tabu, 
 * a local reset (<CODE>moveNb</CODE> moves) is performed.
 * See <CITE>Yet Another Local Search Method for Constraint Solving</CITE>, 
 * for a precise description of the algorithm. 
 *
 * <P>Concrete subclasses of this abstract class 
 * should define moves and projected errors (errors on variables).
 * For each variable, 
 * the corresponding projected error is obtained by 
 * the aggregation of the errors of the relations constraining the variable.
 * For each relation, the corresponding error is obtained by computing 
 * an error function (which has to be defined by the user).
 *
 * @since 2.3
 * @author Yan Georget
 */
public abstract class AdaptiveSolver extends LocalSearchSolver {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(AdaptiveSolver.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------ 
    /** The number of iterations a variable can remain tabu. */
    protected int tabuTenure;
    /** The maximal number of tabu variables allowed before resetting. */
    protected int tabuMaxSize;
    /** The number of moves to perform when resetting. */
    protected int moveNb;
    /** The probability to stay on a plateau 
        (to perform a non-improving move). */ 
    protected float plateauProbability;
    /** The collection of the problem relations 
        for which an error function is defined. */
    private Collection relations;
    /** The relations by variables. */
    protected Collection[] relationsByVariables;
    /** The global error. */
    protected int error;
    /** The errors by variables. */
    protected int[] errorsByVariables;
    /** The errors by relations. */
    protected Map errorsByRelations;
    /** The errors functions by relations. */
    protected Map errorFcts;
    /** The dates until which the variables are tabu. */
    long[] tabu; 
    /** An array of arrays of booleans (indexed by relations and variables)
        indicating if a relation constrains a variable. */
    boolean[][] constrains;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------ 
    /**
     * Constructs a solver for a given problem.
     * @param problem the problem to be solved
     * @param maxRestarts the maximal number of restarts allowed
     * @param maxIterations the maximal number of iterations allowed 
     * per restart
     * @param errFcts an array of constraint class, error function pairs
     * @param tabuTenure the number of iterations a variable can remain tabu
     * @param tabuMaxSize the maximal number of tabu variables allowed 
     * before resetting
     * @param moveNb the number of moves to perform when resetting
     * @param plateauProbability the probability to stay on a plateau 
     * (to perform a non-improving move) 
     */
    public AdaptiveSolver(Problem problem,
                          long maxRestarts,
                          long maxIterations,
                          Object[][] errFcts,
                          int tabuTenure,
                          int tabuMaxSize,
                          int moveNb,
                          float plateauProbability) {
        super(problem, maxRestarts, maxIterations);
        this.tabuTenure = tabuTenure;
        this.tabuMaxSize = tabuMaxSize;
        this.moveNb = moveNb;
        this.plateauProbability = plateauProbability;
        errorsByVariables = new int[variables.length];
        errorsByRelations = new HashMap(errFcts.length);
        errorFcts = new HashMap(errFcts.length);
        for (int i=0; i<errFcts.length; i++) {
            errorFcts.put(errFcts[i][0], errFcts[i][1]);
        }
        relations = new ArrayList(errFcts.length);
        for (Iterator i = problem.getRelations().iterator(); i.hasNext();) {
            AbstractRelation c = (AbstractRelation) i.next();   
            if (errorFcts.keySet().contains(c.getClass())) {
                relations.add(c);
            }
        }
        relationsByVariables = new ArrayList[variables.length];
        for (int i=0; i<variables.length; i++) {
            relationsByVariables[i] = new ArrayList();
        }
        for (Iterator i = getRelations().iterator(); i.hasNext();) {
            AbstractRelation c = (AbstractRelation) i.next();
            for (int j=0; j<variables.length; j++) {
                if (c.constrains(variables[j])) {
                    relationsByVariables[j].add(c);
                }
            }
        }
        constrains = 
            new boolean[problem.getRelations().size()+1][variables.length];
        for (Iterator i = relations.iterator(); i.hasNext();) {
            AbstractRelation r = (AbstractRelation) i.next();
            for (int j=0; j<variables.length; j++) {
                constrains[r.getUnid()][j] = r.constrains(variables[j]);
            } 
        }
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Returns a boolean indicating if a relation constrains a variable.
     * @param r a relation
     * @param i a variable index
     * @return a boolean
     */
    protected boolean constrains(AbstractRelation r, int i) {
        return constrains[r.getUnid()][i];
    }

    /** @see com.koalog.jcs.solver.LocalSearchSolver */
    protected boolean search(long timeOut) throws TimeOutException {
        for (long it=1; it<=maxIterations;) {
            cat.debug("iteration " + it);
            //dump();
            if (error == 0) {
                storeSolution();
                return true;
            }
            int var = -1;
            int err = Integer.MIN_VALUE;
            int tabuSize = 0;
            for (int i=variables.length; --i>=0;) {
                if (tabu[i] < it) { // not tabu
                    int erri = Math.abs(errorsByVariables[i]);
                    if (erri > err) {
                        err = erri;
                        var = i;
                    }
                } else {
                    tabuSize++;
                }
            }
            if (improvingMove(var)) {
                it++;
                iterations++;
            } else {
                cat.debug("local minimum");
                minima++;
                // marked as tabu for tabuTenure iterations
                tabu[var] = it + tabuTenure;  
                if  (tabuSize + 1 >= tabuMaxSize) {
                    cat.debug("tabu size exceeded");
                    resets++;
                    randomMove(moveNb);
                    for (int i=variables.length; --i>=0;) {
                        tabu[i] = 0;
                    }
                    it += moveNb;
                    iterations += moveNb;
                } 
            }
            if (System.currentTimeMillis() > timeOut) {
                throw new TimeOutException();
            }
        }
        if (error == 0) {
            storeSolution();
            return true;
        } else {
            return false;
        }
    }
    
    /** @see com.koalog.jcs.solver.LocalSearchSolver */
    protected void initRestart() {
        cat.debug("restart " + restarts);
        tabu = new long[variables.length];
        randomInit();
    }

    /**
     * Returns the relations for which an error function has been defined.
     * @return a collection
     */
    public Collection getRelations() {
        return relations;
    }
   
    /**
     * Gets the cached error for a relation.
     * @param r a relation
     * @return an int
     */
    public int getRelationError(AbstractRelation r) {
        return ((ErrorFct) errorFcts.get(r.getClass())).error(r);
    }

    /**
     * Updates the errors for the variables.
     */
    public void updateVariableErrors() {
        for (int i=variables.length; --i>=0;) {
            updateVariableError(i);
        }
    }

    /**
     * Updates the global error.
     *
     * <P>This implementation 
     * (which can of course be overridden) 
     * computes the sum 
     * of the absolute values of the errors of the relations.
     */
    public void updateError() {
        error = 0;
        for (Iterator i = getRelations().iterator(); i.hasNext();) {
            error += Math.abs(((Integer) errorsByRelations.get(i.next()))
                              .intValue());
        }
    }

    void dump() {
        for (Iterator i = getRelations().iterator(); i.hasNext();) {
            AbstractRelation r = (AbstractRelation) i.next();
            cat.debug(r + "="+ errorsByRelations.get(r));
        }
        for (int i=0; i<variables.length; i++) {
            cat.debug(variables[i] + "=" + errorsByVariables[i]);
        }
        cat.debug("error=" + error);
    }

    //------------------------------------------------------------------------
    // ABSTRACT METHODS
    //------------------------------------------------------------------------
    /**
     * Updates the projected error for a variable.
     * @param i the index of the variable
     */
    protected abstract void updateVariableError(int i);
    
    /**
     * Randomly inits some variables.
     *
     * <P>This method should update all the errors: 
     * at least the global error and the projected errors.
     */
    protected abstract void randomInit();

    /**
     * Randomly performs <CODE>nb</CODE> moves.
     * @param nb the number of moves to perform
     *
     * <P>This method should update all the errors: 
     * at least the global error and the projected errors.
     */
    protected abstract void randomMove(int nb);

    /**
     * Tries to improve the global error by exploring 
     * a neighborhood of the selected variable.
     *
     * <P>This method should update all the errors: 
     * at least the global error and the projected errors.
     *
     * @param var a variable index
     * @return a boolean indicating if it was possible to improve the error
     */
    protected abstract boolean improvingMove(int var);


    //------------------------------------------------------------------------
    // INNER CLASSES
    //------------------------------------------------------------------------
    /**
     * An encapsulation of an error function.
     */
    public static interface ErrorFct {
        /**
         * Returns an error.
         * @param relation a relation of the correct type
         * @return an int
         */
        public int error(AbstractRelation relation);
    }
}
/*
 * $Log$
 * Revision 1.12  2005/07/18 17:26:08  yan
 * introduced TimeOutException + various simplifications
 *
 * Revision 1.11  2004/11/10 15:16:28  yan
 * optimized structures creation
 *
 * Revision 1.10  2004/11/05 15:34:37  yan
 * added probability to stay on a plateau
 *
 * Revision 1.9  2004/11/02 15:00:02  yan
 * caching calls to constrains method
 *
 * Revision 1.8  2004/11/02 13:54:28  yan
 * optimized for loops
 *
 * Revision 1.7  2004/10/20 18:06:09  yan
 * optimized
 *
 * Revision 1.6  2004/10/12 15:40:14  yan
 * added jdoc
 *
 * Revision 1.5  2004/10/12 14:02:35  yan
 * various refactoring
 *
 * Revision 1.4  2004/10/11 16:20:14  yan
 * refactored
 *
 * Revision 1.3  2004/10/11 15:34:59  yan
 * fixed jdoc
 *
 * Revision 1.2  2004/10/11 15:09:46  yan
 * fixed counters, extends LocalSearchSolver
 *
 * Revision 1.1  2004/10/11 12:11:02  yan
 * initial revision
 *
 */
