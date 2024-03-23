package com.koalog.jcs.constraint.arithmetic;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.domain.MinMaxDomain;
import com.koalog.jcs.domain.SparseDomain;

/**
 * Tests the Permutation class.
 * @author Yan Georget
 */
public class PermutationTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public PermutationTest(String name) {
        super(name);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class.
     */
    public void testPermutation() {
        cat.info("testPermutation");
        Problem pb = new BaseProblem();
        IntegerVariable p0 = new IntegerVariable("p0", new SparseDomain(1,1));
        IntegerVariable p1 = new IntegerVariable("p1", new SparseDomain(0,2));
        IntegerVariable p2 = new IntegerVariable("p2", new SparseDomain(0,2));
        IntegerVariable[] p = new IntegerVariable[] {p0, p1, p2};
        IntegerVariable q0 = new IntegerVariable("q0", new SparseDomain(0,2));
        IntegerVariable q1 = new IntegerVariable("q1", new SparseDomain(0,2));
        IntegerVariable q2 = new IntegerVariable("q2", new SparseDomain(0,2));
        IntegerVariable[] q = new IntegerVariable[] {q0, q1, q2};
        pb.add(new Permutation(p,q));
        pb.setVariables(new IntegerVariable[] {p0, p1, p2, q0, q1, q2});
        Solver s =  new DefaultSplitSolver(pb);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {1,0,2,1,0,2}, 
            {1,2,0,2,0,1} 
        })));
    }
    

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(PermutationTest.class);
}
/*
 * $Log$
 * Revision 1.4  2005/07/13 14:00:41  yan
 * refactored Permutation
 *
 * Revision 1.3  2004/09/17 12:47:23  yan
 * tests now use add and setVariables
 *
 * Revision 1.2  2004/09/17 09:33:02  yan
 * using deepAdd instead of add
 *
 * Revision 1.1  2003/10/31 18:11:06  yan
 * initial revision
 *
 */
