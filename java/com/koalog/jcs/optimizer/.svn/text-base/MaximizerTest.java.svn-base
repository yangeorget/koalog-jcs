package com.koalog.jcs.optimizer;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.solver.BacktrackSolver;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.arithmetic.Abs;
import com.koalog.jcs.constraint.arithmetic.ConstantWeightedSum;
import com.koalog.jcs.domain.MinMaxDomain;

/**
 * Tests the Maximizer class.
 * @author Yan Georget
 */
public class MaximizerTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
     public MaximizerTest(String name) {
        super(name);
    }
   
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the mode CONTINUE.
     */
    public void testCONTINUE() {
        cat.info("testCONTINUE");
        Problem p = new BaseProblem();
        IntegerVariable x = new IntegerVariable("x",-3,3);
        IntegerVariable y = new IntegerVariable("y",-3,3);
        p.add(new Abs(y,x));
        p.setVariables(new IntegerVariable[] {x, y});
        BacktrackSolver s = new DefaultSplitSolver(p);
        new Maximizer(y, 
                      s, 
                      Optimizer.CONTINUE).optimize();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {-3,3}
        })));
    }
    
    /**
     * Tests the mode RESTART.
     */
    public void testRESTART() {
        cat.info("testRESTART");
        Problem p = new BaseProblem();
        IntegerVariable x = new IntegerVariable("x",-3,3);
        IntegerVariable y = new IntegerVariable("y",-3,4);
        IntegerVariable z = new IntegerVariable("z",-3,3);
        p.add(new ConstantWeightedSum(new int[] {1,1,1},
                                      new IntegerVariable[] {x,y,z},
                                      3));
        p.setVariables(new IntegerVariable[]{x,y,z});
        BacktrackSolver s = new DefaultSplitSolver(p);
        new Maximizer(y, 
                      s, 
                      Optimizer.RESTART).optimize();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {-3,3,3}, 
            {-3,4,2}
        })));
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(MaximizerTest.class);
}
