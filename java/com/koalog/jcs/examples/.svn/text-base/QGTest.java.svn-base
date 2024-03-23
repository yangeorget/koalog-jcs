package com.koalog.jcs.examples;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultFFSolver;

/**
 * Tests the QG problem.
 *
 * @author Yan Georget
 */
public class QGTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public QGTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests QG5(7).
     */
    public void testQG5_7() {
        cat.info("testQG5_7");
        Solver s = new DefaultFFSolver(new QGProblem.QG5(7));
        s.solve();
        assertTrue(s.getSolutions().size() == 3);
    }

    /**
     * Tests QG5(8).
     */
    public void testQG5_8() {
        cat.info("testQG5_8");
        Solver s = new DefaultFFSolver(new QGProblem.QG5(8));
        s.solve();
        assertTrue(s.getSolutions().size() == 1);
    }
    
    /**
     * Tests QG5(9).
     */
    public void testQG5_9() {
        cat.info("testQG5_9");
        Solver s = new DefaultFFSolver(new QGProblem.QG5(9));
        s.solve();
        assertTrue(s.getSolutions().size() == 0);
    }
    
    /**
     * Tests QG5(10).
     */
    public void testQG5_10() {
        cat.info("testQG5_10");
        Solver s = new DefaultFFSolver(new QGProblem.QG5(10));
        s.solve();
        assertTrue(s.getSolutions().size() == 0);
    }

    /**
     * Tests QG5(11).
     */
    public void testQG5_11() {
        cat.info("testQG5_11");
        Solver s = new DefaultFFSolver(new QGProblem.QG5(11));
        s.solve();
        assertTrue(s.getSolutions().size() == 5);
    }

    /**
     * Runs QG5(12).
     */
    public void runQG5_12() {
        cat.info("testQG5_12");
        Solver s = new DefaultFFSolver(new QGProblem.QG5(12));
        s.solve();
        assertTrue(s.getSolutions().size() == 0);
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(QGTest.class);
}
/*
 * $Log$
 * Revision 1.2  2004/11/22 10:27:09  yan
 * fixed style
 *
 * Revision 1.1  2004/11/18 17:48:12  yan
 * initial revision
 *
 */
