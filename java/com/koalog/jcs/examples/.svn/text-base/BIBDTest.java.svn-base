package com.koalog.jcs.examples;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultSplitSolver;

/**
 * Tests the BIBD problem.
 *
 * @author Yan Georget
 */
public class BIBDTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public BIBDTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the <6, 10, 5, 3, 2> instance.
     */
    public void testBIBD_6_10_5_3_2() {
        Solver s = new DefaultSplitSolver(new BIBDProblem(6, 10, 5, 3, 2));
        s.solve();
        assertEquals(1, s.getSolutions().size());
    }

    /**
     * Tests the <8, 14, 7, 4, 3> instance.
     */
    public void testBIBD_8_14_7_4_3() {
        Solver s = new DefaultSplitSolver(new BIBDProblem(8, 14, 7, 4, 3));
        s.solve();
        assertEquals(92, s.getSolutions().size());
    }

    /**
     * Runs the <6, 50, 25, 3, 10> instance.
     */
    public void runBIBD_6_50_25_3_10() {
        Solver s = new DefaultSplitSolver(new BIBDProblem(6, 50, 25, 3, 10));
        s.solve(1);
    }

    /**
     * Runs the <6, 60, 30, 3, 12> instance.
     */
    public void runBIBD_6_60_30_3_12() {
        Solver s = new DefaultSplitSolver(new BIBDProblem(6, 60, 30, 3, 12));
        s.solve(1);
    }

    /**
     * Runs the <22, 33, 12, 8, 4> instance.
     */
    public void runBIBD_22_33_12_8_4() {
        Solver s = new DefaultSplitSolver(new BIBDProblem(22, 33, 12, 8, 4));
        s.solve(1);
    }

    /**
     * Runs the LAM's instance.
     */
    public void runBIBD_LAM() {
        Solver s = new DefaultSplitSolver(new BIBDProblem(111, 111, 11, 11, 1));
        s.solve(1);
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(BIBDTest.class);
}
