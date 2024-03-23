package com.koalog.jcs.examples;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.domain.MinMaxDomain;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultSplitSolver;

/**
 * Tests the Cars Sequencing problem.
 *
 * @author Yan Georget
 */
public class CarsSequencingTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public CarsSequencingTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the ECAI88 instance.
     */
    public void testECAI88() {
        CarsSequencingProblem p = 
            new CarsSequencingProblem(CarsSequencingProblem.ECAI88);
        Solver s = new DefaultSplitSolver(p);
        // Solver s = new CarsSequencingSolver(p);
        s.solve();
        assertTrue(s
                   .getSolutions()
                   .equals(MinMaxDomain.toDomains(new int[][] {
                       {0, 1, 5, 2, 4, 3, 3, 4, 2, 5},
                       {0, 2, 5, 1, 4, 3, 2, 4, 3, 5},
                       {0, 2, 5, 1, 5, 3, 4, 2, 3, 4},
                       {4, 3, 2, 4, 3, 5, 1, 5, 2, 0},
                       {5, 2, 4, 3, 3, 4, 2, 5, 1, 0},
                       {5, 3, 4, 2, 3, 4, 1, 5, 2, 0}
                   })));
    }
    
    /**
     * Runs the RP1 instance.
     */
    public void runRP1() {
        CarsSequencingProblem p = 
            new CarsSequencingProblem(CarsSequencingProblem.RP1);
        Solver s = new CarsSequencingSolver(p);
        s.solve(1);
    }

    /**
     * Runs the S60_01 instance.
     */
    public void runS60_01() {
        CarsSequencingProblem p = 
            new CarsSequencingProblem(CarsSequencingProblem.S60_01);
        Solver s = new CarsSequencingSolver(p);
        s.solve(1);
    }
    
    /**
     * Runs the S70_01 instance.
     */
    public void runS70_01() {
        CarsSequencingProblem p = 
            new CarsSequencingProblem(CarsSequencingProblem.S70_01);
        Solver s = new CarsSequencingSolver(p);
        s.solve(1);
    }
    
    /**
     * Runs the S70_02 instance.
     */
    public void runS70_02() {
        CarsSequencingProblem p = 
            new CarsSequencingProblem(CarsSequencingProblem.S70_02);
        Solver s = new CarsSequencingSolver(p);
        s.solve(1);
    }

    /**
     * Runs the S80_01 instance.
     */
    public void runS80_01() {
        CarsSequencingProblem p = 
            new CarsSequencingProblem(CarsSequencingProblem.S80_01);
        Solver s = new CarsSequencingSolver(p);
        s.solve(1);
    }
    
    /**
     * Runs the S90_10 instance.
     */
    public void runS90_10() {
        CarsSequencingProblem p = 
            new CarsSequencingProblem(CarsSequencingProblem.S90_10);
        Solver s = new CarsSequencingSolver(p);
        s.solve(1);
    }
    
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
       Category.getInstance(CarsSequencingTest.class);
}
/*
 * $Log$
 * Revision 1.3  2004/08/30 09:40:39  yan
 * various modifs
 *
 * Revision 1.2  2004/08/26 18:23:07  yan
 * various optims
 *
 * Revision 1.1  2004/08/26 16:13:47  yan
 * initial revision
 *
 */
