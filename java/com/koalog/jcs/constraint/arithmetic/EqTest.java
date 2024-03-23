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
 * Tests the Eq class.
 * @author Yan Georget
 */
public class EqTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public EqTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
     /**
     * Tests the class.
     */
    public void testEq() {
        cat.info("testEq");
        Problem p = new BaseProblem();
        IntegerVariable x = new IntegerVariable("x",-1,1);
        IntegerVariable y = new IntegerVariable("y",-1,1);
        p.add(new Eq(x, y));
        p.setVariables(new IntegerVariable[] {x, y});
        Solver s = new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {-1, -1}, 
            {0, 0}, 
            {1, 1}
        })));
    }
    
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(EqTest.class);
}
/*
 * $Log$
 * Revision 1.4  2004/09/17 12:47:23  yan
 * tests now use add and setVariables
 *
 * Revision 1.3  2004/09/17 09:33:02  yan
 * using deepAdd instead of add
 *
 * Revision 1.2  2002/10/25 09:00:58  yan
 * cleaned category
 *
 * Revision 1.1  2002/09/16 08:01:48  yan
 * initial revision
 *
 */