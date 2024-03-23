package com.koalog.jcs.solver;

import org.apache.log4j.Category;
import com.koalog.jcs.constraint.Problem;

/**
 * This type of solver is correct and complete.
 *
 * <P>When calling:
 * <BLOCKQUOTE><PRE>
 * solver.solve(n);
 * </PRE></BLOCKQUOTE> 
 * if there are less than <CODE>n</CODE> solutions to the problem,
 * the solver will find all solutions and 
 * return after proving that no other solution can be found.
 *
 * @since 2.3
 * @author Yan Georget
 */
public abstract class ExactSolver extends AbstractSolver {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(ExactSolver.class);

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------  
    /**
     * Constructs a solver for a given problem.
     * @param problem the problem to be solved.
     */
    public ExactSolver(Problem problem) {
        super(problem);
    }
}
/*
 * $Log$
 * Revision 1.2  2004/10/11 15:34:59  yan
 * fixed jdoc
 *
 * Revision 1.1  2004/10/11 15:04:37  yan
 * initial revision
 *
 */
