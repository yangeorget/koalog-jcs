package com.koalog.jcs.constraint.arithmetic;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.domain.MinMaxDomain;
import com.koalog.jcs.domain.SparseDomain;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;

/**
 * Tests the cycle class.
 * @author Yan Georget
 */
public class CycleTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public CycleTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class.
     */
    public void testCycle() {
        cat.info("testCycle");
        Problem p = new BaseProblem();
        IntegerVariable a = new IntegerVariable("a", new SparseDomain(0, 1));
        IntegerVariable b = new IntegerVariable("b", new SparseDomain(0, 4));
        IntegerVariable c = new IntegerVariable("c", new SparseDomain(0, 4));
        IntegerVariable d = new IntegerVariable("d", new SparseDomain(0, 4));
        IntegerVariable e = new IntegerVariable("e", new SparseDomain(0, 0));
        IntegerVariable[] vars = new IntegerVariable[] {a, b, c, d, e};
        p.add(new Cycle(vars));
        p.add(new AllDifferent_SPARSE(vars));
        p.setVariables(vars);
        Solver s = new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {1, 2, 3, 4, 0},
            {1, 3, 4, 2, 0}
        })));
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(CycleTest.class);
}
/*
 * $Log$
 * Revision 1.8  2005/07/12 13:31:27  yan
 * using new AllDifferent_SPARSE
 *
 * Revision 1.7  2004/09/17 12:47:23  yan
 * tests now use add and setVariables
 *
 * Revision 1.6  2004/09/17 09:33:02  yan
 * using deepAdd instead of add
 *
 * Revision 1.5  2004/06/16 13:15:17  yan
 * added Permutation
 *
 * Revision 1.4  2002/10/25 09:00:58  yan
 * cleaned category
 *
 * Revision 1.3  2002/05/02 16:21:22  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
