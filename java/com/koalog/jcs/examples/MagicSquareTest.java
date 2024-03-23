package com.koalog.jcs.examples;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultSplitSolver;

/**
 * A test for the magic square problem.
 *
 * @author Yan Georget
 */
public class MagicSquareTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public MagicSquareTest(String name) {
        super(name);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests MagicSquare(4).
     */
    public void testMagicSquare4ALL() {
        cat.info("testMagicSquare4ALL");
        Solver s = new MagicSquareSolver(new MagicSquareProblem(4));
        s.solve();
        assertEquals(880, s.getSolutions().size());
    }
    
    /**
     * Runs MagicSquare(4) with the default solver.
     */
    public void runMagicSquare4ALL() {
        cat.info("runMagicSquare4ALL");
        Solver s = new DefaultSplitSolver(new MagicSquareProblem(4));
        s.solve();
    }
    
    /**
     * Runs MagicSquare with an adaptive solver.
     */
    public void runMagicSquare_ADAPTIVE() {
        cat.info("runMagicSquare_ADAPTIVE");
        Solver s = new MagicSquareAdaptiveSolver(new MagicSquareProblem(30));
        s.solve();
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(MagicSquareTest.class);
}
/*
 * $Log$
 * Revision 1.18  2004/11/05 15:37:18  yan
 * various modifs in run methods
 *
 * Revision 1.17  2004/10/11 12:17:11  yan
 * added a test for the adaptive solver
 *
 * Revision 1.16  2004/09/15 12:51:01  yan
 * added run method (for comparison with other systems)
 *
 * Revision 1.15  2004/06/10 15:11:57  yan
 * externalized some code
 *
 * Revision 1.14  2004/06/03 15:02:33  yan
 * fixed style
 *
 * Revision 1.13  2004/05/15 17:18:03  yan
 * changed test: now computing all solutions
 *
 * Revision 1.12  2004/04/09 14:44:25  yan
 * removal of events
 *
 * Revision 1.11  2004/03/14 18:03:22  yan
 * changed filter API
 *
 * Revision 1.10  2003/04/27 17:47:04  yan
 * fixed style
 *
 * Revision 1.9  2003/03/09 18:46:55  yan
 * optimized choice
 *
 * Revision 1.8  2002/10/25 09:05:20  yan
 * cleaned category
 *
 * Revision 1.7  2002/10/01 14:32:47  yan
 * cleaned a bit
 *
 * Revision 1.6  2002/09/18 13:06:54  yan
 * cleaned a bit
 *
 * Revision 1.5  2002/09/16 08:04:06  yan
 * *** empty log message ***
 *
 * Revision 1.4  2002/05/02 16:16:52  yan
 * moved
 *
 * Revision 1.3  2002/04/22 12:46:44  yan
 * modifications to compile with sun jdk
 *
 * Revision 1.2  2002/04/19 09:53:44  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
