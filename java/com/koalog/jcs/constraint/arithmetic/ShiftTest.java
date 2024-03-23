package com.koalog.jcs.constraint.arithmetic;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.domain.MinMaxDomain;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;

/**
 * Tests the Shift and Shift_SPARSE classes.
 * @author Yan Georget
 */
public class ShiftTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
     public ShiftTest(String name) {
        super(name);
    }
   
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the Shift class.
     */
    public void testShift() {
        cat.info("testShift");
        Problem p = new BaseProblem();
        IntegerVariable x = new IntegerVariable("x",-2,2);
        IntegerVariable y = new IntegerVariable("y",-2,2);
        p.add(new Shift(x, y, 2));
        p.setVariables(new IntegerVariable[] {x, y});
        Solver s = new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {0,-2}, 
            {1,-1}, 
            {2,0}
        })));
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(ShiftTest.class);

}
/*
 * $Log$
 * Revision 1.7  2004/09/17 12:47:23  yan
 * tests now use add and setVariables
 *
 * Revision 1.6  2004/09/17 09:33:02  yan
 * using deepAdd instead of add
 *
 * Revision 1.5  2004/03/14 16:42:59  yan
 * various simplifications
 *
 * Revision 1.4  2002/10/25 09:00:59  yan
 * cleaned category
 *
 * Revision 1.3  2002/05/02 16:21:23  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
