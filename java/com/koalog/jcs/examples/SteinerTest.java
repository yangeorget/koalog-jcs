package com.koalog.jcs.examples;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.SplitSolver;
import com.koalog.jcs.solver.KeepOrderVariableHeuristic;
import com.koalog.jcs.solver.FromSetMaxDomainHeuristic;

/**
 * Tests the Steiner problem.
 * 
 * @author Yan Georget
 */
public class SteinerTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public SteinerTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     */
    public void testSteiner() {
        cat.info("testSteiner");
        Solver solver = 
            new SplitSolver(new SteinerProblem(7),
                            new KeepOrderVariableHeuristic(),
                            new FromSetMaxDomainHeuristic());
        solver.solve(1);
        // TODO: find a better test
        assertTrue(solver.getSolutions().size() == 1);
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(SteinerTest.class);
}
/*
 * $Log$
 * Revision 1.1  2004/06/03 14:55:29  yan
 * initial revision
 *
 */
