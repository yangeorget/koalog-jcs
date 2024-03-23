package com.koalog.jcs.constraint.arithmetic;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.InconsistencyException;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.domain.MinMaxDomain;
import com.koalog.jcs.domain.SparseDomain;

/**
 * Tests the AllDifferent class.
 * @author Yan Georget
 */
public class AllDifferentTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public AllDifferentTest(String name) {
        super(name);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class AllDifferent_SPARSE.
     */
    public void testAllDifferent_SPARSE1() {
        cat.info("testAllDifferent_SPARSE1");
        Problem p = new BaseProblem();
        IntegerVariable[] vars = new IntegerVariable[] {
            new IntegerVariable("x", new SparseDomain(0,1)),
            new IntegerVariable("y", new SparseDomain(0,1)),
            new IntegerVariable("z", new SparseDomain(0,1))
        };
        p.add(new AllDifferent_SPARSE(vars));
        p.setVariables(vars);
        try {
            new DefaultSplitSolver(p).initialFilter();
            assertTrue(false);
        } catch (InconsistencyException ie) {
            assertTrue(true);
        }
    }

    /**
     * Tests the class AllDifferent_SPARSE.
     */
    public void testAllDifferent_SPARSE3() {
        cat.info("testAllDifferent_SPARSE3");
        Problem p = new BaseProblem();
        IntegerVariable[] vars = new IntegerVariable[] {
            new IntegerVariable("x", new SparseDomain(-1,1)),
            new IntegerVariable("y", new SparseDomain(-1,1)),
            new IntegerVariable("z", new SparseDomain(-1,1))
        };
        p.add(new AllDifferent_SPARSE(vars));
        p.setVariables(vars);
        Solver s =  new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {-1,0,1}, 
            {-1,1,0}, 
            {0,-1,1}, 
            {0,1,-1}, 
            {1,-1,0}, 
            {1,0,-1}
        })));
    }

    /**
     * Tests the class AllDifferent_SPARSE.
     */
    public void testAllDifferent_SPARSE4() {
        cat.info("testAllDifferent_SPARSE4");
        IntegerVariable[] vars = new IntegerVariable[] {
            new IntegerVariable("x", new SparseDomain(new int[] {0,1,2})),
            new IntegerVariable("y", new SparseDomain(new int[] {0,1,2})),
            new IntegerVariable("z", new SparseDomain(new int[] {0,1,2})),
            new IntegerVariable("t", new SparseDomain(new int[] {0,1,2,3}))
        };
        Problem p = new BaseProblem();
        p.add(new AllDifferent_SPARSE(vars));
        p.setVariables(vars);
        Solver s = new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][]{
            {0,1,2,3}, 
            {0,2,1,3}, 
            {1,0,2,3}, 
            {1,2,0,3}, 
            {2,0,1,3}, 
            {2,1,0,3}
        })));
    }    

    /**
     * Tests the class AllDifferent.
     */
    public void testAllDifferent() {
        cat.info("testAllDifferent");
        Problem p = new BaseProblem();
        IntegerVariable[] vars = new IntegerVariable[] {
            new IntegerVariable("x",-1,1),
            new IntegerVariable("y",-1,1),
            new IntegerVariable("z",-1,1)
        };
        p.add(new AllDifferent(vars));
        p.setVariables(vars);
        Solver s =  new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {-1,0,1}, 
            {-1,1,0}, 
            {0,-1,1}, 
            {0,1,-1}, 
            {1,-1,0}, 
            {1,0,-1}
        })));
    }
    
    /**
     * Tests the class AllDifferent.
     */
    public void testAllDifferent_HALL() {
        cat.info("testAllDifferent_HALL");
        Problem p = new BaseProblem();
        int n=1600;
        IntegerVariable[] vars = new IntegerVariable[2*n+1];
        for (int i=0; i<n; i++) {
            vars[i] = new IntegerVariable(i-n,0);
        }
        vars[n] = new IntegerVariable(0);
        for (int i=n+1; i<=2*n; i++) {
            vars[i] = new IntegerVariable(0,i-n);
        }
        p.add(new AllDifferent(vars));
        p.setVariables(vars);
        Solver s =  new DefaultSplitSolver(p);
        s.solve(1);
        assertTrue(s.getSolutions().size() == 1);
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(AllDifferentTest.class);
}
/*
 * $Log$
 * Revision 1.14  2005/07/13 12:34:19  yan
 * fixed style
 *
 * Revision 1.13  2005/07/13 08:38:36  yan
 * avoiding the use of complex data structures
 *
 * Revision 1.12  2005/07/12 15:57:52  yan
 * optimized AllDifferent_SPARSE
 *
 * Revision 1.11  2005/07/12 13:32:15  yan
 * added test for new AllDifferent_SPARSE
 *
 * Revision 1.10  2004/09/17 12:47:23  yan
 * tests now use add and setVariables
 *
 * Revision 1.9  2004/09/17 09:33:02  yan
 * using deepAdd instead of add
 *
 * Revision 1.8  2004/05/05 11:46:48  yan
 * fixed style
 *
 * Revision 1.7  2004/04/09 14:42:50  yan
 * removal of events
 *
 * Revision 1.6  2003/03/27 11:01:48  yan
 * added events related method (updateConstraints)
 *
 * Revision 1.5  2003/03/09 17:01:39  yan
 * added test
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
