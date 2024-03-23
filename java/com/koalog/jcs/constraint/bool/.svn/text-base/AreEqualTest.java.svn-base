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
 * Tests the AreEqual class.
 * @author Yan Georget
 */
public class AreEqualTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public AreEqualTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class. 
     */
    public void testAreEqual() {
        cat.info("testAreEqual");
        Problem p = new BaseProblem();
        IntegerVariable x = new IntegerVariable("x", -1, 1);
        IntegerVariable y = new IntegerVariable("y", 0, 2);
        BooleanVariable b = new BooleanVariable("b");
        p.add(new AreEqual(b, x, y));
        p.setVariables(new Variable[] {b, x, y});
        Solver s =  new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {0,-1,0}, 
            {0,-1,1},
            {0,-1,2},
            {0,0,1},
            {0,0,2},
            {0,1,0},
            {0,1,2},
            {1,0,0},
            {1,1,1}
        })));
    }
    
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(AreEqualTest.class);
}
/*
 * $Log$
 * Revision 1.8  2004/09/17 12:47:43  yan
 * tests now use add and setVariables
 *
 * Revision 1.7  2004/09/17 09:34:42  yan
 * using deepAdd instead of add
 *
 * Revision 1.6  2002/10/25 09:02:17  yan
 * cleaned category
 *
 * Revision 1.5  2002/09/16 08:04:05  yan
 * *** empty log message ***
 *
 */
