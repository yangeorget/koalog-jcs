package com.koalog.jcs.examples;

import com.koalog.jcs.solver.CostSplitSolver;
import com.koalog.jcs.optimizer.Optimizer;
import com.koalog.jcs.optimizer.Minimizer;

/**
 * A minimizer for the Travelling Salesman Problem.
 *
 * @author Yan Georget
 */
public class TSPMinimizer extends Minimizer {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param p a TSP
     */
    public TSPMinimizer (TSPProblem p) {
        super(p.sum,
              new CostSplitSolver(p, p.costs),
              Optimizer.CONTINUE);
    }
}
