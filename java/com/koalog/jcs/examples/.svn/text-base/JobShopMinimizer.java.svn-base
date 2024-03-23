package com.koalog.jcs.examples;

import org.apache.log4j.Category;
import com.koalog.jcs.optimizer.Optimizer;
import com.koalog.jcs.optimizer.Minimizer;

/**
 * A minimizer for the JSSP.
 *
 * @author Yan Georget
 */
public class JobShopMinimizer extends Minimizer {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param p a JSSP
     */
    public JobShopMinimizer (JobShopProblem p) {
        super(p.makespan,
              new JobShopSolver(p),
              Optimizer.CONTINUE);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.optimizer.BaseOptimizer */
    public void localOptimumFound() {
        super.localOptimumFound();
        // uncomment the following line for a nice display of the schedule
        // cat.info(((JobShopProblem) solver.getProblem()).scheduleToString());
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(JobShopMinimizer.class);

}
