package com.koalog.jcs.constraint.arithmetic;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;

/**
 * Tests the WeightedSum class.
 * @author Yan Georget
 */
public class WeightedSumTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public WeightedSumTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the translation into small constraints.
     */    
    public void testWeightedSum() {
        cat.info("testWeightedSum");
        Problem p = new BaseProblem("constant_sum");
        IntegerVariable sum = new IntegerVariable("sum", -10, 10);
        IntegerVariable[] vars = new IntegerVariable[] {
            new IntegerVariable("x", 1),
            new IntegerVariable("y", 2),
            new IntegerVariable("z", 3)
        };
        p.add(new WeightedSum(sum,
                              new int[] {-3, 2, 0},
                              vars));
        p.setVariables(new IntegerVariable[] {sum});
        p.flattenConstraints();
        p.updateDependencies();
        assertEquals(1, p.getConstraints().size());
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(WeightedSumTest.class);
}
