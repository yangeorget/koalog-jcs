package com.koalog.jcs.constraint.set;

import java.util.Arrays;
import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.SplitSolver;
import com.koalog.jcs.solver.KeepOrderVariableHeuristic;
import com.koalog.jcs.solver.FromSetMinDomainHeuristic;
import com.koalog.jcs.variable.Variable;
import com.koalog.jcs.variable.SetVariable;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.domain.SetDomain;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;

/**
 * Tests the Intersects class.
 * @author Yan Georget
 */
public class IntersectsTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public IntersectsTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
     /**
     * Tests the class.
     */
    public void testIntersects() {
        cat.info("testIntersects");
        Problem p = new BaseProblem();
        BooleanVariable b = new BooleanVariable("b"); 
        SetVariable y = 
            new SetVariable("y", 
                            new SetDomain(Arrays.asList(new Integer[] {
                                new Integer(3)
                            }),
                                          Arrays.asList(new Integer[] {
                                              new Integer(3), 
                                              new Integer(1), 
                                          })));
        SetVariable z = 
            new SetVariable("z", 
                            new SetDomain(Arrays.asList(new Integer[] {
                                new Integer(1)
                            }),
                                          Arrays.asList(new Integer[] {
                                              new Integer(0), 
                                              new Integer(1), 
                                              new Integer(2)
                                          })));
        p.add(new Intersects(b, y, z));
        p.setVariables(new Variable[] {b, y, z});
        final SetVariable[] sv = new SetVariable[] {y, z};
        Solver s = new SplitSolver(p,
                                   new KeepOrderVariableHeuristic(),
                                   new FromSetMinDomainHeuristic()) {
                public boolean choice() {
                    return choice(sv);
                }
            };
        s.solve();
        assertEquals(8, s.getSolutions().size());
    }
    
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(IntersectsTest.class);
}
/*
 * $Log$
 * Revision 1.7  2004/09/17 12:48:02  yan
 * tests now use add and setVariables
 *
 * Revision 1.6  2004/09/17 09:34:42  yan
 * using deepAdd instead of add
 *
 * Revision 1.5  2004/08/25 14:16:45  yan
 * fixed style
 *
 * Revision 1.4  2004/05/05 11:48:50  yan
 * fixed style
 *
 * Revision 1.3  2004/04/09 14:45:23  yan
 * *** empty log message ***
 *
 * Revision 1.2  2004/03/14 18:03:22  yan
 * changed filter API
 *
 * Revision 1.1  2003/07/19 18:29:55  yan
 * initial revision
 *
 */
