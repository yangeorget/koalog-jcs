package com.koalog.jcs.constraint.arithmetic;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.InconsistencyException;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.domain.MinMaxDomain;

/**
 * Tests the Range class.
 * @author Yan Georget
 */
public class InDomainTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public InDomainTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class.
     */
    public void testRange1() {
        cat.info("testRange1");
        Problem p = new BaseProblem();
        IntegerVariable x = new IntegerVariable("x",-30,30);
        p.add(new InDomain(x, new int[] {-50, -25, 0, 25, 50}));
        p.setVariables(new IntegerVariable[] {x});
        try {
            new DefaultSplitSolver(p).initialFilter();
            assertTrue(x.getMin() == -25);
            assertTrue(x.getMax() == 25);
        } catch (InconsistencyException ie) {
            assertTrue(false);
        }
    }

    /**
     * Tests the class.
     */
    public void testRange2() {
        cat.info("testRange2");
        Problem p = new BaseProblem();
        IntegerVariable x = new IntegerVariable("x",0,10);
        p.add(new InDomain(x, new int[] {-10, -5, -1}));
        p.setVariables(new IntegerVariable[] {x});
        try {
            new DefaultSplitSolver(p).initialFilter();
            assertTrue(false);
        } catch (InconsistencyException ie) {
            assertTrue(true);
        }
    }

    /**
     * Tests the class.
     */
    public void testRange3() {
        cat.info("testRange3");
        Problem p = new BaseProblem();
        IntegerVariable x = new IntegerVariable("x",0,5);
        p.add(new InDomain(x, new int[] {-10, 0, 10}));
        p.setVariables(new IntegerVariable[] {x});
        try {
            new DefaultSplitSolver(p).initialFilter();
            assertTrue(x.getMin() == 0 && x.getMax() == 0);
        } catch (InconsistencyException ie) {
            assertTrue(false);
        }
    }

    /**
     * Tests the class.
     */
    public void testRange4() {
        cat.info("testRange4");
        Problem p = new BaseProblem();
        IntegerVariable x = new IntegerVariable("x",0,10);
        p.add(new InDomain(x, new int[] {-10, 1, 3, 5}));
        p.setVariables(new IntegerVariable[] {x});
        Solver s = new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {1}, 
            {3}, 
            {5}
        })));
    }
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(InDomainTest.class);
}
/*
 * $Log$
 * Revision 1.7  2004/09/17 12:47:23  yan
 * tests now use add and setVariables
 *
 * Revision 1.6  2004/09/17 09:33:02  yan
 * using deepAdd instead of add
 *
 * Revision 1.5  2004/09/08 13:12:37  yan
 * values has now to be sorted
 *
 * Revision 1.4  2004/04/09 14:42:50  yan
 * removal of events
 *
 * Revision 1.3  2004/04/01 19:18:12  yan
 * moved filter methods
 *
 * Revision 1.2  2003/02/11 10:30:42  yan
 * fixed style
 *
 * Revision 1.1  2002/12/11 12:19:55  yan
 * initial revision
 *
 */
