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
 * Tests the Min class.
 * @author Yan Georget
 */
public class MinTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public MinTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
     /**
     * Tests the class.
     */
    public void testMin() {
        cat.info("testMin");
        Problem p = new BaseProblem();
        final IntegerVariable x = new IntegerVariable("x", 1, 2);
        final SetVariable y = 
            new SetVariable("y", 
                            new SetDomain(Arrays.asList(new Integer[] {}),
                                          Arrays.asList(new Integer[] {
                                              new Integer(1), 
                                              new Integer(2), 
                                              new Integer(3) 
                                          })));

        p.add(new Min(x, y));
        p.setVariables(new Variable[] {y});
        Solver s = new SplitSolver(p,
                                   new KeepOrderVariableHeuristic(),
                                   new FromSetMaxDomainHeuristic());
        s.solve();
        assertEquals(6, s.getSolutions().size());
    }
    
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(MinTest.class);
}
