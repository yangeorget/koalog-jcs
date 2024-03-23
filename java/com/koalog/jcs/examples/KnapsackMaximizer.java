package com.koalog.jcs.examples;

import com.koalog.jcs.solver.SplitSolver;
import com.koalog.jcs.solver.KeepOrderVariableHeuristic;
import com.koalog.jcs.solver.DecreasingOrderDomainHeuristic;
import com.koalog.jcs.optimizer.Maximizer;
import com.koalog.jcs.optimizer.Optimizer;

/**
 * A maximizer for the Knapsack problem.
 *
 * @author Yan Georget
 */
public class KnapsackMaximizer extends Maximizer {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param p a Knapsack problem
     */
    public KnapsackMaximizer(KnapsackProblem p) {
        super(p.weight, 
              new SplitSolver(p,
                              new KeepOrderVariableHeuristic(),
                              new DecreasingOrderDomainHeuristic()), 
              Optimizer.CONTINUE);
    }
}
