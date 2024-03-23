package com.koalog.jcs.constraint.arithmetic;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.domain.MinMaxDomain;

/**
 * Tests the Prime class.
 * @author Yan Georget
 */
public class PrimeTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public PrimeTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class.
     */
    public void testPrime() {
        cat.info("testPrime");
        Problem p = new BaseProblem();
        IntegerVariable x = new IntegerVariable("x",0,10);
        p.add(new Prime(x));
        p.setVariables(new IntegerVariable[] {x});
        Solver s = new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {2}, 
            {3}, 
            {5},
            {7}
        })));
    }
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(PrimeTest.class);
}
/*
 * $Log$
 * Revision 1.4  2004/09/17 12:47:23  yan
 * tests now use add and setVariables
 *
 * Revision 1.3  2004/09/17 09:33:02  yan
 * using deepAdd instead of add
 *
 * Revision 1.2  2004/09/08 13:16:01  yan
 * fixed style
 *
 * Revision 1.1  2004/09/08 13:13:10  yan
 * initial revision
 *
 */
