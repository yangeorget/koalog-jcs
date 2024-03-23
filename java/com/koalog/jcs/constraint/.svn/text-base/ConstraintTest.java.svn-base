package com.koalog.jcs.constraint;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.InconsistencyException;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.arithmetic.AllDifferent;
import com.koalog.jcs.constraint.arithmetic.Less;
import com.koalog.jcs.solver.BacktrackSolver;
import com.koalog.jcs.solver.DefaultSplitSolver;

/**
 * Tests the Constraint class.
 * @author Yan Georget
 */
public class ConstraintTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public ConstraintTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the entailment.
     */
    public void testEntailment() {
        cat.info("testEntailment");
        Problem p = new BaseProblem();
        IntegerVariable x = new IntegerVariable("x", 0, 2);
        IntegerVariable y = new IntegerVariable("y", 1, 3);
        Constraint c = new Less(x, y);
        p.add(c);
        p.setVariables(new IntegerVariable[] {x, y});
        BacktrackSolver s = new DefaultSplitSolver(p);
        ChoicePointStack cp = s.getChoicePoints();
        ConstraintScheduler cs = s.getConstraintScheduler();
        try {
            s.initialFilter();
            assertTrue(cp.getDate() > c.getDateEntailed());
            cp.push();
            y.chooseMax(cp, cs, 1);
            s.filter();
            assertFalse(cp.getDate() > c.getDateEntailed());
            cp.pop(cs);
            s.filter();
            assertTrue(cp.getDate() > c.getDateEntailed());
        } catch (InconsistencyException ie) {
            assertTrue(false);
        } 
    }

    /**
     * Tests the fail method.
     */
    public void testFail() {
        Problem p = new BaseProblem();
        IntegerVariable x = new IntegerVariable("x", 0, 1);
        IntegerVariable y = new IntegerVariable("y", 0, 1);
        IntegerVariable z = new IntegerVariable("z", 0, 1);
        IntegerVariable[] vars = {x, y, z};
        p.add(new AllDifferent(vars));
        p.setVariables(vars);
        BacktrackSolver s = new DefaultSplitSolver(p);
        try {
            s.initialFilter();
            assertTrue(false);
        } catch (ConstraintInconsistencyException cie) {
            assertEquals(cie.getConstraint().getClass().getName(), 
                         "com.koalog.jcs.constraint.arithmetic.AllDifferent");
        } 
        
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(ConstraintTest.class);
}
/*
 * $Log$
 * Revision 1.5  2004/09/17 12:48:17  yan
 * tests now use add and setVariables
 *
 * Revision 1.4  2004/09/17 09:35:12  yan
 * using deepAdd instead of add
 *
 * Revision 1.3  2004/04/09 14:42:15  yan
 * removal of events
 *
 * Revision 1.2  2004/04/01 19:15:30  yan
 * problem is not a constraint anymore
 *
 * Revision 1.1  2003/04/01 16:58:21  yan
 * initial revision
 *
 */
