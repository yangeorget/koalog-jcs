package com.koalog.jcs.constraint.set;

import java.util.Arrays;
import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.SplitSolver;
import com.koalog.jcs.solver.KeepOrderVariableHeuristic;
import com.koalog.jcs.solver.FromSetMaxDomainHeuristic;
import com.koalog.jcs.variable.SetVariable;
import com.koalog.jcs.variable.Variable;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.domain.SetDomain;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;

/**
 * Tests the Max class.
 * @author Yan Georget
 */
public class MaxTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public MaxTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class.
     */
    public void testMax() {
        cat.info("testMax");
        Problem p = new BaseProblem();
        final IntegerVariable x = new IntegerVariable("x", 3, 4);
        final SetVariable y = 
            new SetVariable("y", 
                            new SetDomain(Arrays.asList(new Integer[] {}),
                                          Arrays.asList(new Integer[] {
                                              new Integer(1), 
                                              new Integer(2), 
                                              new Integer(3), 
                                              new Integer(5) 
                                          })));

        p.add(new Max(x, y));
        p.setVariables(new Variable[] {y});
        Solver s = new SplitSolver(p,
                                   new KeepOrderVariableHeuristic(),
                                   new FromSetMaxDomainHeuristic());
        s.solve();
        assertEquals(4, s.getSolutions().size());
    }
 
    /**
     * Tests the class together with Min.
     */
    public void testMaxWithMin() {
        cat.info("testMaxWithMin");
        Problem p = new BaseProblem();
        final IntegerVariable x = new IntegerVariable("x", 1, 10);
        final SetVariable y = 
            new SetVariable("y", 
                            new SetDomain(Arrays.asList(new Integer[] {}),
                                          Arrays.asList(new Integer[] {
                                              new Integer(0), 
                                              new Integer(1), 
                                              new Integer(2), 
                                              new Integer(3), 
                                              new Integer(4), 
                                              new Integer(5),
                                              new Integer(6) 
                                          })));

        p.add(new Min(x, y));
        p.add(new Max(x, y));
        p.setVariables(new Variable[] {y});
        Solver s = new SplitSolver(p,
                                   new KeepOrderVariableHeuristic(),
                                   new FromSetMaxDomainHeuristic());
        s.solve();
        assertEquals(6, s.getSolutions().size());
    }

    /**
     * Tests the class together with Min.
     */
    public void testMaxWithMin2() {
        cat.info("testMaxWithMin2");
        Problem p = new BaseProblem();
        final IntegerVariable min = new IntegerVariable("x", 1, 1);
        final IntegerVariable max = new IntegerVariable("x", 5, 5);
        final SetVariable y = 
            new SetVariable("y", 
                            new SetDomain(Arrays.asList(new Integer[] {}),
                                          Arrays.asList(new Integer[] {
                                              new Integer(0), 
                                              new Integer(1), 
                                              new Integer(2), 
                                              new Integer(3), 
                                              new Integer(4), 
                                              new Integer(5),
                                              new Integer(6) 
                                          })));

        p.add(new Min(min, y));
        p.add(new Max(max, y));
        p.setVariables(new Variable[] {y});
        Solver s = new SplitSolver(p,
                                   new KeepOrderVariableHeuristic(),
                                   new FromSetMaxDomainHeuristic());
        s.solve();
        assertEquals(8, s.getSolutions().size());
    }
    
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(MaxTest.class);
}
