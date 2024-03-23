package com.koalog.jcs.examples;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.optimizer.Minimizer;

/**
 * Tests the TSP.
 *
 * @author Yan Georget
 */
public class TSPTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public TSPTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(TSPTest.class);

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Runs instance GR17 (taken from TSPLIB) stopping after 1s.
     */
    public void runGR17_TIMEOUT() {
        cat.info("runGR17_TIMEOUT");
        TSPProblem p = new TSPProblem(TSPProblem.GR17, 0, 10000);
        // minimizer definition
        Minimizer minimizer = new TSPMinimizer(p);
        minimizer.optimize(1000);
    }

    /**
     * Tests instance GR17 (taken from TSPLIB).
     * 
     * <P>Note: an optimal solution is 
     * [3,4,10,12,8,16,7,5,11,1,9,15,6,14,2,0,13].
     * </P>
     */
    public void testGR17() {
        cat.info("testGR17");
        TSPProblem p = new TSPProblem(TSPProblem.GR17, 0, 10000);
        // minimizer definition
        Minimizer minimizer = new TSPMinimizer(p);
        minimizer.optimize();
        // junit assertion
        assertEquals(2085, minimizer.getOptimum());
    }

    /**
     * Tests instance GR21 (taken from TSPLIB).
     */
    public void testGR21() {
        cat.info("testGR21");
        TSPProblem p = new TSPProblem(TSPProblem.GR21,0,10000);
        // minimizer definition
        Minimizer minimizer = new TSPMinimizer(p);
        minimizer.optimize();
        // junit assertion
        assertEquals(2707, minimizer.getOptimum());
    }

    /**
     * runs instance GR24 (taken from TSPLIB).
     */
    public void runGR24() {
        cat.info("runGR24");
        TSPProblem p = new TSPProblem(TSPProblem.GR24,0,10000);
        // minimizer definition
        Minimizer minimizer = new TSPMinimizer(p);
        minimizer.optimize();
        // junit assertion
        assertEquals(1272, minimizer.getOptimum());
    }
}
