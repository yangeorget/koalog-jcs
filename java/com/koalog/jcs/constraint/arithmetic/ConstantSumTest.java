package com.koalog.jcs.constraint.arithmetic;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.InconsistencyException;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.variable.IntegerVariable;

/** 
 * Tests the ConstantSum class.
 * @author Yan Georget
 */
public class ConstantSumTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public  ConstantSumTest(String name) {
        super(name);
    }
  
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class.
     */
    public void testConstantSum() {
        cat.info("testConstantSum");
        Problem p = new BaseProblem();
        IntegerVariable[] vars = new IntegerVariable[] {
            new IntegerVariable("v0", 3, 10),
            new IntegerVariable("v1", 3, 10),
            new IntegerVariable("v2", 3, 10)
        };
        p.add(new ConstantSum(vars, 9));
        p.setVariables(vars);
        try {
            new DefaultSplitSolver(p).initialFilter();
            assertEquals(3, ((IntegerVariable) vars[0]).getMax());
            assertEquals(3, ((IntegerVariable) vars[1]).getMax());
            assertEquals(3, ((IntegerVariable) vars[2]).getMax());
        } catch (InconsistencyException ie) {
            assertTrue(false);
        }
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(ConstantSumTest.class);
}
/*
 * $Log$
 * Revision 1.10  2004/09/17 12:47:23  yan
 * tests now use add and setVariables
 *
 * Revision 1.9  2004/09/17 09:33:02  yan
 * using deepAdd instead of add
 *
 * Revision 1.8  2004/05/15 17:25:40  yan
 * fixed style
 *
 * Revision 1.7  2004/05/15 15:40:14  yan
 * added test
 *
 * Revision 1.6  2003/03/27 11:01:48  yan
 * added events related method (updateConstraints)
 *
 * Revision 1.5  2003/02/11 10:26:41  yan
 * fixed style
 *
 * Revision 1.4  2002/10/25 09:00:58  yan
 * cleaned category
 *
 * Revision 1.3  2002/10/07 10:17:40  yan
 * fixed style
 *
 * Revision 1.2  2002/10/05 16:26:46  yan
 * cleaned a bit
 *
 * Revision 1.1  2002/09/16 08:01:48  yan
 * initial revision
 *
 */
