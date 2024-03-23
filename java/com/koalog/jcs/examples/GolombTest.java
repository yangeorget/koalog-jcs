package com.koalog.jcs.examples;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.optimizer.Optimizer;

/**
 * Tests the Golomb ruler problem.
 *
 * @author Yan Georget
 */
public class GolombTest extends TestCase {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(GolombTest.class);

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public GolombTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests Golomb(4).
     */
    public void testGolomb4() {
        cat.info("testGolomb4");
        GolombProblem p = new GolombProblem(4);
        Optimizer minimizer = new GolombMinimizer(p);
        minimizer.optimize();
        assertEquals(6, minimizer.getOptimum());
    }

    /**
     * Tests Golomb(5).
     */
    public void testGolomb5() {
        cat.info("testGolomb5");
        GolombProblem p = new GolombProblem(5);
        Optimizer minimizer = new GolombMinimizer(p);
        minimizer.optimize();
        assertEquals(11, minimizer.getOptimum());
    }

    /**
     * Tests Golomb(6).
     */
    public void testGolomb6() {
        cat.info("testGolomb6");
        GolombProblem p = new GolombProblem(6);
        Optimizer minimizer = new GolombMinimizer(p);
        minimizer.optimize();
        assertEquals(17, minimizer.getOptimum());
    }

    /**
     * Tests Golomb(7).
     */
    public void testGolomb7() {
        cat.info("testGolomb7");
        GolombProblem p = new GolombProblem(7);
        Optimizer minimizer = new GolombMinimizer(p);
        minimizer.optimize();
        assertEquals(25, minimizer.getOptimum());
    }

    /**
     * Tests Golomb(8).
     */
    public void testGolomb8() {
        cat.info("testGolomb8");
        GolombProblem p = new GolombProblem(8);
        Optimizer minimizer = new GolombMinimizer(p);
        minimizer.optimize();
        assertEquals(34, minimizer.getOptimum());
    }

    /**
     * Tests Golomb(9).
     */
    public void testGolomb9() {
        cat.info("testGolomb9");
        GolombProblem p = new GolombProblem(9);
        Optimizer minimizer = new GolombMinimizer(p);
        minimizer.optimize();
        assertEquals(44, minimizer.getOptimum());
    }


    /**
     * Tests Golomb(10).
     */
    public void testGolomb10() {
        cat.info("testGolomb10");
        GolombProblem p = new GolombProblem(10);
        Optimizer minimizer = new GolombMinimizer(p);
        minimizer.optimize();
        assertEquals(55, minimizer.getOptimum());
    }

    /**
     * Runs Golomb(11).
     */
    public void runGolomb11() {
        cat.info("runGolomb11");
        GolombProblem p = new GolombProblem(11);
        Optimizer minimizer = new GolombMinimizer(p);
        minimizer.optimize();
    }

    /**
     * Runs Golomb(12).
     */
    public void runGolomb12() {
        cat.info("runGolomb12");
        GolombProblem p = new GolombProblem(12);
        Optimizer minimizer = new GolombMinimizer(p);
        minimizer.optimize();
    }

    /**
     * Runs Golomb(13).
     */
    public void runGolomb13() {
        cat.info("runGolomb13");
        GolombProblem p = new GolombProblem(13);
        Optimizer minimizer = new GolombMinimizer(p);
        minimizer.optimize();
    }

    /**
     * Runs Golomb(14).
     */
    public void runGolomb14() {
        cat.info("runGolomb14");
        GolombProblem p = new GolombProblem(14);
        Optimizer minimizer = new GolombMinimizer(p);
        minimizer.optimize();
    }

    /**
     * Runs Golomb(15).
     */
    public void runGolomb15() {
        cat.info("runGolomb15");
        GolombProblem p = new GolombProblem(15);
        Optimizer minimizer = new GolombMinimizer(p);
        minimizer.optimize();
    }
}
/*
 * $Log$
 * Revision 1.17  2005/07/18 15:22:54  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.16  2004/07/05 19:17:58  yan
 * various small modifs
 *
 * Revision 1.15  2004/05/05 19:36:30  yan
 * externalized problems
 *
 * Revision 1.14  2004/04/09 14:44:25  yan
 * removal of events
 *
 * Revision 1.13  2004/03/14 18:03:22  yan
 * changed filter API
 *
 * Revision 1.12  2003/06/05 14:54:37  yan
 * *** empty log message ***
 *
 * Revision 1.11  2003/05/08 13:51:55  yan
 * fixed style
 *
 * Revision 1.10  2003/05/08 13:50:34  yan
 * fixed javadoc
 *
 * Revision 1.9  2003/05/04 15:07:13  yan
 * optimized modelling
 *
 * Revision 1.8  2003/04/27 17:47:04  yan
 * fixed style
 *
 * Revision 1.7  2003/03/27 11:58:37  yan
 * using add(Collection)
 *
 * Revision 1.6  2003/03/27 11:09:49  yan
 * various fixes
 *
 * Revision 1.5  2002/10/25 09:05:20  yan
 * cleaned category
 *
 * Revision 1.4  2002/10/08 17:17:22  yan
 * various optimizations and cleaning
 *
 * Revision 1.3  2002/05/02 16:16:52  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
