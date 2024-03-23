package com.koalog.jcs.examples;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultFFSolver;

/**
 * Tests the Kakuro.
 *
 * @author Yan Georget
 */
public class KakuroTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public KakuroTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(KakuroTest.class);

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests instance KS20060802.
     */
    public void testKS20060802() {
        cat.info("testKS20060802");
        KakuroProblem p = new KakuroProblem(KakuroProblem.KS20060802_GRID,
                                            KakuroProblem.KS20060802_HCLUES,
                                            KakuroProblem.KS20060802_VCLUES);
        Solver s = new DefaultFFSolver(p);
        s.solve();
        assertEquals(1, s.getSolutions().size());
    }
}
