package com.koalog.jcs.examples;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.SplitSolver;
import com.koalog.jcs.solver.KeepOrderVariableHeuristic;
import com.koalog.jcs.solver.DecreasingOrderDomainHeuristic;
import com.koalog.jcs.domain.MinMaxDomain;

/**
 * Tests the unknown multiplication problem.
 *
 * @author Yan Georget
 */
public class UnknownMultiplicationTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public UnknownMultiplicationTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests that the unique solution is found.
     */
    public void testUnknownMultiplication() {
        cat.info("testUnknownMultiplication");
        Solver solver = new SplitSolver(new UnknownMultiplicationProblem(),
                                        new KeepOrderVariableHeuristic(),
                                        new DecreasingOrderDomainHeuristic());
        solver.solve(2);
        assertTrue(solver
                   .getSolutions()
                   .equals(MinMaxDomain.toDomains(new int[][] {{
                       1, 
                       7, 
                       9, 
                       2, 
                       2, 
                       4, 
                       7, 
                       1, 
                       6,  
                       3,  
                       5,  
                       8,  
                       3,  
                       5,  
                       8,  
                       4,  
                       0,  
                       0,  
                       9,  
                       6}})));
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(UnknownMultiplicationTest.class);
}
/*
 * $Log$
 * Revision 1.2  2004/09/08 08:23:13  yan
 * fixed style
 *
 * Revision 1.1  2004/09/07 19:17:12  yan
 * initial revision
 *
 */
