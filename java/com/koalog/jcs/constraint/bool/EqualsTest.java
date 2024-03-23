package com.koalog.jcs.constraint.bool;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.variable.Variable;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.domain.MinMaxDomain;

/**
 * Tests the Equals class.
 * @author Yan Georget
 */
public class EqualsTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public EqualsTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class. 
     */
    public void testEquals() {
        cat.info("testEquals");
        Problem p = new BaseProblem();
        IntegerVariable x = new IntegerVariable("x", 1, 3);
        BooleanVariable b = new BooleanVariable("b");
        p.add(new Equals(b, x, 2));
        p.setVariables(new Variable[] {b, x});
        Solver s =  new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {0,1}, 
            {0,3},
            {1,2}
        })));
    }
    
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(Equals.class);
}
/*
 * $Log$
 * Revision 1.4  2004/09/17 12:47:43  yan
 * tests now use add and setVariables
 *
 * Revision 1.3  2004/09/17 09:34:42  yan
 * using deepAdd instead of add
 *
 * Revision 1.2  2002/10/25 09:02:17  yan
 * cleaned category
 *
 * Revision 1.1  2002/09/16 08:08:47  yan
 * initial revision
 *
 */