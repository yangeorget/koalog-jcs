package com.koalog.jcs.choicepoint;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.domain.Domain;
import com.koalog.jcs.domain.MinMaxDomain;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.arithmetic.Eq;
import com.koalog.jcs.solver.BacktrackSolver;
import com.koalog.jcs.solver.DefaultSplitSolver;

/**
 * Tests the ChoicePointStack.
 * @author Yan Georget
 */
public class ChoicePointStackTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
     public ChoicePointStackTest(String name) {
        super(name);
    }
   
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * A simple test for the stack of choice points.
     */
    public void testStack() {
        Problem p = new BaseProblem();
        IntegerVariable x = new IntegerVariable("x",0,100);
        IntegerVariable y = new IntegerVariable("y",0,100);
        IntegerVariable z = new IntegerVariable("z",0,10);
        p.add(new Eq(x, y));
        p.add(new Eq(y, z));
        p.setVariables(new IntegerVariable[] {x, y, z});
        BacktrackSolver s = new DefaultSplitSolver(p);
        s.solve(1);
        BaseChoicePointStack cp = (BaseChoicePointStack) s.getChoicePoints();
        cat.info("CP stack: " + cp);
        assertEquals(2, cp.stack.size());
        BaseChoicePoint cp0 = (BaseChoicePoint) cp.stack.get(0);
        assertTrue(new MinMaxDomain(0, 100).equals((Domain) cp0.get(x)));
        assertTrue(new MinMaxDomain(0, 100).equals((Domain) cp0.get(y)));
        assertTrue(cp0.choices.isEmpty());
        BaseChoicePoint cp1 = (BaseChoicePoint) cp.stack.get(1);
        assertTrue(new MinMaxDomain(1, 10).equals((Domain) cp1.get(x)));
        assertTrue(new MinMaxDomain(0, 10).equals((Domain) cp1.get(y))); 
        assertTrue(new MinMaxDomain(0, 10).equals((Domain) cp1.get(z)));
        assertTrue(cp1.choices.size() == 1);
        assertTrue(cp1.choices.contains(x));
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(ChoicePointStackTest.class);
}
/*
 * $Log$
 * Revision 1.5  2004/09/17 12:48:17  yan
 * tests now use add and setVariables
 *
 * Revision 1.4  2004/09/17 09:36:08  yan
 * using deepAdd instead of add
 *
 * Revision 1.3  2004/06/14 15:55:52  yan
 * using storable references and values
 *
 * Revision 1.2  2004/04/13 16:35:29  yan
 * fixed style
 *
 * Revision 1.1  2004/04/13 15:29:51  yan
 * added a test
 *
 */
