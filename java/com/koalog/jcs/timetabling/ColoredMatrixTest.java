package com.koalog.jcs.timetabling;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.solver.BacktrackSolver;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.domain.MinMaxDomain;

/**
 * Tests the ColoredMatrix classes.
 * @author Yan Georget
 */
public class ColoredMatrixTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public ColoredMatrixTest(String name) {
        super(name);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class.
     */
    public void testColoredMatrix() {
        cat.info("testColoredMatrix");
        Problem p = new BaseProblem();
        IntegerVariable x11 = new IntegerVariable("x11", 0, 0);
        IntegerVariable x12 = new IntegerVariable("x12", 1, 2);
        IntegerVariable x21 = new IntegerVariable("x21", 1, 2);
        IntegerVariable x22 = new IntegerVariable("x22", 0, 2);
        IntegerVariable n0 = new IntegerVariable("n0", 0, 2);
        IntegerVariable n1 = new IntegerVariable("n1", 0, 2);
        IntegerVariable n2 = new IntegerVariable("n2", 1, 1);
        IntegerVariable cn10 = new IntegerVariable("cn10", 1, 2);
        IntegerVariable cn11 = new IntegerVariable("cn11", 0, 2);
        IntegerVariable cn12 = new IntegerVariable("cn12", 0, 2);
        IntegerVariable cn20 = new IntegerVariable("cn20", 0, 2);
        IntegerVariable cn21 = new IntegerVariable("cn21", 0, 2);
        IntegerVariable cn22 = new IntegerVariable("cn22", 0, 2);
        IntegerVariable ln10 = new IntegerVariable("ln10", 0, 2);
        IntegerVariable ln11 = new IntegerVariable("ln11", 0, 2);
        IntegerVariable ln12 = new IntegerVariable("ln12", 0, 2);
        IntegerVariable ln20 = new IntegerVariable("ln20", 0, 2);
        IntegerVariable ln21 = new IntegerVariable("ln21", 0, 2);
        IntegerVariable ln22 = new IntegerVariable("ln22", 0, 2);
        p.add(new ColoredMatrix(new IntegerVariable[][] {{x11, x12}, 
                                                         {x21, x22}},
                                new IntegerVariable[] {n0, n1, n2},
                                new IntegerVariable[][] {{cn10, cn11, cn12}, 
                                                         {cn20, cn21, cn22}},
                                new IntegerVariable[][] {{ln10, ln11, ln12}, 
                                                         {ln20, ln21, ln22}},
                                true));
        p.setVariables(new IntegerVariable[] {x11, x12, x21, x22});
        BacktrackSolver s =  new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {0, 1, 1, 2},
            {0, 1, 2, 0},
            {0, 1, 2, 1},
            {0, 2, 1, 0},
            {0, 2, 1, 1}
        })));
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(ColoredMatrixTest.class);
}
/*
 * $Log$
 * Revision 1.2  2005/07/29 15:04:05  yan
 * added a flag for redundant modelling
 *
 * Revision 1.1  2005/07/19 15:53:28  yan
 * initial revision
 *
 */
