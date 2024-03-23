package com.koalog.jcs.examples;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultFFSolver;
import com.koalog.jcs.domain.MinMaxDomain;

/**
 * Tests the alpha problem.
 *
 * @author Yan Georget
 */
public class AlphaTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public AlphaTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Runs alpha, stopping after 10ms.
     */
    public void runAlpha_TIMEOUT() {
        cat.info("runAlpha_TIMEOUT");
        Solver solver = new DefaultFFSolver(new AlphaProblem());
        solver.solve(1, 10);
    }

    /**
     * Tests that the unique solution is found.
     */
    public void testAlpha() {
        cat.info("testAlpha");
        Solver solver = new DefaultFFSolver(new AlphaProblem());
        solver.solve(2);
        assertTrue(solver
                   .getSolutions()
                   .equals(MinMaxDomain.toDomains(new int[][] {{
                       5,
                       13,
                       9,
                       16,
                       20,
                       4,
                       24,
                       21,
                       25,
                       17,
                       23,
                       2,
                       8,
                       12,
                       10,
                       19,
                       7,
                       11,
                       15,
                       3,
                       1,
                       26,
                       6,
                       22,
                       14,
                       18}})));
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(AlphaTest.class);
}
