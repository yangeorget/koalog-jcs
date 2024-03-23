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
 * Tests the Exactly class.
 * @author Yan Georget
 */
public class ExactlyTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public ExactlyTest(String name) {
        super(name);
    }
  
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class.
     */
    public void testExactly() {
        cat.info("testExactly");
        Problem p = new BaseProblem();
        IntegerVariable[] x = new IntegerVariable[] {
            new IntegerVariable("x0", 0,2),
            new IntegerVariable("x1", 0,2),
            new IntegerVariable("x2", 0,2),
            new IntegerVariable("x3", 0,2)
        };
        p.add(new Exactly(3, x, 1));
        p.setVariables(x);
        Solver s =  new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {0, 1, 1, 1},
            {1, 0, 1, 1},
            {1, 1, 0, 1},
            {1, 1, 1, 0},
            {1, 1, 1, 2},
            {1, 1, 2, 1},
            {1, 2, 1, 1},
            {2, 1, 1, 1}
        })));
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(ExactlyTest.class);
}
/*
 * $Log$
 * Revision 1.5  2004/09/17 12:47:23  yan
 * tests now use add and setVariables
 *
 * Revision 1.4  2004/09/17 09:33:02  yan
 * using deepAdd instead of add
 *
 * Revision 1.3  2003/02/11 10:30:42  yan
 * fixed style
 *
 * Revision 1.2  2003/02/05 18:49:01  yan
 * fixed bug
 *
 * Revision 1.1  2003/02/05 17:26:55  yan
 * Cardinality became Exactly
 *
 */
