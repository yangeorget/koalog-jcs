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
 * Tests the Atmost constraint.
 * @author Yan Georget
 */
public class AtmostTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public AtmostTest(String name) {
        super(name);
    }
  
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class.
     */
    public void testAtmost() {
        cat.info("testAtmost");
        Problem p = new BaseProblem();
        IntegerVariable[] x = new IntegerVariable[] {
            new IntegerVariable("x0", 0,1),
            new IntegerVariable("x1", 0,1),
            new IntegerVariable("x2", 0,1)
        };
        p.add(new Atmost(1, x, 1));
        p.setVariables(x);
        Solver s =  new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {0, 0, 0},
            {0, 0, 1},
            {0, 1, 0},
            {1, 0, 0}
        })));
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(AtmostTest.class);
}
/*
 * $Log$
 * Revision 1.3  2004/09/17 12:47:23  yan
 * tests now use add and setVariables
 *
 * Revision 1.2  2004/09/17 09:33:02  yan
 * using deepAdd instead of add
 *
 * Revision 1.1  2004/05/05 14:01:57  yan
 * initial revision
 *
 */
