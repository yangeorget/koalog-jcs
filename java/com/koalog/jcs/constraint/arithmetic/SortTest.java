package com.koalog.jcs.constraint.arithmetic;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.InconsistencyException;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;

/**
 * Tests the sort class.
 * @author Yan Georget
 */
public class SortTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public SortTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class.
     */
    public void testSort() {
        cat.info("testSort");
        IntegerVariable[] x = new IntegerVariable[] {
            new IntegerVariable("x0", 3,3),
            new IntegerVariable("x1", 0,1),
            new IntegerVariable("x2", 2,3),
            new IntegerVariable("x3", 1,3)};
        IntegerVariable[] y = new IntegerVariable[] {
            new IntegerVariable("y0", 0,0),
            new IntegerVariable("y1", 0,1),
            new IntegerVariable("y2", 1,2),
            new IntegerVariable("y3", 2,3)};
        IntegerVariable[] o = new IntegerVariable[] {
            new IntegerVariable("o0", 2,3),
            new IntegerVariable("o1", 0,1),
            new IntegerVariable("o2", 1,2),
            new IntegerVariable("o3", 1,2)};
        Problem p = new BaseProblem();
        p.add(new AllDifferent(o));
        p.add(new Sort(x, y, o));
        p.setVariables(new IntegerVariable[] { 
            o[0],o[1],o[2],o[3],x[0],x[1],x[2],x[3],y[0],y[1],y[2],y[3]
        });
        try {
            new DefaultSplitSolver(p).initialFilter();
            assertTrue(x[0].isInstantiated());
            assertEquals(3, x[0].getMin());
            assertTrue(x[1].isInstantiated());
            assertEquals(0, x[1].getMin());
            assertTrue(x[2].isInstantiated() && x[2].getMin() == 2);
            assertTrue(x[3].isInstantiated() && x[3].getMin() == 1);
            assertTrue(y[0].isInstantiated() && y[0].getMin() == 0);
            assertTrue(y[1].isInstantiated() && y[1].getMin() == 1);
            assertTrue(y[2].isInstantiated() && y[2].getMin() == 2);
            assertTrue(y[3].isInstantiated() && y[3].getMin() == 3);
            assertTrue(o[0].isInstantiated() && o[0].getMin() == 3);
            assertTrue(o[1].isInstantiated() && o[1].getMin() == 0);
            assertTrue(o[2].isInstantiated() && o[2].getMin() == 2);
            assertTrue(o[3].isInstantiated() && o[3].getMin() == 1);
        } catch (InconsistencyException ie) {
            assertTrue(false);
        }
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(SortTest.class);
}
