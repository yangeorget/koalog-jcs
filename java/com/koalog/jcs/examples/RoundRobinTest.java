package com.koalog.jcs.examples;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.solver.Solver;

/**
 * Tests the round robin problem.
 *
 * @author Yan Georget
 */
public class RoundRobinTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public RoundRobinTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the round robin problem of size 8.
     */
    public void testRoundRobin8() {
        cat.info("testRoundRobin8");
        Solver s = new RoundRobinSolver(new RoundRobinProblem(8));
        s.solve(1);
        assertTrue(true); // TODO: find a test
    }
    
    /**
     * Tests the round robin problem of size 10.
     */
    public void testRoundRobin10() {
        cat.info("testRoundRobin10");
        Solver s = new RoundRobinSolver(new RoundRobinProblem(10));
        s.solve(1);
        assertTrue(true); // TODO: find a test
    }

    /**
     * Runs the round robin problem of size 12.
     */
    public void runRoundRobin12() {
        cat.info("runRoundRobin12");
        Solver s = new RoundRobinSolver(new RoundRobinProblem(12));
        s.solve(1);
    }

    /**
     * Runs the round robin problem of size 14.
     */
    public void runRoundRobin14() {
        cat.info("runRoundRobin14");
        Solver s = new RoundRobinSolver(new RoundRobinProblem(14));
        s.solve(1);
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(RoundRobinTest.class);
}
/*
 * $Log$
 * Revision 1.4  2004/05/05 19:36:30  yan
 * externalized problems
 *
 * Revision 1.3  2003/10/31 18:09:38  yan
 * *** empty log message ***
 *
 * Revision 1.2  2003/10/12 15:47:03  yan
 * *** empty log message ***
 *
 * Revision 1.1  2003/08/15 10:09:06  yan
 * initial revision
 *
 */
