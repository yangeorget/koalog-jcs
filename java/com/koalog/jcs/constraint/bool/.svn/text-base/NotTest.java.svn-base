package com.koalog.jcs.constraint.bool;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.domain.MinMaxDomain;

/**
 * Tests the Not class.
 * @author Yan Georget
 */
public class NotTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public NotTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class. 
     */
    public void testNot() {
        cat.info("testNot");
        Problem p = new BaseProblem();
        BooleanVariable a = new BooleanVariable("a");
        BooleanVariable b = new BooleanVariable("b");
        p.add(new Not(a, b));
        p.setVariables(new BooleanVariable[] {a, b});
        Solver s =  new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {0,1}, 
            {1,0}
        })));
    }
    
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(NotTest.class);
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
