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
 * Tests the EmptyIntersection class.
 * @author Yan Georget
 */
public class EmptyIntersectionTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public EmptyIntersectionTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
     /**
     * Tests the class.
     */
    public void testEmptyIntersection() {
        cat.info("testEmptyIntersection");
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
                            new SetDomain(Arrays.asList(new Integer[] {
                                new Integer(1),
                            }),
                                          Arrays.asList(new Integer[] {
                                              new Integer(0), 
                                              new Integer(1), 
                                          })));
        p.add(new EmptyIntersection(s1, s2));
        p.setVariables(new SetVariable[] {s1, s2});
        Solver s = new SplitSolver(p,
                                   new KeepOrderVariableHeuristic(),
                                   new FromSetMinDomainHeuristic());
        s.solve();
        assertEquals(4, s.getSolutions().size());
    }
    
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(EmptyIntersectionTest.class);
}
/*
 * $Log$
 * Revision 1.3  2004/09/17 12:48:02  yan
 * tests now use add and setVariables
 *
 * Revision 1.2  2004/09/17 09:34:42  yan
 * using deepAdd instead of add
 *
 * Revision 1.1  2003/07/12 22:54:17  yan
 * initial revision
 *
 */
