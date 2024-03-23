package com.koalog.jcs.constraint.arithmetic;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.domain.MinMaxDomain;

/**
 * Tests the Abs class.
 * @author Yan Georget
 */
public class AbsTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public AbsTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
     /**
     * Tests the class.
     */
    public void testAbs() {
        cat.info("testAbs");
        Problem p = new BaseProblem();
        IntegerVariable x = new IntegerVariable("x",-2,2);
        IntegerVariable y = new IntegerVariable("y",-2,2);
        p.add(new Abs(x, y));
        p.setVariables(new IntegerVariable[] {x, y});
        Solver s = new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {0,0}, 
            {1,-1}, 
            {1,1}, 
            {2,-2}, 
            {2,2}
        })));
    }
    
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(AbsTest.class);
}
/*
 * $Log$
 * Revision 1.6  2004/09/17 12:47:23  yan
 * tests now use add and setVariables
 *
 * Revision 1.5  2004/09/17 09:33:02  yan
 * using deepAdd instead of add
 *
 * Revision 1.4  2002/10/25 09:00:58  yan
 * cleaned category
 *
 * Revision 1.3  2002/05/02 16:21:22  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
