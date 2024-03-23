package com.koalog.jcs.examples;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.domain.MinMaxDomain;

/**
 * Tests the magic sequence problem.
 *
 * @author Yan Georget
 */
public class MagicSequenceTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
      public MagicSequenceTest(String name) {
        super(name);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests with n=50.
     */
    public void testMagicSequence50() {
        cat.info("testMagicSequence50");
        Solver s = new DefaultSplitSolver(new MagicSequenceProblem(50));
        s.solve(2);
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][]{{
            0, 0, 0, 1, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 1, 2, 46
        }})));
    }

    /**
     * Tests with n=100.
     */
    public void testMagicSequence100() {
        cat.info("testMagicSequence100");
        Solver s = new DefaultSplitSolver(new MagicSequenceProblem(100));
        s.solve(2);
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][]{{
            0, 0, 0, 1, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 1, 2, 96
        }})));
    }

    /**
     * Tests with n=200.
     */
    public void testMagicSequence200() {
        cat.info("testMagicSequence200");
        Solver s = new DefaultSplitSolver(new MagicSequenceProblem(200));
        s.solve(2);
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][]{{
            0, 0, 0, 1, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 1, 2, 196
        }})));
    }

    /**
     * Runs with n=800.
     */
    public void runMagicSequence800() {
        cat.info("runMagicSequence");
        Solver s = new DefaultSplitSolver(new MagicSequenceProblem(800));
        s.solve(1);
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(MagicSequenceTest.class);
}
/*
 * $Log$
 * Revision 1.10  2004/11/09 15:44:06  yan
 * added tests
 *
 * Revision 1.9  2004/09/15 12:49:23  yan
 * renamed run method
 *
 * Revision 1.8  2004/06/10 14:46:30  yan
 * fixed doc
 *
 * Revision 1.7  2004/05/15 17:25:40  yan
 * fixed style
 *
 * Revision 1.6  2004/05/06 17:08:00  yan
 * optimized
 *
 * Revision 1.5  2004/05/05 19:36:30  yan
 * externalized problems
 *
 * Revision 1.4  2003/07/12 17:57:57  yan
 * improved modelling
 *
 * Revision 1.3  2003/04/27 17:47:04  yan
 * fixed style
 *
 * Revision 1.2  2003/03/27 11:58:37  yan
 * using add(Collection)
 *
 * Revision 1.1  2002/11/26 13:11:10  yan
 * initial revision
 *
 */
