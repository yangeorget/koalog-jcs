package com.koalog.jcs.examples;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.solver.DefaultFFSolver;
import com.koalog.jcs.domain.MinMaxDomain;

/**
 * Tests the queens problem.
 *
 * @author Yan Georget
 */
public class QueensTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
     public QueensTest(String name) {
        super(name);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests Queens(4).
     */
    public void testQueens4() {
        cat.info("testQueens4");
        Solver s = new DefaultSplitSolver(new QueensProblem(4));
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {1,3,0,2},
            {2,0,3,1}
        })));
    }

    /**
     * Tests Queens(8).
     */
    public void testQueens8() {
        cat.info("testQueens8");
        Solver s = new DefaultSplitSolver(new QueensProblem(8));
        s.solve(1);
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {0,4,7,5,2,6,1,3}
        })));
    }

   /**
     * Tests Queens(8) with first-fail heuristic.
     */
    public void testQueens8FF() {
        cat.info("testQueens8FF");
        Solver s = new DefaultFFSolver(new QueensProblem(8));
        s.solve(1);
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {0,4,7,5,2,6,1,3}
        })));
    }

    /**
     * Tests Queens and looks for all solutions.
     */
    public void testQueens8ALL() {
        cat.info("testQueens8ALL");
        Solver s = new DefaultSplitSolver(new QueensProblem(8));
        s.solve();
        assertTrue(s.getSolutions().size() == 92);
    }

    /**
     * Tests Queens and looks for all solutions.
     */
    public void testQueens10ALL() {
        cat.info("testQueens10ALL");
        Solver s = new DefaultSplitSolver(new QueensProblem(10));
        s.solve();
        assertTrue(s.getSolutions().size() == 724);
    }

    /**
     * Runs the queens problem with an adaptive solver.
     */
    public void runQueens_ADAPTIVE() {
        cat.info("runQueens_ADAPTIVE");
        Solver s = new QueensAdaptiveSolver(new QueensProblem_ADAPTIVE(200));
        s.solve();
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(QueensTest.class);
}
/*
 * $Log$
 * Revision 1.22  2004/11/22 10:32:39  yan
 * added test
 *
 * Revision 1.21  2004/10/11 12:16:37  yan
 * added a test for the adpative solver
 *
 * Revision 1.20  2004/09/17 09:38:30  yan
 * using solve()
 *
 * Revision 1.19  2004/06/10 15:10:18  yan
 * using new solve method
 *
 * Revision 1.18  2004/05/15 17:25:40  yan
 * fixed style
 *
 * Revision 1.17  2004/05/07 17:47:52  yan
 * added a method to find all solutions of pb of size 8
 *
 * Revision 1.16  2004/05/06 10:56:30  yan
 * using DefaultFFSolver
 *
 * Revision 1.15  2004/05/05 19:36:30  yan
 * externalized problems
 *
 * Revision 1.14  2004/04/09 17:15:36  yan
 * fixed style
 *
 * Revision 1.13  2004/04/09 14:44:25  yan
 * removal of events
 *
 * Revision 1.12  2004/03/14 16:40:33  yan
 * removed some test methods
 *
 * Revision 1.11  2003/04/27 17:47:04  yan
 * fixed style
 *
 * Revision 1.10  2003/04/01 14:01:08  yan
 * using add(Collection)
 *
 * Revision 1.9  2003/03/27 11:09:49  yan
 * various fixes
 *
 * Revision 1.8  2003/03/20 18:07:56  yan
 * added various run methods
 *
 * Revision 1.7  2003/03/09 16:59:21  yan
 * added test
 *
 * Revision 1.6  2002/10/25 09:05:20  yan
 * cleaned category
 *
 * Revision 1.5  2002/10/02 16:08:13  yan
 * queens(48) changed into queens(8)
 *
 * Revision 1.4  2002/10/01 14:32:47  yan
 * cleaned a bit
 *
 * Revision 1.3  2002/05/02 16:16:52  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:44  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
