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
 * Tests the Count constraint.
 * @author Yan Georget
 */
public class CountTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public CountTest(String name) {
        super(name);
    }
  
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class.
     */
    public void testCount() {
        cat.info("testCount");
        Problem p = new BaseProblem();
        IntegerVariable c = new IntegerVariable("c", 1, 2);
        IntegerVariable[] x = new IntegerVariable[] {
            new IntegerVariable("x0", 0,1),
            new IntegerVariable("x1", 0,1),
            new IntegerVariable("x2", 0,1)
        };
        p.add(new Count(c, x, 1));
        p.setVariables(new IntegerVariable[] {c, x[0], x[1], x[2]});
        Solver s =  new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {1, 0, 0, 1},
            {1, 0, 1, 0},
            {1, 1, 0, 0},
            {2, 0, 1, 1},
            {2, 1, 0, 1},
            {2, 1, 1, 0}
        })));
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(CountTest.class);
}
/*
 * $Log$
 * Revision 1.6  2005/07/22 12:06:55  yan
 * remove useless test
 *
 * Revision 1.5  2004/09/17 12:47:23  yan
 * tests now use add and setVariables
 *
 * Revision 1.4  2004/09/17 09:33:02  yan
 * using deepAdd instead of add
 *
 * Revision 1.3  2004/06/15 13:27:11  yan
 * added test
 *
 * Revision 1.2  2004/05/05 14:01:09  yan
 * factorized some code
 *
 * Revision 1.1  2003/06/15 19:46:30  yan
 * initial revision
 *
 */
