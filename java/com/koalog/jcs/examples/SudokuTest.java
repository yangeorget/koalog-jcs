package com.koalog.jcs.examples;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultFFSolver;

/**
 * Tests the Sudoku.
 *
 * @author Yan Georget
 */
public class SudokuTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public SudokuTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(SudokuTest.class);

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests instance FO4.
     */
    public void testFO4() {
        cat.info("testFO4");
        SudokuProblem p = new SudokuProblem(SudokuProblem.FO4);
        Solver s = new DefaultFFSolver(p);
        s.solve();
        assertEquals(1, s.getSolutions().size());

    }

    /**
     * Tests instance FO5.
     */
    public void testFO5() {
        cat.info("testFO5");
        SudokuProblem p = new SudokuProblem(SudokuProblem.FO5);
        Solver s = new DefaultFFSolver(p);
        s.solve();
        assertEquals(1, s.getSolutions().size());
    }

    /**
     * Tests instance EASY.
     */
    public void testEASY() {
        cat.info("testEASY");
        SudokuProblem p = new SudokuProblem(SudokuProblem.EASY);
        Solver s = new DefaultFFSolver(p);
        s.solve();
        assertEquals(1, s.getSolutions().size());
    }
}
/*
 * $Log$
 */
