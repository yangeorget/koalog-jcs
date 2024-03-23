package com.koalog.jcs.examples;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.domain.MinMaxDomain;

/**
 * Tests the magic hexagon problem.
 *
 * @author Yan Georget
 */
public class MagicHexagonTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public MagicHexagonTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests that the unique solution (wrt symmetries) is found.
     */
    public void testMagicHexagon() {
        cat.info("testMagicHexagon");
        Solver solver = new DefaultSplitSolver(new MagicHexagonProblem());
        solver.solve();
        assertTrue(solver
                   .getSolutions()
                   .equals(MinMaxDomain.toDomains(new int[][] {{
                       3, 19, 16, 
                       17, 7, 2, 12, 
                       18, 1, 5, 4, 10, 
                       11, 6, 8, 13, 
                       9, 14, 15
                   }})));
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(MagicHexagonTest.class);
}
/*
 * $Log$
 * Revision 1.1  2004/06/18 08:23:16  yan
 * initial revision
 *
 */
