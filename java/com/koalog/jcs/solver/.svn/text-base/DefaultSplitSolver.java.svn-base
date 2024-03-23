package com.koalog.jcs.solver;

import com.koalog.jcs.constraint.Problem;

/**
 * This is the default split solver.
 * It chooses the variables in order, 
 * then, for a given variable, tries the values in order.
 *
 * <P>It can be used as follows:
 * <PRE>
 * Solver solver = new DefaultSplitSolver(problem);
 * </PRE>
 * Note that this is equivalent to:
 * <PRE>
 * Solver solver = new SplitSolver(pb, 
 *                                 new KeepOrderVariableHeuristic(), 
 *                                 new IncreasingOrderDomainHeuristic());
 * </PRE>
 * </P>
 *
 * @see KeepOrderVariableHeuristic
 * @see IncreasingOrderDomainHeuristic
 * @author Yan Georget
 */
public class DefaultSplitSolver extends SplitSolver { 
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------  
    /**
     * Sole constructor.
     * @param problem the problem to be solved
     */
    public DefaultSplitSolver(Problem problem) {
        super(problem, 
              new KeepOrderVariableHeuristic(), 
              new IncreasingOrderDomainHeuristic());
    }
}
/*
 * $Log$
 * Revision 1.6  2002/06/16 13:59:14  yan
 * added javadoc
 *
 * Revision 1.5  2002/05/22 09:27:25  yan
 * added javadoc
 *
 * Revision 1.4  2002/05/06 10:14:38  yan
 * *** empty log message ***
 *
 * Revision 1.3  2002/05/02 16:13:18  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:44  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
