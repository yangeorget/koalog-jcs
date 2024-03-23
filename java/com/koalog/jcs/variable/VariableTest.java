package com.koalog.jcs.variable;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.Constraint;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.arithmetic.Add;
import com.koalog.jcs.constraint.arithmetic.Mul;
import com.koalog.jcs.constraint.arithmetic.Less;
import com.koalog.jcs.constraint.arithmetic.Neq;
import com.koalog.jcs.constraint.arithmetic.Element;

/**
 * Tests the Variable class.
 * @author Yan Georget
 */
public class VariableTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
     public VariableTest(String name) {
        super(name);
    }
   
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the getConstraintNb method.
     */
    public void testConstraintNb() {
        Problem p = new BaseProblem();
        IntegerVariable d = new IntegerVariable("d",-2,2);
        IntegerVariable x = new IntegerVariable("x",-2,2);
        IntegerVariable y = new IntegerVariable("y",-2,2);
        p.add(new Add(x,d,y));
        p.add(new Mul(x,2,y));
        p.add(new Mul(x,2,d));
        p.add(new Mul(x,0,y));
        p.flattenConstraints();
        p.updateDependencies();
        assertEquals(3, x.getConstraintNb());
    }

    /**
     * Tests the dependencies.
     */
    public void testDependencies() {
        cat.info("testDependencies");
        Problem p = new BaseProblem();
        IntegerVariable x = new IntegerVariable("x", 0, 2);
        IntegerVariable y = new IntegerVariable("y", 1, 3);
        IntegerVariable z = new IntegerVariable("z", 2, 4);
        Constraint c1 = new Less(x, y);
        Constraint c2 = new Neq(x, y);
        Constraint c3 = new Element(x, y, new int[] {1});
        p.add(c1);
        p.add(c2);
        p.add(c3);
        p.flattenConstraints();
        p.updateDependencies();
        assertTrue(x.groundConstraints.contains(c2) 
                   && x.groundConstraints.size() == 1);
        assertTrue(x.domConstraints.size() == 0);
        assertTrue(x.minConstraints.contains(c1) 
                   && x.minConstraints.size() == 1);
        assertTrue(x.maxConstraints.size() == 0);
        assertTrue(x.minmaxConstraints.contains(c3) 
                   && x.minmaxConstraints.size() == 1);
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(VariableTest.class);
}
/*
 * $Log$
 * Revision 1.7  2004/09/17 12:49:04  yan
 * tests now use add and setVariables
 *
 * Revision 1.6  2004/09/17 09:36:08  yan
 * using deepAdd instead of add
 *
 * Revision 1.5  2004/06/25 14:59:05  yan
 * dependencies are now updated in the solver
 *
 * Revision 1.4  2004/05/05 11:43:42  yan
 * fixed style
 *
 * Revision 1.3  2004/04/01 19:21:43  yan
 * problem is not a constraint anymore
 *
 * Revision 1.2  2003/04/01 16:59:39  yan
 * added tests
 *
 * Revision 1.1  2003/03/27 11:06:47  yan
 * initial revision
 *
 */
