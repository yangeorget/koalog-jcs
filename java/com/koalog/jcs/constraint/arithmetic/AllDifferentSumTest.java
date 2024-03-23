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
 * Tests the AllDifferentSum class.
 * @author Yan Georget
 */
public class AllDifferentSumTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public AllDifferentSumTest(String name) {
        super(name);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the filtering.
     */
    public void testFilter() {
        cat.info("testFilter");
        Problem p = new BaseProblem();
        IntegerVariable v0 = new IntegerVariable("v0", 1, 5);
        IntegerVariable v1 = new IntegerVariable("v1", 1, 5);
        IntegerVariable v2 = new IntegerVariable("v2", 1, 5);
        IntegerVariable sum = new IntegerVariable("sum", 5, 6);
        p.add(new AllDifferentSum(sum, new IntegerVariable[] {v0, v1, v2}));
        p.setVariables(new IntegerVariable[] {sum, v0, v1, v2});
        try {
            new DefaultSplitSolver(p).initialFilter();
            assertTrue(sum.isInstantiated());
            assertEquals(6, sum.getMin());
        } catch (InconsistencyException ie) {
            assertTrue(false);
        }
    }

    /**
     * Tests the constraint as a problem.
     */
    public void testAllDifferentSum() {
        cat.info("testAllDifferentSum");
        Problem p = new BaseProblem();
        IntegerVariable v0 = new IntegerVariable("v0", 1, 7);
        IntegerVariable v1 = new IntegerVariable("v1", 1, 7);
        IntegerVariable v2 = new IntegerVariable("v2", 1, 7);
        IntegerVariable sum = new IntegerVariable("sum", 0, 7);
        p.add(new AllDifferentSum(sum, new IntegerVariable[] {v0, v1, v2}));
        p.setVariables(new IntegerVariable[] {sum, v0, v1, v2});
        Solver s = new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {6, 1, 2, 3},
            {6, 1, 3, 2},
            {6, 2, 1, 3},
            {6, 2, 3, 1},
            {6, 3, 1, 2},
            {6, 3, 2, 1},
            {7, 1, 2, 4},
            {7, 1, 4, 2},
            {7, 2, 1, 4},
            {7, 2, 4, 1},
            {7, 4, 1, 2},
            {7, 4, 2, 1}
        })));
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
       Category.getInstance(AllDifferentSumTest.class);
}
