package com.koalog.jcs.examples;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultFFSolver;
import com.koalog.jcs.domain.MinMaxDomain;

/**
 * Tests the Donald problem.
 *
 * @author Yan Georget
 */
public class DonaldTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public DonaldTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests that the unique solution is found.
     */
    public void testDonald() {
        cat.info("testDonald");
        Solver solver = new DefaultFFSolver(new DonaldProblem());
        solver.solve(2);
        assertTrue(solver
                   .getSolutions()
                   .equals(MinMaxDomain.toDomains(new int[][] {
                       {0,7,2,6,8,1,9,5,3,4}
                   })));
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(DonaldTest.class);
}
/*
 * $Log$
 * Revision 1.4  2004/05/06 10:56:30  yan
 * using DefaultFFSolver
 *
 * Revision 1.3  2004/05/05 19:36:30  yan
 * externalized problems
 *
 * Revision 1.2  2003/03/27 11:09:49  yan
 * various fixes
 *
 * Revision 1.1  2002/11/26 13:11:10  yan
 * initial revision
 *
 */
