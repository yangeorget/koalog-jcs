package com.koalog.jcs.solver;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;

/**
 * Tests the SplitHighDomainHeuristic class.
 * @author Yan Georget
 */
public class SplitHighDomainHeuristicTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
     public SplitHighDomainHeuristicTest(String name) {
        super(name);
    }
   
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class.
     */
    public void testSplitHighDomainHeuristic() {
        cat.info("testSplitHighDomainHeuristic");
        Problem p = new BaseProblem();
        IntegerVariable x = new IntegerVariable("x", -3, 3);
        IntegerVariable y = new IntegerVariable("y", -3, 3);
        IntegerVariable z = new IntegerVariable("z", -3, 3);
        p.setVariables(new IntegerVariable[] {x,y,z});
        BacktrackSolver s = new SplitSolver(p, 
                                            new KeepOrderVariableHeuristic(), 
                                            new SplitHighDomainHeuristic());
        s.solve();
        assertEquals(7*7*7, s.getSolutions().size());
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(SplitHighDomainHeuristicTest.class);
}
/*
 * $Log$
 * Revision 1.1  2004/07/10 16:49:07  yan
 * initial revision
 *
 */
