package com.koalog.jcs.examples;

import com.koalog.jcs.optimizer.Optimizer;
import com.koalog.jcs.optimizer.Minimizer;

/**
 * A minimizer for the Golomb problem.
 *
 * @author Yan Georget
 */
public class GolombMinimizer extends Minimizer {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param p a Golomb problem
     */
    public GolombMinimizer(GolombProblem p) {
        super(p.dist[0][p.n-1], 
              new GolombSolver(p), 
              Optimizer.CONTINUE);
    }
}
