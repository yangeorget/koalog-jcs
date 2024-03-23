package com.koalog.jcs.examples;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.InconsistencyException;
import com.koalog.jcs.optimizer.Minimizer;

/**
 * Tests the Job-Shop problem.
 *
 * @author Yan Georget
 */
public class JobShopTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public JobShopTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(JobShopTest.class);

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Solves the MT06 instance.
     */
    public void testMT06() {
        cat.info("testMT06");
        JobShopProblem p = new JobShopProblem(JobShopProblem.MT06);
        Minimizer minimizer = new JobShopMinimizer(p);
        minimizer.optimize();
        assertEquals(55, minimizer.getOptimum());
    }

    /**
     * LA01's proof of optimality.
     */
    public void testLA01() {
        cat.info("testLA01");
        JobShopProblem p = new JobShopProblem(JobShopProblem.LA01, 666-1);
        try {
            new JobShopSolver(p).initialFilter();
            assertTrue(false);
        } catch (InconsistencyException ie) {
            assertTrue(true);
        }
    }

    /**
     * LA04's proof of optimality.
     */
    public void runLA04() {
        cat.info("testLA04");
        JobShopProblem p = new JobShopProblem(JobShopProblem.LA04, 590-1);
        JobShopSolver s = new JobShopSolver(p);
        s.solve();
        assertEquals(0, s.getSolutions().size());        
    }

   /**
     * LA31's proof of optimality.
     */
    public void testLA31() {
        cat.info("testLA31");
        JobShopProblem p = new JobShopProblem(JobShopProblem.LA31, 1784-1);
        try {
            new JobShopSolver(p).initialFilter();
            assertTrue(false);
        } catch (InconsistencyException ie) {
            assertTrue(true);
        }
    }

    /**
     * MT20's proof of optimality.
     */
    public void testMT20() {
        cat.info("testMT20");
        JobShopProblem p = new JobShopProblem(JobShopProblem.MT20, 1165-1);
        try {
            new JobShopSolver(p).initialFilter();
            assertTrue(false);
        } catch (InconsistencyException ie) {
            assertTrue(true);
        }
    }

    /**
     * MT10's proof of optimality.
     */
    public void runMT10_OPT() {
        cat.info("runMT10_OPT");
        JobShopProblem p = new JobShopProblem(JobShopProblem.MT10, 930-1);
        JobShopSolver s = new JobShopSolver(p);
        s.solve();
        assertEquals(0, s.getSolutions().size());
    }

    /**
     * Solves the MT10 instance.
     */
    public void runMT10() {
        cat.info("runMT10");
        JobShopProblem p = new JobShopProblem(JobShopProblem.MT10);
        Minimizer minimizer = new JobShopMinimizer(p);
        minimizer.optimize();
        assertEquals(930, minimizer.getOptimum());
    }
}
/*
 * $Log$
 * Revision 1.14  2004/07/20 12:32:33  yan
 * added test
 *
 * Revision 1.13  2004/07/20 08:45:41  yan
 * added test
 *
 * Revision 1.12  2004/07/19 14:34:05  yan
 * fixed style
 *
 * Revision 1.11  2004/07/19 09:20:38  yan
 * externalized pb definitions
 *
 * Revision 1.10  2004/07/19 08:03:52  yan
 * added tests
 *
 * Revision 1.9  2004/07/10 22:02:02  yan
 * various modifs
 *
 * Revision 1.8  2004/07/08 15:07:17  yan
 * various fixes
 *
 * Revision 1.7  2004/07/08 11:06:07  yan
 * moved from jobshop
 *
 */
