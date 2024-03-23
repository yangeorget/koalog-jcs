package com.koalog.jcs.examples;

import org.apache.log4j.Category;
import com.koalog.jcs.solver.DefaultFFSolver;

/**
 * A solver for the round robin problem.
 *
 * @author Yan Georget
 */
public class RoundRobinSolver extends DefaultFFSolver {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(RoundRobinSolver.class);

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param p the problem
     */
    public RoundRobinSolver(RoundRobinProblem p) {
        super(p);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.solver.BaseSolver */
    public void solutionFound() {
        super.solutionFound();
        // improved display
        RoundRobinProblem prob  = (RoundRobinProblem)problem;
        int n = prob.n;
        for (int p=0; p<n/2; p++) {
            StringBuffer buf = new StringBuffer();
            for (int w=0; w<n-1; w++) {
                buf.append(prob.t[p][w][0].getMin());
                buf.append("-");
                buf.append(prob.t[p][w][1].getMin());
                buf.append(" ");
            }
            cat.info(buf);
        }
    }
}
