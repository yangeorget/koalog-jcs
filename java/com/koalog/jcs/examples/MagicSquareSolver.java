package com.koalog.jcs.examples;

import com.koalog.jcs.solver.SplitSolver;
import com.koalog.jcs.solver.SmallestDomainVariableHeuristic;
import com.koalog.jcs.solver.DecreasingOrderDomainHeuristic;

/**
 * A solver for the magic square problem.
 *
 * @author Yan Georget
 */
public class MagicSquareSolver extends SplitSolver {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param p a magic square problem
     */
    public MagicSquareSolver(MagicSquareProblem p) {
        super(p,
              new SmallestDomainVariableHeuristic(),
              new DecreasingOrderDomainHeuristic());
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.solver.BacktrackSolver */
    public boolean choice() {
        if (choice(((MagicSquareProblem) problem).firstDiagonal)) {
            return true;
        }
        if (choice(((MagicSquareProblem) problem).secondDiagonal)) {
            return true;
        }
        return choice(((MagicSquareProblem) problem).elements);
    }
}
