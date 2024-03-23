package com.koalog.jcs.examples;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.solver.DefaultFFSolver;
import com.koalog.jcs.solver.SplitSolver;
import com.koalog.jcs.solver.SmallestDomainVariableHeuristic;
import com.koalog.jcs.solver.GreatestDomainVariableHeuristic;
import com.koalog.jcs.solver.MostConstrainedVariableHeuristic;
import com.koalog.jcs.solver.IncreasingOrderDomainHeuristic;
import com.koalog.jcs.solver.SplitHighDomainHeuristic;
import com.koalog.jcs.solver.SplitLowDomainHeuristic;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.domain.MinMaxDomain;

/**
 * Tests the send+more=money problem.
 * 
 * @author Yan Georget
 */
public class SendTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public SendTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Finds the unique solution.
     */
    public void runSend() {
        cat.info("runSend");
        Solver solver = new DefaultSplitSolver(new SendProblem());
        solver.solve(1);
    }

    /**
     * Tests that the unique solution is found.
     */
    public void testSend() {
        cat.info("testSend");
        Solver solver = new DefaultSplitSolver(new SendProblem());
        solver.solve(2);
        assertTrue(solver
                   .getSolutions()
                   .equals(MinMaxDomain.toDomains(new int[][] {
                       {9,5,6,7,1,0,8,2}
                   })));
    }

    /**
     * Tests that the unique solution is found.
     */
    public void testSend_FF() {
        cat.info("testSend_FF");
        Solver solver = new DefaultFFSolver(new SendProblem());
        solver.solve(2);
        assertTrue(solver
                   .getSolutions()
                   .equals(MinMaxDomain.toDomains(new int[][] {
                       {9,5,6,7,1,0,8,2}
                   })));
    }

    /**
     * Tests that the unique solution is found.
     */
    public void testSend_GREATEST() {
        cat.info("testSend_GREATEST");
        Solver solver = 
            new SplitSolver(new SendProblem(),
                            new GreatestDomainVariableHeuristic(),
                            new IncreasingOrderDomainHeuristic());
        solver.solve(2);
        assertTrue(solver
                   .getSolutions()
                   .equals(MinMaxDomain.toDomains(new int[][] {
                       {9,5,6,7,1,0,8,2}
                   })));
    }

    /**
     * Tests that the unique solution is found.
     */
    public void testSend_SPLITHIGH() {
        cat.info("testSend_SPLITHIGH");
        Solver solver = 
            new SplitSolver(new SendProblem(),
                            new SmallestDomainVariableHeuristic(),
                            new SplitHighDomainHeuristic());
        solver.solve(2);
        assertTrue(solver
                   .getSolutions()
                   .equals(MinMaxDomain.toDomains(new int[][] {
                       {9,5,6,7,1,0,8,2}
                   })));
    }

    /**
     * Tests that the unique solution is found.
     */
    public void testSend_SPLITLOW() {
        cat.info("testSend_SPLITLOW");
        Solver solver = 
            new SplitSolver(new SendProblem(),
                            new SmallestDomainVariableHeuristic(),
                            new SplitLowDomainHeuristic());
        solver.solve(2);
        assertTrue(solver
                   .getSolutions()
                   .equals(MinMaxDomain.toDomains(new int[][] {
                       {9,5,6,7,1,0,8,2}
                   })));
    }

    /**
     * Tests that the unique solution is found.
     */
    public void testSend_MOSTCONSTRAINED() {
        cat.info("testSend_MOSTCONSTRAINED");
        Problem p = new SendProblem();
        Solver solver = 
            new SplitSolver(p,
                            new MostConstrainedVariableHeuristic(),
                            new IncreasingOrderDomainHeuristic());
        solver.solve(2);
        assertTrue(solver
                   .getSolutions()
                   .equals(MinMaxDomain.toDomains(new int[][] {
                       {9,5,6,7,1,0,8,2}
                   })));
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(SendTest.class);
}
/*
 * $Log$
 * Revision 1.12  2004/09/15 12:52:03  yan
 * removed some heuristics
 *
 * Revision 1.11  2004/07/21 16:37:43  yan
 * fixed usage of MostConstrainedVH's constructor
 *
 * Revision 1.10  2004/05/06 10:56:30  yan
 * using DefaultFFSolver
 *
 * Revision 1.9  2004/05/05 19:36:30  yan
 * externalized problems
 *
 * Revision 1.8  2004/05/05 15:38:20  yan
 * added test
 *
 * Revision 1.7  2003/04/27 17:47:04  yan
 * fixed style
 *
 * Revision 1.6  2003/03/27 11:09:49  yan
 * various fixes
 *
 * Revision 1.5  2002/10/25 09:05:20  yan
 * cleaned category
 *
 * Revision 1.4  2002/09/18 13:06:38  yan
 * Added tests
 *
 * Revision 1.3  2002/05/02 16:16:52  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:44  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
