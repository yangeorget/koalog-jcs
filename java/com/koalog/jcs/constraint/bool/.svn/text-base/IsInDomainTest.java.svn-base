package com.koalog.jcs.constraint.bool;

import java.util.SortedSet;
import java.util.TreeSet;
import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.variable.Variable;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.domain.MinMaxDomain;
import com.koalog.jcs.domain.SparseDomain;

/**
 * Tests the InRange class.
 * @author Yan Georget
 */
public class IsInDomainTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public IsInDomainTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class. 
     */
    public void testInRange() {
        cat.info("testInRange");
        Problem p = new BaseProblem();
        IntegerVariable x = new IntegerVariable("x", new SparseDomain(1, 3));
        BooleanVariable b = new BooleanVariable("b");
        SortedSet t = new TreeSet();
        t.add(new Integer(2));
        t.add(new Integer(3));
        t.add(new Integer(4));
        p.add(new IsInDomain(b, x, t));
        p.setVariables(new Variable[] {b, x});
        Solver s =  new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {0,1}, 
            {1,2},
            {1,3}
        })));
    }
    
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(IsInDomainTest.class);
}
/*
 * $Log$
 * Revision 1.1  2005/07/25 10:10:32  yan
 * renamed InSet into InRange
 *
 * Revision 1.1  2005/07/22 17:05:12  yan
 * initial revision
 *
 */
