package com.koalog.jcs.solver;

import com.koalog.jcs.constraint.Problem;

/**
 * This is the default random solver.
 * It chooses the variables in order,
 * then, for a given variable, chooses a value at random.
 *
 * <P>It can be used as follows:
 * <PRE>
 * Solver solver = new DefaultRandomSolver(problem);
 * </PRE>
 * Note that this is equivalent to:
 * <PRE>
 * Solver solver = new SplitSolver(pb, 
 *                                 new KeepOrderVariableHeuristic(), 
 *                                 new RandomOrderDomainHeuristic());
 * </PRE>
 * </P>
 *
 * @see KeepOrderVariableHeuristic
 * @see RandomOrderDomainHeuristic
 * @author Yan Georget
 */
public class DefaultRandomSolver extends SplitSolver { 
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------  
    /**
     * Sole constructor.
     * @param problem the problem to be solved
     */
    public DefaultRandomSolver(Problem problem) {
        super(problem, 
              new KeepOrderVariableHeuristic(), 
              new RandomOrderDomainHeuristic());
    }
}
/*
 * $Log$
 * Revision 1.1  2005/09/23 07:51:58  yan
 * initial revision
 *
 */
