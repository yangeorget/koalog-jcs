package com.koalog.jcs.examples;

import com.koalog.jcs.solver.MinCostDomainHeuristic;
import com.koalog.jcs.solver.KeepOrderVariableHeuristic;
import com.koalog.jcs.solver.SplitSolver;

/**
 * A solver for the cars sequencing problem.
 * @author Yan Georget
 */
public class CarsSequencingSolver extends SplitSolver {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /** 
     * Sole constructor
     * @param p a cars sequencing problem
     */
    public CarsSequencingSolver(CarsSequencingProblem p) {
        super(p,
              new KeepOrderVariableHeuristic(),
              new MinCostDomainHeuristic(p.costs));
    }
}
