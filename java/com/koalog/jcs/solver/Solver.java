package com.koalog.jcs.solver;

import com.koalog.jcs.constraint.Problem;

/**
 * The <CODE>Solver</CODE> interface is the root of the solver hierarchy. 
 * All solvers implement the methods of this interface.
 *
 * <P>A solver is responsible for finding solutions to a problem.
 * Requesting a solver to solve its associated problem is achieved by:
 * <BLOCKQUOTE><PRE>
 * solver.solve(n);
 * </PRE></BLOCKQUOTE> where <CODE>n</CODE> 
 * is the requested number of solutions. 
 * Requesting <CODE>n</CODE> solutions does not imply that 
 * the problem has more or less than <CODE>n</CODE> solutions. 
 * It means that once the solver has found <CODE>n</CODE> solutions,
 * it will stop searching for additional ones. 
 * 
 * <P>When the solver is done, 
 * but before the <CODE>solve</CODE> method returns, 
 * the <CODE>solved</CODE> method is called.
 * By default, it displays the solutions and the solver counters.
 * The counters depend on the type of solver, 
 * but contain at least the total amount of time spent 
 * finding the solutions.</P>
 * 
 * <P>After the <CODE>solve</CODE> method returns, 
 * the computed solutions can be accessed with 
 * the <CODE>getSolutions</CODE> method, 
 * which returns a <CODE>SolutionStack</CODE>.</P>
 *
 * @author Yan Georget
 * @see SolutionStack
 */
public interface Solver {
    //------------------------------------------------------------------------
    // CONSTANTS
    //------------------------------------------------------------------------
    /** The i18n resource. */
    String I18N_RESOURCE = "com.koalog.jcs.i18n.All";

    //------------------------------------------------------------------------
    // ABSTRACT METHODS
    //------------------------------------------------------------------------
      /**
     * Returns the problem of the solver.
     * @return a problem
     */
    Problem getProblem();

    /**
     * Sets the problem of the solver.
     * @param problem a problem
     */
    void setProblem(Problem problem);

    /**
     * Solves the problem 
     * (generating all the solutions when the solver allows it).
     * Computes the total time.
     *
     * @since 2.1
     */
    void solve();

    /**
     * Solves the problem (generating at most max solutions).
     * Computes the total time.
     * 
     * @param max the maximal number of solutions
     */
    void solve(int max);

    /**
     * Solves the problem (generating at most max solutions), 
     * stops when time is exceeded.
     * Computes the total time.
     * 
     * @param max the maximal number of solutions
     * @param time a long
     * 
     * @since 2.6
     */
    void solve(int max, long time);

    /**
     * Resets the solver:<UL>
     * <LI>undoes the side effects on the variables (if any),
     * <LI>does not reinitialize the counters,
     * <LI>does not clear the solutions found.
     * </UL>
     *
     * <P>In order to clear everything one should also call 
     * the <CODE>clear</CODE> method.
     * @see #clear 
     */
    void reset();

    /**
     * Clears the solver:<UL>
     * <LI>clear the solutions found,
     * <LI>reinitialize the counters,
     * <LI>does not reset the variables to their initial values. 
     * </UL>
     *
     * @see #reset 
     */
    void clear();

    /**
     * Displays the solutions and the global counters.
     */
    void solved();

    /**
     * Does something when no (more) solution can be found.
     */
    void noMoreSolution();
 
    /** 
     * Does something when a time-out occurs. 
     * @since 2.6
     */
    void timeOut();

    /**
     * Returns a solution stack containing the solutions found.
     * @return a solution stack.
     */
    SolutionStack getSolutions();

    /**
     * Inits the counters.
     */
    void initCounters();

    /**
     * Resets the counters.
     */
    void resetCounters();
}
/*
 * $Log$
 * Revision 1.17  2005/07/18 17:48:05  yan
 * fixed jdoc
 *
 * Revision 1.16  2005/07/18 17:26:08  yan
 * introduced TimeOutException + various simplifications
 *
 * Revision 1.15  2004/10/11 15:34:59  yan
 * fixed jdoc
 *
 * Revision 1.14  2004/10/07 12:14:33  yan
 * added back resetCounters in Solver
 *
 * Revision 1.13  2004/09/17 15:38:14  yan
 * more refactoring
 *
 * Revision 1.12  2004/09/17 14:45:00  yan
 * various cleaning
 *
 * Revision 1.11  2004/09/17 14:18:51  yan
 * various cleaning
 *
 * Revision 1.10  2004/06/10 15:09:37  yan
 * fixed jdoc
 *
 * Revision 1.9  2004/06/10 14:45:54  yan
 * added a new solve method
 *
 * Revision 1.8  2003/03/07 13:52:52  yan
 * fixed style
 *
 * Revision 1.7  2002/06/16 19:23:16  mphilip
 * Modified jdoc.
 *
 * Revision 1.6  2002/05/22 09:27:25  yan
 * added javadoc
 *
 * Revision 1.5  2002/05/03 14:26:49  yan
 * added javadoc
 *
 * Revision 1.4  2002/05/02 16:13:18  yan
 * moved
 *
 * Revision 1.3  2002/04/22 12:47:44  yan
 * added setProblem method
 *
 * Revision 1.2  2002/04/19 09:53:44  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
