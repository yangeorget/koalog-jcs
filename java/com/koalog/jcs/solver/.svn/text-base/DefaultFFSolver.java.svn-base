package com.koalog.jcs.solver;

import com.koalog.jcs.constraint.Problem;

/**
 * This is the default first-fail solver.
 * It chooses the variables with smallest domain first,
 * then, for a given variable, tries the values in order.
 *
 * <P>It can be used as follows:
 * <PRE>
 * Solver solver = new DefaultFFSolver(problem);
 * </PRE>
 * Note that this is equivalent to:
 * <PRE>
 * Solver solver = new SplitSolver(pb, 
 *                                 new SmallestDomainVariableHeuristic(), 
 *                                 new IncreasingOrderDomainHeuristic());
 * </PRE>
 * </P>
 *
 * @see SmallestDomainVariableHeuristic
 * @see IncreasingOrderDomainHeuristic
 * @author Yan Georget
 */
public class DefaultFFSolver extends SplitSolver { 
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------  
    /**
     * Sole constructor.
     * @param problem the problem to be solved
     */
    public DefaultFFSolver(Problem problem) {
        super(problem, 
              new SmallestDomainVariableHeuristic(), 
              new IncreasingOrderDomainHeuristic());
    }
}
/*
 * $Log$
 * Revision 1.1  2004/05/06 10:54:40  yan
 * initial revision
 *
 */
