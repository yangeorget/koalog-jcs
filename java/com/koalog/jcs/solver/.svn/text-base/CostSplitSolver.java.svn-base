package com.koalog.jcs.solver;

import java.util.Map;
import com.koalog.jcs.constraint.Problem;

/**
 * This split solver is adequate when 
 * each assignement of each variable can be associated with a cost.
 * 
 * <P>It chooses the variables using the 
 * <CODE>MaxRegretVariableHeuristic</CODE> 
 * and, for the chosen variable, uses the 
 * <CODE>MinCostDomainHeuristic</CODE>.</P>
 *
 * <P>It is used as follows:
 * <PRE>
 * Solver solver = new CostSplitSolver(problem, costs);
 * </PRE>
 * </P>
 *
 * @see MaxRegretVariableHeuristic
 * @see MinCostDomainHeuristic
 * @author Yan Georget
 */
public class CostSplitSolver extends SplitSolver { 
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------  
    /**
     * Sole constructor.
     * @param problem the problem to be solved
     * @param costs a map that maps a variable to an array of costs 
     * (each element in the variable's domain is associated with a cost)
     */
    public CostSplitSolver(Problem problem, Map costs) {
        super(problem, 
              new MaxRegretVariableHeuristic(costs), 
              new MinCostDomainHeuristic(costs));
    }
}
/*
 * $Log$
 * Revision 1.1  2002/06/16 13:41:18  yan
 * initial revision
 *
 */
