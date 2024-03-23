package com.koalog.jcs.solver;
  
import com.koalog.jcs.BacktrackInconsistencyException;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.InconsistencyException;
import com.koalog.jcs.TimeOutException;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.choicepoint.PrunableChoicePointStack;
import com.koalog.jcs.constraint.ComplexityConstraintScheduler;
import com.koalog.jcs.constraint.Constraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
//import com.koalog.jcs.constraint.LazyComplexityConstraintScheduler;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.variable.MinMaxVariable;

import com.koalog.util.I18N;

import java.util.Iterator;
import java.util.Locale;

import org.apache.log4j.Category;

/** 
 * <P><CODE>BacktrackSolver</CODE> is the abstract class of solvers that know:
 * <UL> 
 * <LI> how to backtrack, which means that they know how to explore choices 
 * (implemented by the <CODE>ChoicePoint</CODE> class),
 * <LI> how to schedule the constraints that need to be filtered.
 * </UL>
 *
 * <P>Subclasses of <CODE>BacktrackSolver</CODE> implement the abstract 
 * <CODE>choice</CODE> method. 
 *
 * <P>Backtrack solvers have additional counters that deal with 
 * the number of backtracks used to find the solutions.</P>
 *
 * @author Yan Georget
 * @see com.koalog.jcs.choicepoint.ChoicePoint
 * @see ChoicePointStack 
 * @see ConstraintScheduler
 */
public abstract class BacktrackSolver extends BaseSolver {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(BacktrackSolver.class);
    private static final boolean DEBUG = cat.isDebugEnabled();

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The stack of the choice points created during the resolution. */
    protected ChoicePointStack choicePoints;
    /** The constraint scheduler used to schedule 
        the constraints to be filtered. */
    protected ConstraintScheduler constraintScheduler;
    /** The total number of filters 
        for finding all the solutions of the problem. */
    protected long totalFiltersNb;
    /** The number of filters for finding the current solution. */
    protected long filtersNb;
    /** The total number of backtracks 
        for finding all the solutions of the problem. */
    protected long totalBacktracksNb;
    /** The number of backtracks for finding the current solution. */
    protected long backtracksNb;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Creates a BacktrackSolver from a Problem.
     * @param problem the Problem 
     */
    public BacktrackSolver(Problem problem) {
        super(problem);
        choicePoints = new PrunableChoicePointStack();
        problem.flattenConstraints();
        problem.updateDependencies();
        // constraintScheduler = new LazyComplexityConstraintScheduler();
        constraintScheduler = new ComplexityConstraintScheduler();
        cat.debug("solver created");
    }

    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------
    /**
     * Returns the choice point stack. 
     * @return a choice point stack
     */
    public final ChoicePointStack getChoicePoints() {
        return choicePoints;
    }

    /**
     * Sets the constraint scheduler.
     * @param constraintScheduler a constraint scheduler
     */
    public final void 
        setConstraintScheduler(ConstraintScheduler constraintScheduler) {
        this.constraintScheduler = constraintScheduler;
    }
    
    /**
     * Gets the constraint scheduler.
     * @return a constraint scheduler
     */
    public final ConstraintScheduler getConstraintScheduler() {
        return constraintScheduler;
    }

    //------------------------------------------------------------------------
    // ABSTRACT METHODS
    //------------------------------------------------------------------------ 
    /**
     * Makes a choice.
     * @return a boolean indicating if a choice has been made
     */    
    public abstract boolean choice();

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------ 
    /** @see com.koalog.jcs.solver.Solver */
    public void reset() {
        constraintScheduler.clear();
        choicePoints.reset(constraintScheduler);
    }

    /** @see com.koalog.jcs.solver.AbstractSolver */
    public void findSolutions(int max, long timeOut) 
        throws InconsistencyException, TimeOutException {
        initialFilter();
        for (;;) {
            search(timeOut); 
            if (getSolutions().size() >= max) {
                return;
            } 
            backtrack(); // looking for another solution
        }
    }
 
    /**
     * Performs the initial filtering of the problem 
     * (filtering all its constraints).
     *
     * @throws ConstraintInconsistencyException when there is no solution
     * @see com.koalog.jcs.constraint.Constraint
     */
    public void initialFilter() throws ConstraintInconsistencyException {
        constraintScheduler.clear();
        for (Iterator i = problem.getConstraints().iterator(); i.hasNext();) {
            final Constraint c = (Constraint) i.next();
            c.init(choicePoints, constraintScheduler);
            constraintScheduler.add(choicePoints, c);
        }
        filter();
    }

    /**
     * Filters the problem according to the constraint scheduler.
     *
     * <P>By default, calls <CODE>consistencyFilter</CODE>.
     *
     * @throws ConstraintInconsistencyException when there is no solution
     */
    public void filter() throws ConstraintInconsistencyException {
        consistencyFilter();
    }

    /**
     * Enforces consistency.
     * @throws ConstraintInconsistencyException when there is no solution
     */
    public final void consistencyFilter() 
        throws ConstraintInconsistencyException {
        Constraint c;
        while ((c = constraintScheduler.pop(choicePoints)) != null) {
            if (DEBUG) {
                cat.debug("filtering " + c);
            }
            c.filter(choicePoints, constraintScheduler);
            filtersNb++;
        }
    }

    /**
     * Shaves the minimal and the maximal values of a variable. 
     *
     * <P>Usually used by overidding the <CODE>filter</CODE> method:
     * <PRE>
     * public void filter() throws ConstraintInconsistencyException {
     *    super.filter();
     *    // shave some variables here
     * }
     * </PRE>
     * @param var a <CODE>MinMaxVariable</CODE>
     * @param deep true for deep shaving, false otherwise
     * @throws ConstraintInconsistencyException when there is no solution 
     * @since 3.1
     */
    public void shaveMinMax(MinMaxVariable var, boolean deep) 
        throws ConstraintInconsistencyException {
        if (var.isNotInstantiated()) {
            var.shaveMin(this, deep);
            var.shaveMax(this, deep);
        }
    }

    /**
     * Shaves the minimal and the maximal values of some variables.
     * @param vars a <CODE>MinMaxVariable</CODE> array
     * @param deep true for deep shaving, false otherwise
     * @throws ConstraintInconsistencyException when there is no solution 
     */
    public void shaveMinMax(MinMaxVariable[] vars, boolean deep) 
        throws ConstraintInconsistencyException {
        for (int i=0; i<vars.length; i++) {
            final MinMaxVariable var = vars[i];
            if (var.isNotInstantiated()) {
                var.shaveMin(this, deep);
                var.shaveMax(this, deep);
            }
        }
    }

    /**
     * Posts a constraint.
     * @param constraint a constraint
     * @throws ConstraintInconsistencyException when there is no solution
     * @since 2.3
     */
    public void post(Constraint constraint) 
        throws ConstraintInconsistencyException {
        problem.add(constraint);
        problem.flattenConstraints(constraint);
        constraint.updateDependencies();
        constraintScheduler.clear();
        constraint.init(choicePoints, constraintScheduler);
        constraintScheduler.add(choicePoints, constraint);
        filter();
    }

    /**
     * Searches for a solution (making choices).
     * @param timeOut the time-out in ms
     * @throws InconsistencyException when there is no solution
     * @throws TimeOutException when a time-out occurs
     */
    public void search(long timeOut) 
        throws InconsistencyException, TimeOutException {
        if (DEBUG) {
            cat.debug("entering search");
        }
        while(choice()) {
            if (System.currentTimeMillis() > timeOut) {
                throw new TimeOutException();
            }
            try { 
                filter();
            } catch (ConstraintInconsistencyException ie) {
                backtrack();
            }
        }
        solutionFound();
        resetCounters();
        if (DEBUG) {
            cat.debug("exiting search");
        }
    }

    /**
     * Backtracks to the previous consistent state.
     * @throws BacktrackInconsistencyException when is it not possible 
     * to backtrack anymore
     */
    public void backtrack() throws BacktrackInconsistencyException {
        while(!choicePoints.isEmpty()) {
            try {
                backtracksNb++;
                constraintScheduler.clear();
                choicePoints.pop(constraintScheduler);
                filter();
                return;
            } catch (ConstraintInconsistencyException ie) {
                cat.debug("inconsistent choice point");
            }
        }
        constraintScheduler.clear();
        throw new BacktrackInconsistencyException();
    }
    

    /** @see com.koalog.jcs.solver.Solver */
    public void resetCounters() {
        super.resetCounters();
        totalFiltersNb += filtersNb;
        totalBacktracksNb += backtracksNb;
        filtersNb = backtracksNb = 0;
    }
        
    /** @see com.koalog.jcs.solver.Solver */
    public void initCounters() {
        super.initCounters();
        totalFiltersNb = filtersNb = totalBacktracksNb = backtracksNb = 0;
    }

   /**
     * Converts the solver to a string.
     * @return a string
     */
    public String toString() {
        StringBuffer buf = new StringBuffer(super.toString());
        buf.append(choicePoints);
        return buf.toString();
    }

    /** @see com.koalog.jcs.solver.BaseSolver */
    public void solutionFound() {
        super.solutionFound();
        Locale locale = Locale.getDefault();
        StringBuffer buf = new StringBuffer();
        buf.append(backtracksNb);
        buf.append(" "); 
        buf.append(I18N.getString(I18N_RESOURCE, 
                                  "BACKTRACKS", 
                                  locale));
        buf.append(", "); 
        buf.append(filtersNb); 
        buf.append(" "); 
        buf.append(I18N.getString(I18N_RESOURCE, 
                                  "FILTERS", 
                                  locale));
        buf.append(", "); 
        buf.append(I18N.getString(I18N_RESOURCE, 
                                  "MAX_DEPTH", 
                                  locale)); 
        buf.append(" "); 
        buf.append(choicePoints.maxDepth());
        cat.info(buf);
    }

    /** @see com.koalog.jcs.solver.Solver */
    public void solved() {
        super.solved();
        Locale locale = Locale.getDefault();
        StringBuffer buf = new StringBuffer();
        buf.append(totalBacktracksNb);
        buf.append(" ");
        buf.append(I18N.getString(I18N_RESOURCE, 
                                  "BACKTRACKS_IN_TOTAL", 
                                  locale));
        buf.append(", "); 
        buf.append(totalFiltersNb); 
        buf.append(" ");  
        buf.append(I18N.getString(I18N_RESOURCE, 
                                  "FILTERS_IN_TOTAL", 
                                  locale));
        buf.append(", "); 
        buf.append(I18N.getString(I18N_RESOURCE, 
                                  "MAX_DEPTH", 
                                  locale)); 
        buf.append(" ");  
        buf.append(choicePoints.maxDepth());
        cat.info(buf);
    }
}
