package com.koalog.jcs.optimizer;

import com.koalog.jcs.variable.IntegerVariable;

/**
 * Interface <CODE>Optimizer</CODE> is the root of the optimizer hierarchy 
 * (each optimizer being parameterized by a <CODE>Solver</CODE>, 
 * there is a hierarchy of optimizers matching the solver hierarchy).
 * All optimizers implement the methods of this interface.
 *
 * <P>An optimizer is responsible for finding optimal solutions.
 * The variable to be optimized is an <CODE>IntegerVariable</CODE>, 
 * the optimal value 
 * (returned by method <CODE>getOptimum</CODE> is thus an integer).</P>
 *
 * <P>Requesting an optimizer to find an optimal solution is achieved by:
 * <PRE>
 * optimizer.optimize();
 * </PRE> 
 * When the optimizer is done, 
 * but before the <CODE>optimize</CODE> method returns, 
 * the <CODE>optimized</CODE> method is called.
 * By default, 
 * it calls the <CODE>solved</CODE> method of the corresponding solver.</P>
 * 
 * <P>Optimizers find the global optimum 
 * by searching for a sequence of local optima. 
 * They can work in two distinct modes: 
 * <CODE>Optimizer.RESTART</CODE> or <CODE>Optimizer.CONTINUE</CODE>:
 * <UL>
 * <LI>in the <CODE>RESTART</CODE> mode, 
 * each time a local optimum is found, the
 * problem is enriched with a new constraint discarding that optimum from
 * the solutions, 
 * and the entire search space is again explored <EM>from scratch</EM> 
 * to search for the next local optimum;</LI>
 * <LI> in the <CODE>CONTINUE</CODE> mode, 
 * the search space is explored incrementally.</LI>
 * </UL>
 * The <CODE>CONTINUE</CODE> mode generally gives better results, 
 * except for some heuristics that require a well-balanced search space.</P>
 *
 * @see  IntegerVariable
 * @see  com.koalog.jcs.solver.Solver
 * @author Yan Georget
 */
public interface Optimizer {
    //------------------------------------------------------------------------
    // CONSTANTS
    //------------------------------------------------------------------------  
    /** The mode in which the optimizer explores the rest of 
        the search tree. */
    int CONTINUE = 0;
    /** The mode in which the optimizer restarts with a fresh 
        search tree. */
    int RESTART = 1;    
    
    //------------------------------------------------------------------------
    // ABSTRACT METHODS
    //------------------------------------------------------------------------  
    /**
     * Optimizes the variable to be optimized.
     * Computes the total time.
     */
    void optimize();

    /**
     * Optimizes the variable to be optimized (during the given time).
     * Computes the total time.
     * @param time a long
     */
    void optimize(long time);

    /**
     * Does something when the optimum is found.
     */
    void optimized();

    /** 
     * Returns the optimal value for the variable to be optimized.
     * @return an int
     */
    int getOptimum();

    /**
     * Returns the variable to be optimized.
     * @return an integer variable
     */
    IntegerVariable getVariable();
}
/*
 * $Log$
 * Revision 1.12  2003/09/21 18:13:23  yan
 * removed reset method
 *
 * Revision 1.11  2003/03/20 15:25:25  yan
 * added top-level anytime method
 *
 * Revision 1.10  2003/03/07 13:53:29  yan
 * fixed style
 *
 * Revision 1.9  2003/01/31 13:52:12  yan
 * added reset method
 *
 * Revision 1.8  2002/06/20 12:08:32  yan
 * fixed javadoc typos
 *
 * Revision 1.7  2002/06/16 18:58:25  mphilip
 * Modified jdoc.
 *
 * Revision 1.6  2002/06/16 13:57:25  yan
 * added javadoc
 *
 * Revision 1.5  2002/05/22 09:27:25  yan
 * added javadoc
 *
 * Revision 1.4  2002/05/03 14:26:49  yan
 * added javadoc
 *
 * Revision 1.3  2002/05/02 16:14:13  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:44  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
