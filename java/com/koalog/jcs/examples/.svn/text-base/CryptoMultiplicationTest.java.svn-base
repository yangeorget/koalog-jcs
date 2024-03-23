package com.koalog.jcs.examples;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.domain.MinMaxDomain;

/**
 * Tests the crypto multiplication problem.
 *
 * @author Yan Georget
 */
public class CryptoMultiplicationTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public CryptoMultiplicationTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests that the unique solution is found.
     */
    public void testCryptoMultiplication() {
        cat.info("testCryptoMultiplication");
        Solver solver = 
            new DefaultSplitSolver(new CryptoMultiplicationProblem());
        solver.solve(2);
        assertTrue(solver
                   .getSolutions()
                   .equals(MinMaxDomain.toDomains(new int[][] {{
                       3, 
                       4, 
                       8, 
                       2, 
                       8, 
                       2, 
                       7, 
                       8, 
                       4,  
                       6, 
                       9,
                       6,
                       9, 
                       7, 
                       4}})));
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
       Category.getInstance(CryptoMultiplicationTest.class);
}
/*
 * $Log$
 * Revision 1.1  2005/07/19 10:51:28  yan
 * initial revision
 *
 */
