package com.koalog.jcs.constraint.arithmetic;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.domain.MinMaxDomain;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.variable.IntegerVariable;

/** 
 * Tests the Max class.
 * @author Yan Georget
 */
public class MaxTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public  MaxTest(String name) {
        super(name);
    }
  
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class.
     */
    public void testMax() {
        cat.info("testMax");
        Problem p = new BaseProblem();
        IntegerVariable x0 = new IntegerVariable("x0", 2, 3);
        IntegerVariable x1 = new IntegerVariable("x1", 3, 4);
        IntegerVariable x2 = new IntegerVariable("x2", 3, 5);
        IntegerVariable m = new IntegerVariable("m", 4, 5);
        p.add(new Max(m, new IntegerVariable[] {x0,x1,x2}));
        p.setVariables(new IntegerVariable[] {x0,x1,x2,m});
        Solver s = new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {2, 3, 4, 4},
            {2, 3, 5, 5},
            {2, 4, 3, 4},
            {2, 4, 4, 4},
            {2, 4, 5, 5},
            {3, 3, 4, 4},
            {3, 3, 5, 5},
            {3, 4, 3, 4},
            {3, 4, 4, 4},
            {3, 4, 5, 5}
        })));
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(MaxTest.class);
}
/*
 * $Log$
 * Revision 1.2  2005/07/03 14:28:40  yan
 * fixed style
 *
 * Revision 1.1  2005/06/13 08:58:55  yan
 * initial revision
 *
 */
