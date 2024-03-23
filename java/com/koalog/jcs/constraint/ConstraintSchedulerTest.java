package com.koalog.jcs.constraint;

import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.choicepoint.BaseChoicePointStack;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.arithmetic.Leq;
import com.koalog.jcs.constraint.arithmetic.ConstantWeightedSum;
import com.koalog.jcs.constraint.arithmetic.AllDifferent_SPARSE;
import com.koalog.jcs.constraint.arithmetic.ConstantSum;

/**
 * Tests the constraint schedulers.
 * @author Yan Georget
 */
public class ConstraintSchedulerTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORSz
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public ConstraintSchedulerTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * A basic test for the ComplexityConstraintScheduler.
     * It checks that the constraints are popped in the correct order.
     */
    public void testComplexityConstraintScheduler1() {
        cat.info("testComplexityConstraintScheduler1");
        ChoicePointStack cp = new BaseChoicePointStack();
        BaseConstraintScheduler cs = new ComplexityConstraintScheduler();
        IntegerVariable x = new IntegerVariable("x", 0, 10);
        IntegerVariable y = new IntegerVariable("y", 0, 10);
        IntegerVariable z = new IntegerVariable("z", 0, 10);
        IntegerVariable t = new IntegerVariable("t", 0, 10);
        IntegerVariable[] vars = new IntegerVariable[] {x, y, z, t};
        Problem p = new BaseProblem();     
        p.add(new Leq(x, y));
        p.add(new ConstantWeightedSum(new int[] {2,2,2,2}, vars, 10));
        p.add(new AllDifferent_SPARSE(vars));
        p.flattenConstraints();
        p.updateDependencies();
        Collection col = p.getConstraints();
        cat.info(col);
        for (Iterator i = col.iterator(); i.hasNext();) {
            cs.add(cp, (Constraint) i.next());
        }
        cat.info(cs);
        Constraint c = cs.pop(cp);
        assertTrue(cs.date > c.getDateScheduled());
        assertTrue(c.hasConstantComplexity());
        c = cs.pop(cp);
        assertTrue(cs.date > c.getDateScheduled());
        assertTrue(c.hasConstantComplexity());
        c = cs.pop(cp);
        assertTrue(cs.date > c.getDateScheduled());
        assertTrue(c.hasConstantComplexity());
        c = cs.pop(cp);
        assertTrue(cs.date > c.getDateScheduled());
        assertTrue(c.hasConstantComplexity());
        c = cs.pop(cp);
        assertTrue(cs.date > c.getDateScheduled());
        assertTrue(c.hasConstantComplexity());
        c = cs.pop(cp);
        assertTrue(cs.date > c.getDateScheduled());
        c = cs.pop(cp);
        assertTrue(cs.date > c.getDateScheduled());
        assertFalse(c.hasConstantComplexity());
        assertTrue(cs.pop(cp) == null);
    }

    /**
     * Another tests for the ComplexityConstraintScheduler.
     * It checks that some constraints are not added to the scheduler.
     */
    public void testComplexityConstraintScheduler2() {
        cat.info("testComplexityConstraintScheduler2");
        ChoicePointStack cp = new BaseChoicePointStack();
        BaseConstraintScheduler cs = new ComplexityConstraintScheduler();
        IntegerVariable x = new IntegerVariable("x", 0, 10);
        IntegerVariable y = new IntegerVariable("y", 0, 10);
        IntegerVariable z = new IntegerVariable("z", 0, 10);
        IntegerVariable t = new IntegerVariable("t", 0, 10);
        IntegerVariable[] vars = new IntegerVariable[] {x, y, z, t};
        Constraint leq1 = new Leq(x, y);
        Constraint leq2 = new Leq(y, z);
        Constraint leq3 = new Leq(z, t);
        Constraint sum = new ConstantSum(vars, 10);
        List p = new ArrayList();
        p.add(leq1);
        p.add(leq2);
        p.add(leq3);
        p.add(sum);
        // leq3 being entailed, it won't be added to the scheduler
        leq3.entailed(cp);
        cs.constraint = leq1;
        // leq1 being idempotent, it won't be added to the scheduler
        cs.add(cp, p);
        cat.info(cs);
        assertTrue(cs.date > leq1.getDateScheduled());
        assertTrue(cs.date > leq3.getDateScheduled());
        assertTrue(cs.date <= leq2.getDateScheduled());
        assertTrue(cs.date <= sum.getDateScheduled());
        Constraint c = cs.pop(cp);
        assertEquals(leq2, c);
        c = cs.pop(cp);
        assertEquals(sum, c);
        assertTrue(cs.pop(cp) == null);
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(ConstraintSchedulerTest.class);
}
/*
 * $Log$
 * Revision 1.6  2005/07/13 11:09:32  yan
 * fixed test
 *
 * Revision 1.5  2005/07/12 13:31:27  yan
 * using new AllDifferent_SPARSE
 *
 * Revision 1.4  2004/09/17 12:48:17  yan
 * tests now use add and setVariables
 *
 * Revision 1.3  2004/09/17 09:33:02  yan
 * using deepAdd instead of add
 *
 * Revision 1.2  2004/06/25 14:59:56  yan
 * fixed style
 *
 * Revision 1.1  2004/06/24 14:18:35  yan
 * initial revision
 *
 */
