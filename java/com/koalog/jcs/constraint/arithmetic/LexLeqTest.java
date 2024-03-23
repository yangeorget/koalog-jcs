package com.koalog.jcs.constraint.arithmetic;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.InconsistencyException;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.BacktrackSolver;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.domain.MinMaxDomain;
import com.koalog.jcs.constraint.Constraint;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;

/** 
 * Tests the LexLeq classes.
 * @author Yan Georget
 */
public class LexLeqTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public LexLeqTest(String name) {
        super(name);
    }
  
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class LexLeq (entailment).
     */
    public void testLexLeq1() {
        cat.info("testLexLeq1");
        Problem p = new BaseProblem();
        IntegerVariable x0 = new IntegerVariable("x0",0,1);
        IntegerVariable x1 = new IntegerVariable("x1",0);
        IntegerVariable[] x = {x0, x1};
        IntegerVariable y0 = new IntegerVariable("y0",1);
        IntegerVariable y1 = new IntegerVariable("y1",1);
        IntegerVariable[] y = {y0, y1};
        Constraint c = new LexLeq(x, y);
        p.add(c);
        p.setVariables(new IntegerVariable[] {x0, x1, y0, y1});
        BacktrackSolver s = new DefaultSplitSolver(p);
        try {
            s.initialFilter();
            assertFalse(s.getChoicePoints().getDate() > c.getDateEntailed());  
        } catch (InconsistencyException ie) {
            assertTrue(false);
        }
    }

    /**
     * Tests the class LexLeq.
     */
    public void testLexLeq2() {
        cat.info("testLexLeq2");
        Problem p = new BaseProblem();
        IntegerVariable x0 = new IntegerVariable("x0",0,1);
        IntegerVariable x1 = new IntegerVariable("x1",0,1);
        IntegerVariable[] x = {x0, x1};
        IntegerVariable y0 = new IntegerVariable("y0",0,1);
        IntegerVariable y1 = new IntegerVariable("y1",0,1);
        IntegerVariable[] y = {y0, y1};
        p.add(new LexLeq(x, y));
        p.setVariables(new IntegerVariable[] {x0, x1, y0, y1});
        Solver s =  new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {0,0,0,0},
            {0,0,0,1},
            {0,0,1,0},
            {0,0,1,1},
            {0,1,0,1},
            {0,1,1,0},
            {0,1,1,1},
            {1,0,1,0},
            {1,0,1,1},
            {1,1,1,1}
        })));
    }

    /**
     * Tests the class LexLeq.
     */
    public void testLexLeq3() {
        cat.info("testLexLeq3");
        Problem p = new BaseProblem();
        IntegerVariable x0 = new IntegerVariable("x0",0,1);
        IntegerVariable x1 = new IntegerVariable("x1",0,1);
        IntegerVariable[] x = {x0, x1};
        IntegerVariable y0 = new IntegerVariable("y0",0,1);
        IntegerVariable y1 = new IntegerVariable("y1",0,1);
        IntegerVariable[] y = {y0, y1};
        p.add(new LexLeq(x, y));
        p.setVariables(new IntegerVariable[] {y1, y0, x1, x0});
        Solver s =  new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {0,0,0,0},
            {0,1,0,0},
            {0,1,0,1},
            {0,1,1,0},
            {1,0,0,0},
            {1,0,1,0},
            {1,1,0,0},
            {1,1,0,1},
            {1,1,1,0},
            {1,1,1,1}
        })));
    }
    
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(LexLeqTest.class);
}
