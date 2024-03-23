package com.koalog.jcs.examples;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.SplitSolver;
import com.koalog.jcs.solver.GreatestDomainVariableHeuristic;
import com.koalog.jcs.solver.SplitLowDomainHeuristic;
import com.koalog.jcs.domain.MinMaxDomain;

/**
 * Tests the fractions problem.
 *
 * @author Yan Georget
 */
public class FractionsTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public FractionsTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests that the unique solution is found.
     */
    public void testFractions() {
        cat.info("testFractions");
        Solver solver = new SplitSolver(new FractionsProblem(),
                                        new GreatestDomainVariableHeuristic(),
                                        new SplitLowDomainHeuristic());
        solver.solve();
        assertTrue(solver
                   .getSolutions()
                   .equals(MinMaxDomain.toDomains(new int[][] {{
                       5,
                       3,
                       4,
                       7,
                       6,
                       8,
                       9,
                       1,
                       2}})));
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(FractionsTest.class);
}
/*
 * $Log$
 * Revision 1.1  2004/09/15 12:53:10  yan
 * initial revision
 *
 */
