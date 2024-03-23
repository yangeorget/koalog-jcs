package com.koalog.jcs.constraint.set;

import java.util.Arrays;
import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.SplitSolver;
import com.koalog.jcs.solver.KeepOrderVariableHeuristic;
import com.koalog.jcs.solver.FromSetMinDomainHeuristic;
import com.koalog.jcs.variable.SetVariable;
import com.koalog.jcs.domain.SetDomain;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;

/**
 * Tests the Eq class.
 * @author Yan Georget
 */
public class EqTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public EqTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
     /**
     * Tests the class.
     */
    public void testEq1() {
        cat.info("testEq1");
        Problem p = new BaseProblem();
        SetVariable s1 = 
            new SetVariable("s1", 
                            new SetDomain(Arrays.asList(new Integer[] {
                                new Integer(0)
                            }),
                                          Arrays.asList(new Integer[] {
                                              new Integer(0), 
                                              new Integer(1), 
                                              new Integer(2), 
                                              new Integer(3)
                                          })));
        SetVariable s2 = 
            new SetVariable("s2", 
                            new SetDomain(Arrays.asList(new Integer[] {}),
                                          Arrays.asList(new Integer[] {
                                              new Integer(0), 
                                              new Integer(1), 
                                              new Integer(2), 
                                              new Integer(4)
                                          })));
        p.add(new Eq(s1, s2));
        p.setVariables(new SetVariable[] {s1, s2});
        Solver s = new SplitSolver(p,
                                   new KeepOrderVariableHeuristic(),
                                   new FromSetMinDomainHeuristic());
        s.solve();
        assertTrue(s.getSolutions().size() == 4);
    }

    /**
     * Tests the class.
     */
    public void testEq2() {
        cat.info("testEq2");
        Problem p = new BaseProblem();
        SetVariable s1 = 
            new SetVariable("s1", 
                            new SetDomain(Arrays
                                          .asList(new Integer[] {
                                              new Integer(0),
                                              new Integer(1)
                                          }),
                                          Arrays
                                          .asList(new Integer[] {
                                              new Integer(0), 
                                              new Integer(1), 
                                              new Integer(2)
                                          })));
        SetVariable s2 = 
            new SetVariable("s2", 
                            new SetDomain(Arrays
                                          .asList(new Integer[] {
                                          }),
                                          Arrays
                                          .asList(new Integer[] {
                                              new Integer(0), 
                                              new Integer(1), 
                                              new Integer(3)
                                          })));
        p.add(new Eq(s1, s2));
        p.setVariables(new SetVariable[] {s1, s2});
        Solver s = new SplitSolver(p,
                                   new KeepOrderVariableHeuristic(),
                                   new FromSetMinDomainHeuristic());
        s.solve();
        assertEquals(1, s.getSolutions().size());
    }
    
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(EqTest.class);
}
/*
 * $Log$
 * Revision 1.9  2004/09/17 12:48:02  yan
 * tests now use add and setVariables
 *
 * Revision 1.8  2004/09/17 09:38:30  yan
 * using solve()
 *
 * Revision 1.7  2004/09/17 09:34:42  yan
 * using deepAdd instead of add
 *
 * Revision 1.6  2003/07/12 18:00:07  yan
 * fixed style
 *
 * Revision 1.5  2003/07/12 17:54:52  yan
 * added tests
 *
 * Revision 1.4  2003/06/26 17:15:50  yan
 * fixed imports
 *
 * Revision 1.3  2003/06/23 17:14:18  yan
 * fixed style
 *
 * Revision 1.2  2003/06/19 15:06:11  yan
 * added assertion
 *
 * Revision 1.1  2003/06/19 15:00:16  yan
 * initial revision
 *
 */
