package com.koalog.jcs.solver;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.SolverException;
import com.koalog.jcs.InconsistencyException;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.domain.MinMaxDomain;
import com.koalog.jcs.constraint.Constraint;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.arithmetic.ConstantWeightedSum;
import com.koalog.jcs.constraint.arithmetic.Leq;
import com.koalog.jcs.constraint.arithmetic.Less;
import com.koalog.jcs.constraint.arithmetic.Neq;

/**
 * Tests the BacktrackSolver class.
 * @author Yan Georget
 */
public class BacktrackSolverTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
     public BacktrackSolverTest(String name) {
        super(name);
    }
   
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the reset method.
     */
    public void testReset() {
        cat.info("testReset");
        Problem p = new BaseProblem();
        IntegerVariable x = new IntegerVariable("x",-3,3);
        IntegerVariable y = new IntegerVariable("y",-3,4);
        IntegerVariable z = new IntegerVariable("z",-3,3);
        p.add(new ConstantWeightedSum(new int[] {1,1,1},
                                      new IntegerVariable[] {x,y,z},
                                      3));
        p.setVariables(new IntegerVariable[] {x,y,z});
        BacktrackSolver s = new DefaultSplitSolver(p);
        try {
            s.search(Long.MAX_VALUE); 
            s.reset();
            assertTrue(x.getDomain().equals(new MinMaxDomain(-3, 3)));
            assertTrue(y.getDomain().equals(new MinMaxDomain(-3, 4)));
            assertTrue(z.getDomain().equals(new MinMaxDomain(-3, 3)));
        } catch (SolverException ie) {
            assertTrue(false);
        }
    }

    /**
     * Tests the solve method.
     */
    public void testSolve1() {
        cat.info("testSolve1");
        Problem p = new BaseProblem();
        IntegerVariable x = new IntegerVariable("x", 0,2);
        IntegerVariable y = new IntegerVariable("y", 2,2);
        p.add(new Leq(x, y));
        p.setVariables(new IntegerVariable[] {x,y});
        BacktrackSolver s = new DefaultSplitSolver(p);
        s.solve();
        assertEquals(3, s.getSolutions().size());
    }

    /**
     * Tests the solve method.
     */
    public void testSolve2() {
        cat.info("testSolve2");
        Problem p = new BaseProblem();
        IntegerVariable x = new IntegerVariable("x", 0,2);
        IntegerVariable y = new IntegerVariable("y", 2,2);
        p.add(new Leq(x, y));
        p.setVariables(new IntegerVariable[] {x,y});
        BacktrackSolver s = new DefaultSplitSolver(p);
        s.solve(1);
        s.solve(1);
        assertEquals(2, s.getSolutions().size());
    }

    /**
     * Tests the post method.
     */
    public void testPost1() {
        cat.info("testPost1");
        Problem p = new BaseProblem();
        BacktrackSolver s = new DefaultSplitSolver(p);
        IntegerVariable x = new IntegerVariable("x", 0,2);
        IntegerVariable y = new IntegerVariable("y", 2,2);
        try {
            s.post(new Less(x, y));
        } catch (InconsistencyException ie) {
            assertTrue(false);
        }
        assertEquals(1, x.getMax());
    }

    /**
     * Tests the post method.
     */
    public void testPost2() {
        cat.info("testPost2");
        Problem p = new BaseProblem();
        BacktrackSolver s = new DefaultSplitSolver(p);
        IntegerVariable x = new IntegerVariable("x", 0,2);
        IntegerVariable y = new IntegerVariable("y", 2,2);
        Constraint c = new Less(x, y);
        c.entailed(s.getChoicePoints());
        try {
            s.post(c);
        } catch (InconsistencyException ie) {
            assertTrue(false);
        }
        assertEquals(2, x.getMax());
    }

    /**
     * Tests the post method.
     */
    public void testPost3() {
        cat.info("testPost3");
        Problem p = new BaseProblem();
        BacktrackSolver s = new DefaultSplitSolver(p);
        IntegerVariable x = new IntegerVariable("x", 0,1);
        IntegerVariable y = new IntegerVariable("y", 0,1);
        IntegerVariable z = new IntegerVariable("z", 0,1);
        Constraint cxy = new Neq(x, y);
        Constraint cyz = new Neq(y, z);
        Constraint czx = new Neq(z, x);
        p.setVariables(new IntegerVariable[] {x, y, z});
        try {
            cat.debug(p.getConstraints());
            s.post(cxy);
            cat.debug(p.getConstraints());
            s.post(cyz);
            cat.debug(p.getConstraints());
            s.post(czx);
            cat.debug(p.getConstraints());
            s.getChoicePoints().push();
            x.chooseMax(s.getChoicePoints(), 
                        s.getConstraintScheduler(), 
                        0);
            cat.debug(x);
            cat.debug(s.getConstraintScheduler());
            s.filter();
            assertTrue(false);
        } catch (InconsistencyException ie) {
            cat.debug(ie);
            assertTrue(true);
        }
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(BacktrackSolverTest.class);
}
/*
 * $Log$
 * Revision 1.8  2005/07/19 07:40:38  yan
 * real anytime constraint solving
 *
 * Revision 1.7  2004/09/17 12:49:04  yan
 * tests now use add and setVariables
 *
 * Revision 1.6  2004/09/17 09:36:08  yan
 * using deepAdd instead of add
 *
 * Revision 1.5  2004/09/16 13:36:02  yan
 * added post method
 *
 * Revision 1.4  2004/06/16 09:55:02  yan
 * changed the test for the bug on solve method
 *
 * Revision 1.3  2004/06/16 09:53:13  yan
 * added a test for a bug on solve method
 *
 * Revision 1.2  2002/10/25 09:05:21  yan
 * cleaned category
 *
 * Revision 1.1  2002/10/04 13:17:57  yan
 * initial revision
 *
 */
