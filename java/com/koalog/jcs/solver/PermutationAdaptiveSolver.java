package com.koalog.jcs.solver;

import org.apache.log4j.Category;
import java.util.Iterator;
import com.koalog.jcs.domain.MinMaxDomain;
import com.koalog.jcs.domain.Domain;
import com.koalog.jcs.constraint.AbstractRelation;
import com.koalog.jcs.constraint.Problem;

/**
 * A default adaptive solver super class for solving permutation problems.
 *
 * <P>In the case of permutation problems, 
 * a move can be defined as the swap of two variables.
 *
 * @since 2.3
 * @author Yan Georget
 */
public abstract class PermutationAdaptiveSolver extends AdaptiveSolver {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
       Category.getInstance(PermutationAdaptiveSolver.class);

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.    
     * @param problem a permutation problem
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
    public PermutationAdaptiveSolver(Problem problem,
                                     long maxRestarts,
                                     long maxIterations,
                                     Object[][] errFcts,
                                     int tabuTenure,
                                     int tabuMaxSize,
                                     int moveNb,
                                     float plateauProbability) {
        super(problem,
              maxRestarts,
              maxIterations,
              errFcts,
              tabuTenure,
              tabuMaxSize,
              moveNb,
              plateauProbability);

    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.solver.AdaptiveSolver */
    protected void randomInit() {
        for (int i=variables.length; --i>=0;) {
            variables[i].setDomain(new MinMaxDomain(i));
        }
        randomMove(variables.length);
    }
    
    /** @see com.koalog.jcs.solver.AdaptiveSolver */
    protected void randomMove(int nb) {
        while (nb>0) {
            int vi = (int) (Math.random() * variables.length);
            int vj = (int) (Math.random() * variables.length);
            if (vi != vj) {
                nb--;
                swap(vi, vj);
            } 
        }
        for (Iterator i = getRelations().iterator(); i.hasNext();) {
            AbstractRelation r = (AbstractRelation) i.next();
            errorsByRelations.put(r, new Integer(getRelationError(r)));
        }
        updateVariableErrors();
        updateError();
    }
     
    /** @see com.koalog.jcs.solver.AdaptiveSolver */
    protected boolean improvingMove(int var) {
        int bestDiff = 0;
        int bestMove = -1;
        for (int i=0; i<variables.length; i++) {
            if (i != var) {
                int diff = getErrorDiffWhenSwapping(i, var);
                if (diff <= bestDiff) {
                    bestDiff = diff;
                    bestMove = i;
                }
            }
        }
        if (bestMove != -1) { 
            if (bestDiff != 0 || Math.random() < plateauProbability) {
                updateVariableErrorsWhenSwapping(bestMove, var);
                error += bestDiff;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Updates the projected errors 
     * when swapping two variables.
     * @param vi the index of a variable
     * @param vj the index of a variable
     */
    protected void updateVariableErrorsWhenSwapping(int vi, int vj) {
        swap(vi, vj);
        for (Iterator i = relationsByVariables[vi].iterator(); 
             i.hasNext();) {
            AbstractRelation r = (AbstractRelation) i.next();
            errorsByRelations.put(r, new Integer(getRelationError(r)));
        }
        for (Iterator i = relationsByVariables[vj].iterator(); 
             i.hasNext();) {
            AbstractRelation r = (AbstractRelation) i.next();
            if (!constrains(r, vi)) {
                errorsByRelations.put(r, new Integer(getRelationError(r)));
            }
        }
        updateVariableErrors();
    }

    /**
     * Swaps the domains of two variables.
     * @param vi the index of a variable
     * @param vj the index of a variable
     */
    protected void swap(int vi, int vj) {
        Domain tmp = variables[vi].getDomain();
        variables[vi].setDomain(variables[vj].getDomain());
        variables[vj].setDomain(tmp);
    }

    //------------------------------------------------------------------------
    // ABSTRACT METHODS
    //------------------------------------------------------------------------
    /**
     * Returns the difference between 
     * the new global error and the current global error 
     * if swapping two variables.
     * @param vi the index of a variable
     * @param vj the index of a variable
     * @return an int
     */
    protected abstract int getErrorDiffWhenSwapping(int vi, int vj);
}
/*
 * $Log$
 * Revision 1.8  2004/11/05 15:35:06  yan
 * added probability to stay on a plateau
 *
 * Revision 1.7  2004/11/02 15:00:02  yan
 * caching calls to constrains method
 *
 * Revision 1.6  2004/11/02 13:54:28  yan
 * optimized for loops
 *
 * Revision 1.5  2004/10/20 18:06:31  yan
 * optimized
 *
 * Revision 1.4  2004/10/12 14:02:35  yan
 * various refactoring
 *
 * Revision 1.3  2004/10/11 15:36:46  yan
 * using long instead of int in constructor
 *
 * Revision 1.2  2004/10/11 15:06:05  yan
 * reusing randomMove in randomInit
 *
 * Revision 1.1  2004/10/11 12:11:56  yan
 * initial revision
 *
 */


