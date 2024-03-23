package com.koalog.jcs.examples;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.domain.MinMaxDomain;

/**
 * Tests the zebra problem.
 *
 * @author Yan Georget
 */
public class ZebraTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public ZebraTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests that the unique solution is found.
     */
    public void testZebra() {
        cat.info("testZebra");
        Solver solver = new DefaultSplitSolver(new ZebraProblem());
        solver.solve();
        assertTrue(solver
                   .getSolutions()
                   .equals(MinMaxDomain.toDomains(new int[][] {{
                       2, 
                       3, 
                       4, 
                       1, 
                       0, 
                       2, 
                       4, 
                       3, 
                       0, 
                       1, 
                       4, 
                       2, 
                       0, 
                       3, 
                       1, 
                       3, 
                       2, 
                       0, 
                       1, 
                       4, 
                       1, 
                       4, 
                       2, 
                       3, 
                       0
                   }})));
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(ZebraTest.class);
}
/*
 * $Log$
 * Revision 1.1  2004/11/18 17:48:12  yan
 * initial revision
 *
 */
