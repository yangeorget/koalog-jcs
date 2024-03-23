package com.koalog.jcs.constraint.arithmetic;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.domain.MinMaxDomain;

/**
 * Tests the LeqShift class.
 * @author Yan Georget
 */
public class LeqShiftTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public LeqShiftTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
     /**
     * Tests the class.
     */
    public void testLeqShift() {
        cat.info("testLeqShift");
        Problem p = new BaseProblem();
        IntegerVariable x = new IntegerVariable("x",0,2);
        IntegerVariable y = new IntegerVariable("y",-2,0);
        p.add(new LeqShift(x, y, 2));
        p.setVariables(new IntegerVariable[] {x, y});
        Solver s = new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {0, -2}, 
            {0, -1}, 
            {0, 0}, 
            {1, -1},
            {1, 0},
            {2, 0}
        })));
    }
    
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(LeqShiftTest.class);
}
/*
 * $Log$
 * Revision 1.3  2004/09/17 12:47:23  yan
 * tests now use add and setVariables
 *
 * Revision 1.2  2004/09/17 09:33:02  yan
 * using deepAdd instead of add
 *
 * Revision 1.1  2002/11/18 11:48:52  yan
 * initial revision
 *
 */
