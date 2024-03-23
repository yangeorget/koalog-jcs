package com.koalog.jcs.solver;

import org.apache.log4j.Category;
import java.util.Locale;
import com.koalog.util.I18N;
import com.koalog.jcs.TimeOutException;
import com.koalog.jcs.InconsistencyException;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.domain.Domain;
import com.koalog.jcs.variable.Variable;

/**
 * An abstract class for local search.
 * This type of solver is correct but not complete.
 *
 * <P>This type of solvers finds at most one solution:
 * <BLOCKQUOTE><PRE>
 * solver.solve(n);
 * </PRE></BLOCKQUOTE>
 * is thus equivalent to:
 * <BLOCKQUOTE><PRE>
 * solver.solve();
 * </PRE></BLOCKQUOTE>
 *
 * <P>A local search solver defines the following counters:
 * <UL>
 * <LI>the number of <CODE>restarts</CODE>,
 * <LI>the number of <CODE>iterations</CODE>,
 * <LI>the number of local <CODE>minima</CODE>,
 * <LI>the number of partial <CODE>resets</CODE>.
 * </UL>
 *
 * @since 2.3
 * @author Yan Georget
 */
public abstract class LocalSearchSolver extends AbstractSolver {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
       Category.getInstance(LocalSearchSolver.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------ 
    /** The maximal number of restarts allowed. */
    long maxRestarts;
    /** The maximal number of iterations allowed per restart. */
    long maxIterations;
    /** The number of iterations. */
    long iterations;
    /** The number of restarts. */
    long restarts;
    /** The number of resets. */
    long resets;
    /** The number of minima. */
    long minima;
    Domain[] staticDomains;
    /** The problem variables. */
    protected Variable[] variables;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------ 
    /**
     * Constructs a solver for a given problem.
     * @param problem the problem to be solved
     * @param maxRestarts the maximal number of restarts allowed
     * @param maxIterations the maximal number of iterations allowed 
     * per restart
     */
    public LocalSearchSolver(Problem problem,
                             long maxRestarts,
                             long maxIterations) {
        super(problem);
        this.maxRestarts = maxRestarts;
        this.maxIterations = maxIterations;
        variables = problem.getVariables();

    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** 
     * Finds at most one solution.
     * @see com.koalog.jcs.solver.AbstractSolver 
     */
    public void findSolutions(int max, long timeOut) 
        throws InconsistencyException, TimeOutException {
        staticDomains = getDomains();
        for (restarts=0; restarts<maxRestarts; restarts++) {
            initRestart();
            if (search(timeOut)) {
                return;
            }
        }
    }
 
    /** @see com.koalog.jcs.solver.Solver */
    public void initCounters() {
        super.initCounters();
        resets = minima = iterations = restarts = 0;
    }

    /** @see com.koalog.jcs.solver.Solver */
    public void resetCounters() {
        super.resetCounters();
        resets = minima = iterations = restarts = 0;
    }

    /** @see com.koalog.jcs.solver.Solver */
    public void solved() {
        super.solved();
        Locale locale = Locale.getDefault();
        StringBuffer buf = new StringBuffer();
        buf.append(restarts);
        buf.append(" ");
        buf.append(I18N.getString(I18N_RESOURCE, 
                                  "RESTARTS", 
                                  locale));
        buf.append(", "); 
        buf.append(iterations);
        buf.append(" ");
        buf.append(I18N.getString(I18N_RESOURCE, 
                                  "ITERATIONS", 
                                  locale));
        buf.append(", "); 
        buf.append(minima); 
        buf.append(" ");  
        buf.append(I18N.getString(I18N_RESOURCE, 
                                  "MINIMA", 
                                  locale));
        buf.append(", "); 
        buf.append(resets); 
        buf.append(" ");  
        buf.append(I18N.getString(I18N_RESOURCE, 
                                  "RESETS", 
                                  locale)); 
        cat.info(buf);
    }
    
    /** @see com.koalog.jcs.solver.AbstractSolver */
    public void reset() {
        for (int i=variables.length; --i>=0;) {
            variables[i].setDomain(staticDomains[i]);
        }
    }

    //------------------------------------------------------------------------
    // ABSTRACT METHODS
    //------------------------------------------------------------------------
    /**
     * An initialization method called at each restart.
     */
    protected abstract void initRestart();

    /** 
     * A method for improving the current solution 
     * by performing at most <CODE>maxIterations</CODE> iterations.
     * 
     * <P>Updates the global iterations counter.
     *
     * @param timeOut a long
     * @return true if a solution has been found
     * @throws TimeOutException when a time-out occurs
     */
    protected abstract boolean search(long timeOut) throws TimeOutException;
}
/*
 * $Log$
 * Revision 1.10  2005/07/22 12:03:05  yan
 * made findSolutions public
 *
 * Revision 1.9  2005/07/19 07:40:38  yan
 * real anytime constraint solving
 *
 * Revision 1.8  2005/07/18 17:36:50  yan
 * fixed style
 *
 * Revision 1.7  2005/07/18 17:26:08  yan
 * introduced TimeOutException + various simplifications
 *
 * Revision 1.6  2004/11/02 13:54:28  yan
 * optimized for loops
 *
 * Revision 1.5  2004/10/12 15:42:14  yan
 * added jdoc
 *
 * Revision 1.4  2004/10/12 14:02:35  yan
 * various refactoring
 *
 * Revision 1.3  2004/10/11 16:20:14  yan
 * refactored
 *
 * Revision 1.2  2004/10/11 15:36:03  yan
 * using long instead of int
 *
 * Revision 1.1  2004/10/11 15:05:03  yan
 * initial revision
 *
 */
