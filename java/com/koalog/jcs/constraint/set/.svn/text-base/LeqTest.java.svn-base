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
 * Tests the Leq class.
 * @author Yan Georget
 */
public class LeqTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public LeqTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
     /**
     * Tests the class.
     */
    public void testLeq1() {
        cat.info("testLeq1");
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
        p.add(new Leq(s1, s2));
        p.setVariables(new SetVariable[] {s1, s2});
        Solver s = new SplitSolver(p,
                                   new KeepOrderVariableHeuristic(),
                                   new FromSetMinDomainHeuristic());
        s.solve();
        assertEquals(2, s.getSolutions().size());
    }
    
    /**
     * Tests the class.
     */
    public void testLeq2() {
        cat.info("testLeq2");
        Problem p = new BaseProblem();
        SetVariable s1 = 
            new SetVariable("s1", 
                            new SetDomain(Arrays
                                          .asList(new Integer[] {
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
                                              new Integer(0), 
                                              new Integer(1), 
                                              new Integer(2)
                                          }),
                                          Arrays
                                          .asList(new Integer[] {
                                              new Integer(0), 
                                              new Integer(1), 
                                              new Integer(2)
                                          })));
        p.add(new Leq(s1, s2));
        p.setVariables(new SetVariable[] {s1, s2});
        Solver s = new SplitSolver(p,
                                   new KeepOrderVariableHeuristic(),
                                   new FromSetMinDomainHeuristic());
        s.solve();
        assertEquals(8, s.getSolutions().size());
    }

    /**
     * Tests the class.
     */
    public void testLeq3() {
        cat.info("testLeq3");
        Problem p = new BaseProblem();
        SetVariable s1 = 
            new SetVariable("s1", 
                            new SetDomain(Arrays
                                          .asList(new Integer[] {
                                          }),
                                          Arrays
                                          .asList(new Integer[] {
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
                                              new Integer(2)
                                          })));
        p.add(new Leq(s1, s2));
        p.setVariables(new SetVariable[] {s1, s2});
        Solver s = new SplitSolver(p,
                                   new KeepOrderVariableHeuristic(),
                                   new FromSetMinDomainHeuristic());
        s.solve();
        assertEquals(8, s.getSolutions().size());
    }
    
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(LeqTest.class);
}
/*
 * $Log$
 * Revision 1.5  2004/09/17 12:48:02  yan
 * tests now use add and setVariables
 *
 * Revision 1.4  2004/09/17 09:38:30  yan
 * using solve()
 *
 * Revision 1.3  2004/09/17 09:34:42  yan
 * using deepAdd instead of add
 *
 * Revision 1.2  2003/07/12 18:00:07  yan
 * fixed style
 *
 * Revision 1.1  2003/07/12 17:55:28  yan
 * initial revision
 *
 */
