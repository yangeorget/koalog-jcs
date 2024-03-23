package com.koalog.jcs.constraint.arithmetic;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.domain.MinMaxDomain;

/**
 * Tests the ConstantWeightedSum class.
 * @author Yan Georget
 */
public class ConstantWeightedSumTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public ConstantWeightedSumTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class.
     */    
    public void testConstantWeightedSum1() {
        cat.info("testConstantWeightedSum1");
        Problem p = new BaseProblem();
        IntegerVariable[] vars = new IntegerVariable[] {
            new IntegerVariable("x",-2,2),
            new IntegerVariable("y",-2,2)
        };
        p.add(new ConstantWeightedSum(new int[] {2,-3},
                                    vars,
                                      6));
        p.setVariables(vars);
        Solver s = new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {0,-2}
        })));
    }

    /**
     * Tests the translation into small constraints.
     */    
    public void testConstantWeightedSum2() {
        cat.info("testConstantWeightedSum2");
        Problem p = new BaseProblem("constant_sum");
        IntegerVariable[] vars = new IntegerVariable[] {
            new IntegerVariable("x", 1),
            new IntegerVariable("y", 2),
            new IntegerVariable("z", 3, 4)
        };
        p.add(new ConstantWeightedSum(new int[] {-3, 2, 1},
                                      vars,
                                      1));
        p.setVariables(vars);
        p.flattenConstraints();
        p.updateDependencies();
        assertEquals(1, p.getConstraints().size());
    }


    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
       Category.getInstance(ConstantWeightedSumTest.class);
}
