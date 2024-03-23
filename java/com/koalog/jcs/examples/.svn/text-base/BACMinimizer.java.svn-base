package com.koalog.jcs.examples;

import com.koalog.jcs.solver.SplitSolver;
import com.koalog.jcs.solver.KeepOrderVariableHeuristic;
import com.koalog.jcs.solver.DecreasingOrderDomainHeuristic;
import com.koalog.jcs.optimizer.Optimizer;
import com.koalog.jcs.optimizer.Minimizer;

/**
 * A minimizer for the Balanced Academic Curriculum Problem.
 *
 * @author Yan Georget
 */
public class BACMinimizer extends Minimizer {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param p a BAC problem
     */
    public BACMinimizer(BACProblem p) {
        super(p.c,
              new SplitSolver(p, 
                              // new SmallestDomainVariableHeuristic(),
                              new KeepOrderVariableHeuristic(), 
                              new DecreasingOrderDomainHeuristic()),
              Optimizer.CONTINUE);
    }
}
