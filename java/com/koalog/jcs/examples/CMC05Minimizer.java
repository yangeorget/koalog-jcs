package com.koalog.jcs.examples;

import com.koalog.jcs.optimizer.*;

/**
 * @author Yan Georget
 */
public class CMC05Minimizer extends Minimizer {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param p a problem
     */
    public CMC05Minimizer(CMC05Problem p) {
        super(p.max,
              new CMC05Solver(p),
              Optimizer.CONTINUE);
    }
}
