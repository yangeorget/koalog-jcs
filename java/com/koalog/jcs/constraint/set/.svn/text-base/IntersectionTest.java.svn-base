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
 * Tests the Intersection class.
 * @author Yan Georget
 */
public class IntersectionTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public IntersectionTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
     /**
     * Tests the class.
     */
    public void testIntersection() {
        cat.info("testIntersection");
        Problem p = new BaseProblem();
        SetVariable s1 = 
            new SetVariable("s1", 
                            new SetDomain(Arrays.asList(new Integer[] {}),
                                          Arrays.asList(new Integer[] {
                                              new Integer(1), 
                                              new Integer(2), 
                                          })));
        SetVariable s2 = 
            new SetVariable("s2", 
                            new SetDomain(Arrays.asList(new Integer[] {}),
                                          Arrays.asList(new Integer[] {
                                              new Integer(0), 
                                              new Integer(1), 
                                          })));
        SetVariable s3 = 
            new SetVariable("s3", 
                            new SetDomain(Arrays.asList(new Integer[] {
                                new Integer(1)
                            }),
                                          Arrays.asList(new Integer[] {
                                              new Integer(0), 
                                              new Integer(1), 
                                              new Integer(2)
                                          })));
        p.add(new Intersection(s3, s1, s2));
        p.setVariables(new SetVariable[] {s1, s2, s3});
        Solver s = new SplitSolver(p,
                                   new KeepOrderVariableHeuristic(),
                                   new FromSetMinDomainHeuristic());
        s.solve();
        assertEquals(4, s.getSolutions().size());
    }
    
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(IntersectionTest.class);
}
/*
 * $Log$
 * Revision 1.3  2004/09/17 12:48:02  yan
 * tests now use add and setVariables
 *
 * Revision 1.2  2004/09/17 09:34:42  yan
 * using deepAdd instead of add
 *
 * Revision 1.1  2003/07/12 17:55:28  yan
 * initial revision
 *
 */
