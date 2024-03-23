package com.koalog.jcs.constraint.arithmetic;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.domain.SparseDomain;
import com.koalog.jcs.domain.MinMaxDomain;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;

/** 
 * Tests the Element class.
 * @author Yan Georget
 */
public class ElementTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public ElementTest(String name) {
        super(name);
    }
  
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class Element.
     */
    public void testElement() {
        cat.info("testElement");
        Problem p = new BaseProblem();
        IntegerVariable x = new IntegerVariable("x",-5,5);
        IntegerVariable i = new IntegerVariable("i", new SparseDomain(-5, 5));
        p.add(new Element(x, i, new int[] {7, 4, -7, 3}));
        p.setVariables(new IntegerVariable[] {i, x});
        Solver s =  new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {1,4}, 
            {3,3}
        })));
    }
    
    /**
     * Tests the class Element_3.
     */
    public void testElement_3() {
        cat.info("testElement_3");
        Problem p = new BaseProblem();
        IntegerVariable a = new IntegerVariable("a",-1,1);
        IntegerVariable x = new IntegerVariable("x",-1,0);
        IntegerVariable y = new IntegerVariable("y",1,2);
        IntegerVariable i = new IntegerVariable("i", new SparseDomain(0, 2));
        p.add(new Element_3(a, i, new IntegerVariable[] {x, y}));
        p.setVariables(new IntegerVariable[] {a, i, x, y});
        Solver s = new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {-1,0,-1,1}, 
            {-1,0,-1,2}, 
            {0,0,0,1},
            {0,0,0,2},
            {1,1,-1,1},
            {1,1,0,1}
        })));
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(ElementTest.class);
}
/*
 * $Log$
 * Revision 1.8  2004/09/17 12:47:23  yan
 * tests now use add and setVariables
 *
 * Revision 1.7  2004/09/17 09:33:02  yan
 * using deepAdd instead of add
 *
 * Revision 1.6  2003/02/11 10:30:42  yan
 * fixed style
 *
 * Revision 1.5  2002/12/11 12:21:49  yan
 * initial revision
 *
 * Revision 1.4  2002/10/25 09:00:58  yan
 * cleaned category
 *
 * Revision 1.3  2002/05/02 16:21:23  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
