package com.koalog.jcs.examples;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultFFSolver;

/**
 * Solves the knight problem.
 *
 * @author Yan Georget
 */
public class KnightTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public KnightTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Runs the knight problem.
     */
    public void testKnight() {
        Solver s = new DefaultFFSolver(new KnightProblem());
        s.solve(1);
        // TODO: find a better test
        assertEquals(1, s.getSolutions().size());
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(KnightTest.class);
}
/*
 * $Log$
 * Revision 1.5  2004/08/30 16:40:05  yan
 * added comment
 *
 * Revision 1.4  2004/05/06 10:56:30  yan
 * using DefaultFFSolver
 *
 * Revision 1.3  2004/05/05 19:36:30  yan
 * externalized problems
 *
 * Revision 1.2  2003/04/27 17:47:04  yan
 * fixed style
 *
 * Revision 1.1  2003/03/27 11:06:47  yan
 * initial revision
 *
 */
