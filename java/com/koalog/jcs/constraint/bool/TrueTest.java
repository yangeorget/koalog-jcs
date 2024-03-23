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
 * Tests the True class.
 * @author Yan Georget
 */
public class TrueTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public TrueTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class. 
     */
    public void testTrue() {
        cat.info("testTrue");
        Problem p = new BaseProblem();
        BooleanVariable b = new BooleanVariable("b");
        p.add(new True(b));
        p.setVariables(new BooleanVariable[] {b});
        Solver s =  new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {1}
        })));
    }
    
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(TrueTest.class);
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
