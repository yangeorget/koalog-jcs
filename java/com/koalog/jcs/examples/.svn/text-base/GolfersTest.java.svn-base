package com.koalog.jcs.examples;

import junit.framework.TestCase;
import org.apache.log4j.Category;

/**
 * @author Yan Georget
 */
public class GolfersTest extends TestCase {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(GolfersTest.class);

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public GolfersTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the golfers problem of size 9.
     */
    public void testGolfers() {
        cat.info("testGolfers");
        GolfersSolver solver = new GolfersSolver(new GolfersProblem(9));
        solver.solve(1);
        // TODO: find a better test
        assertTrue(solver.getSolutions().size() == 1);    
    }
}
/*
 * $Log$
 * Revision 1.9  2004/06/03 15:00:42  yan
 * refactored
 *
 */
