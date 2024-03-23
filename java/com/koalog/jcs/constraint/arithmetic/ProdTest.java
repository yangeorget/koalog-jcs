package com.koalog.jcs.constraint.arithmetic;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.InconsistencyException;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.domain.MinMaxDomain;

/**
 * Tests the Prod class.
 * @author Yan Georget
 */
public class ProdTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public ProdTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class.
     */
    public void testProd1() {
        cat.info("testProd1");
        Problem p = new BaseProblem();
        IntegerVariable x = new IntegerVariable("x",-2, 2);
        IntegerVariable y = new IntegerVariable("y",-2, 2);
        IntegerVariable z = new IntegerVariable("z",-2, 2);
        p.add(new Prod(x, y, z));
        p.setVariables(new IntegerVariable[] {x, y, z});
        Solver s = new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {-2, -2, 1},
            {-2, -1, 2},
            {-2, 1, -2},
            {-2, 2, -1},
            {-1, -1, 1},
            {-1, 1, -1},
            {0, -2, 0},
            {0, -1, 0},
            {0, 0, -2},
            {0, 0, -1},
            {0, 0, 0},
            {0, 0, 1},
            {0, 0, 2},
            {0, 1, 0},
            {0, 2, 0},
            {1, -1, -1},
            {1, 1, 1},
            {2, -2, -1},
            {2, -1, -2},
            {2, 1, 2},
            {2, 2, 1}
        })));
    }
    
    /**
     * Tests the class.
     */
    public void testProd2() {
        cat.info("testProd2");
        Problem p = new BaseProblem();
        IntegerVariable x = new IntegerVariable("x",1);
        IntegerVariable y = new IntegerVariable("y",-2,0);
        IntegerVariable z = new IntegerVariable("z",-2,2);
        p.add(new Prod(x, y, z));
        p.setVariables(new IntegerVariable[] {x, y, z});
        try {
            DefaultSplitSolver s = new DefaultSplitSolver(p);
            s.initialFilter();
            assertEquals(-1, y.getMin());
            assertEquals(-1, z.getMin());
        } catch (InconsistencyException ie) {
            assertTrue(false);
        }
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(ProdTest.class);
}
/*
 * $Log$
 * Revision 1.4  2004/09/17 12:47:23  yan
 * tests now use add and setVariables
 *
 * Revision 1.3  2004/09/17 09:33:02  yan
 * using deepAdd instead of add
 *
 * Revision 1.2  2004/09/08 13:12:01  yan
 * added test
 *
 * Revision 1.1  2004/09/07 16:37:24  yan
 * initial revision
 *
 */
