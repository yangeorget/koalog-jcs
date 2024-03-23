package com.koalog.jcs.constraint.bool;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.variable.Variable;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.domain.MinMaxDomain;

/**
 * Tests the IfThenElse class.
 * @author Yan Georget
 */
public class IfThenElseTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public IfThenElseTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class. 
     */
    public void testIfThenElse_2() {
        cat.info("testIfThenElse_2");
        Problem p = new BaseProblem();
        IntegerVariable x = new IntegerVariable("x", -1, 1);
        BooleanVariable b = new BooleanVariable("b");
        p.add(new IfThenElse_2(x, b, 0, 1));
        p.setVariables(new Variable[] {b, x});
        Solver s =  new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {0, 1},
            {1, 0}
        })));
    }

    /**
     * Tests the class. 
     */
    public void testIfThenElse_3() {
        cat.info("testIfThenElse_3");
        Problem p = new BaseProblem();
        IntegerVariable x = new IntegerVariable("x", -1, 1);
        IntegerVariable y = new IntegerVariable("y", 0, 2);
        BooleanVariable b = new BooleanVariable("b");
        p.add(new IfThenElse_3(x, b, 0, y));
        p.setVariables(new Variable[] {b, x, y});
        Solver s =  new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {0, 0, 0},
            {0, 1, 1},
            {1, 0, 0},
            {1, 0, 1},
            {1, 0, 2}
        })));
    }
 
    /**
     * Tests the class. 
     */
    public void testIfThenElse_3BIS() {
        cat.info("testIfThenElse_3BIS");
        Problem p = new BaseProblem();
        IntegerVariable x = new IntegerVariable("x", -1, 1);
        IntegerVariable y = new IntegerVariable("y", 0, 2);
        BooleanVariable b = new BooleanVariable("b");
        p.add(new IfThenElse_3BIS(x, b, y, 0));
        p.setVariables(new Variable[] {b, x, y});
        Solver s =  new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {0, 0, 0},
            {0, 0, 1},
            {0, 0, 2},
            {1, 0, 0},
            {1, 1, 1}
        })));
    }
    
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(IfThenElseTest.class);
}
/*
 * $Log$
 * Revision 1.8  2004/09/17 12:47:43  yan
 * tests now use add and setVariables
 *
 * Revision 1.7  2004/09/17 09:34:42  yan
 * using deepAdd instead of add
 *
 * Revision 1.6  2003/03/07 11:41:18  yan
 * fixed style
 *
 * Revision 1.5  2003/02/04 17:04:58  yan
 * fixed category
 *
 * Revision 1.4  2002/10/25 09:02:17  yan
 * cleaned category
 *
 * Revision 1.3  2002/09/16 08:04:05  yan
 * *** empty log message ***
 *
 * Revision 1.2  2002/05/02 16:19:06  yan
 * moved
 *
 * Revision 1.1  2002/04/22 12:44:20  yan
 * initial revision
 *
 */
