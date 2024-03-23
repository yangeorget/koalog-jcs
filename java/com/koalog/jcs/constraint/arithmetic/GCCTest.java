package com.koalog.jcs.constraint.arithmetic;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.InconsistencyException;
import com.koalog.jcs.choicepoint.BaseChoicePointStack;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.BacktrackSolver;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.variable.Variable;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.DefaultConstraintScheduler;
import com.koalog.jcs.domain.SparseDomain;
import com.koalog.jcs.domain.MinMaxDomain;
import com.koalog.jcs.domain.IntegerDomain;

/**
 * Tests the GCC class.
 * @author Yan Georget
 */
public class GCCTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public GCCTest(String name) {
        super(name);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    private Problem regin() {
        IntegerVariable[] vars = new IntegerVariable[] {
            new IntegerVariable("peter", new SparseDomain(new int[]{0,1})),
            new IntegerVariable("paul", new SparseDomain(new int[]{0,1})),
            new IntegerVariable("mary", new SparseDomain(new int[]{0,1})),
            new IntegerVariable("john", new SparseDomain(new int[]{0,1})),
            new IntegerVariable("bob", new SparseDomain(new int[]{0,1,2})),
            new IntegerVariable("mike", new SparseDomain(new int[]{1,2,3,4})),
            new IntegerVariable("julia", new SparseDomain(new int[]{2,4}))
        };
        int[] vals = new int[] {
            0, 
            1,
            2,
            3,
            4
        }; 
        int[] low = new int[] {
            1, 
            1,
            1,
            0,
            0
        }; 
        int[] cap = new int[] {
            2, 
            2,
            1,
            2,
            2
        }; 
        Problem p = new BaseProblem();
        p.add(new GCC_SPARSE(vars, vals, low, cap));
        p.setVariables(vars);
        return p;
    }

    /**
     * Tests the filter method.
     */
    public void testSPARSEFilter1() {
        cat.info("testSPARSEFilter1");
        try {
            Problem p = regin();
            new DefaultSplitSolver(p).initialFilter();
            Variable[] vars = p.getVariables();
            assertEquals(0, ((IntegerVariable) vars[0]).getMin());
            assertEquals(1, ((IntegerVariable) vars[0]).getMax());
            assertEquals(0, ((IntegerVariable) vars[1]).getMin());
            assertEquals(1, ((IntegerVariable) vars[1]).getMax());
            assertEquals(0, ((IntegerVariable) vars[2]).getMin());
            assertEquals(1, ((IntegerVariable) vars[2]).getMax());
            assertEquals(0, ((IntegerVariable) vars[3]).getMin());
            assertEquals(1, ((IntegerVariable) vars[3]).getMax());
            assertEquals(2, ((IntegerVariable) vars[4]).getMin());
            assertEquals(2, ((IntegerVariable) vars[4]).getMax());
            assertEquals(3, ((IntegerVariable) vars[5]).getMin());
            assertEquals(4, ((IntegerVariable) vars[5]).getMax());
            assertEquals(4, ((IntegerVariable) vars[6]).getMin());
            assertEquals(4, ((IntegerVariable) vars[6]).getMax());
        } catch (InconsistencyException ie) {
            assertTrue(false);
        }
    }

    /**
     * Tests the filter method.
     */
    public void testSPARSEFilter2() {
        cat.info("testSPARSEFilter2");
        Problem p = regin();
        GCC_SPARSE gcc = (GCC_SPARSE) p.getRelations().iterator().next();
        BacktrackSolver s = new DefaultSplitSolver(p);
        try { 
            s.initialFilter();
            IntegerVariable x = (IntegerVariable) p.getVariables()[0];
            x.adjustValue(new BaseChoicePointStack(), 
                          new DefaultConstraintScheduler(), 
                          gcc, 
                          0);
            s.initialFilter();
            gcc.updateValueNetwork();
            gcc.updateFlow();
            assertTrue(gcc.vn.feasibleFlow());
        } catch(InconsistencyException ie) {
            assertTrue(false);
        }
    }

    /**
     * Tests the filter method.
     */
    public void testFilter() {
        cat.info("testFilter");
        // Puget's example for alldifferent
        IntegerVariable x1 = new IntegerVariable("x1", 3, 4);
        IntegerVariable x2 = new IntegerVariable("x2", 2, 4);
        IntegerVariable x3 = new IntegerVariable("x3", 3, 4);
        IntegerVariable x4 = new IntegerVariable("x4", 2, 5);
        IntegerVariable x5 = new IntegerVariable("x5", 3, 6);
        IntegerVariable x6 = new IntegerVariable("x6", 1, 6);
        Problem p = new BaseProblem();
        p.add(new GCC(new IntegerVariable[] {x1, x2, x3, x4, x5, x6},
                      1, 
                      6,
                      new int[] {0,0,0,0,0,0},
                      new int[] {1,1,1,1,1,1}));
        BacktrackSolver s = new DefaultSplitSolver(p);
        try { 
            s.initialFilter();
            assertEquals(3, ((IntegerDomain) x1.getDomain()).getMin());
            assertEquals(4, ((IntegerDomain) x1.getDomain()).getMax());
            assertEquals(2, ((IntegerDomain) x2.getDomain()).getMin());
            assertEquals(2, ((IntegerDomain) x2.getDomain()).getMax());
            assertEquals(3, ((IntegerDomain) x3.getDomain()).getMin());
            assertEquals(4, ((IntegerDomain) x3.getDomain()).getMax());
            assertEquals(5, ((IntegerDomain) x4.getDomain()).getMin());
            assertEquals(5, ((IntegerDomain) x4.getDomain()).getMax());
            assertEquals(6, ((IntegerDomain) x5.getDomain()).getMin());
            assertEquals(6, ((IntegerDomain) x5.getDomain()).getMax());
            assertEquals(1, ((IntegerDomain) x6.getDomain()).getMin());
            assertEquals(1, ((IntegerDomain) x6.getDomain()).getMax());
        } catch(InconsistencyException ie) {
            assertTrue(false);
        }
    }

    /**
     * Tests the constraint.
     */
    public void testGCC_SPARSE() {
        cat.info("testGCC_SPARSE");
        IntegerVariable[] vars = new IntegerVariable[] {
            new IntegerVariable("x", new SparseDomain(new int[] {0,1,2})),
            new IntegerVariable("y", new SparseDomain(new int[] {0,1,2})),
            new IntegerVariable("z", new SparseDomain(new int[] {0,1,2})),
            new IntegerVariable("t", new SparseDomain(new int[] {0,1,2,3}))
        };
        int[] vals = new int[] {
            0, 
            1,
            2,
            3
        }; 
        int[] low = new int[] {
            0, 
            0, 
            0,
            0
        }; 
        int[] cap = new int[] {
            1, 
            1,
            1,
            1
        }; 
        Problem p = new BaseProblem();
        p.add(new GCC_SPARSE(vars, vals, low, cap));
        p.setVariables(vars);
        Solver s = new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][]{
            {0,1,2,3}, 
            {0,2,1,3}, 
            {1,0,2,3}, 
            {1,2,0,3}, 
            {2,0,1,3}, 
            {2,1,0,3}
        })));
    }    

    /**
     * Tests the constraint.
     */
    public void testGCC() {
        cat.info("testGCC");
        IntegerVariable[] vars = new IntegerVariable[] {
            new IntegerVariable("x", 0, 2),
            new IntegerVariable("y", 0, 2),
            new IntegerVariable("z", 0, 2),
            new IntegerVariable("t", 0, 3)
        };
        int[] low = new int[] {
            0, 
            0, 
            0,
            0
        }; 
        int[] cap = new int[] {
            1, 
            1,
            1,
            1
        }; 
        Problem p = new BaseProblem();
        p.add(new GCC(vars, 0, 3, low, cap));
        p.setVariables(vars);
        Solver s = new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][]{
            {0,1,2,3}, 
            {0,2,1,3}, 
            {1,0,2,3}, 
            {1,2,0,3}, 
            {2,0,1,3}, 
            {2,1,0,3}
        })));
    }    

    /**
     * Tests the constraint GCC on a permutation problem.
     */
    public void testPermutation() {
        cat.info("testPermutation");
        int n = 5;
        IntegerVariable[] vars = new IntegerVariable[n];
        for (int i=0; i<n; i++) {
            vars[i] = new IntegerVariable("x"+i, new MinMaxDomain(0, n-1));
        }
        int[] cap = new int[n];
        int[] low = new int[n];
        for (int i=0; i<n; i++) {
            low[i] = 0;
            cap[i] = 1;
        }
        Problem p = new BaseProblem();
        p.add(new GCC(vars, 0, n-1, low, cap));
        p.setVariables(vars);
        Solver s = new DefaultSplitSolver(p);
        s.solve();
        assertEquals(120, s.getSolutions().size());
    }    

    /**
     * Tests the constraint GCC_SPARSE on a permutation problem.
     */
    public void testPermutation_SPARSE() {
        cat.info("testPermutation_SPARSE");
        int n = 5;
        IntegerVariable[] vars = new IntegerVariable[n];
        for (int i=0; i<n; i++) {
            SparseDomain dom = new SparseDomain(new MinMaxDomain(0, n-1));
            vars[i] = new IntegerVariable("x"+i, dom);
        }
        int[] vals = new int[n];
        int[] cap = new int[n];
        int[] low = new int[n];
        for (int i=0; i<n; i++) {
            vals[i] = i;
            low[i] = 0;
            cap[i] = 1;
        }
        Problem p = new BaseProblem();
        p.add(new GCC_SPARSE(vars, vals, low, cap));
        p.setVariables(vars);
        Solver s = new DefaultSplitSolver(p);
        s.solve();
        assertEquals(120, s.getSolutions().size());
    }    

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(GCCTest.class);
}
/*
 * $Log$
 * Revision 1.13  2005/07/19 17:32:04  yan
 * added test
 *
 * Revision 1.12  2005/03/30 10:21:55  yan
 * renamed GCC into GCC_SPARSE
 *
 * Revision 1.11  2004/11/23 12:59:02  yan
 * added logs
 *
 * Revision 1.10  2004/10/08 10:03:50  yan
 * using getRelations
 *
 * Revision 1.9  2004/09/17 10:05:24  yan
 * changed internalAdd into add
 *
 * Revision 1.8  2004/09/17 09:33:02  yan
 * using deepAdd instead of add
 *
 * Revision 1.7  2004/08/25 14:15:35  yan
 * fixed style
 *
 * Revision 1.6  2004/05/05 11:46:48  yan
 * fixed style
 *
 * Revision 1.5  2004/04/09 14:42:50  yan
 * removal of events
 *
 * Revision 1.4  2004/04/01 19:17:52  yan
 * moved filter methods
 *
 * Revision 1.3  2003/10/03 11:25:12  yan
 * fixed log statements
 *
 * Revision 1.2  2003/10/01 18:34:01  yan
 * added log statement
 *
 * Revision 1.1  2003/08/07 20:06:45  yan
 * initial revision
 *
 */
