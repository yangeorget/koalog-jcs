package com.koalog.jcs.examples;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.domain.MinMaxDomain;

/**
 * Tests the partition problem.
 *
 * @author Yan Georget
 */
public class PartitionTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
      public PartitionTest(String name) {
        super(name);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests Partition(8).
     */
    public void testPartition8() {
        cat.info("testPartition8");
        Solver s = new DefaultSplitSolver(new PartitionProblem(8));
        s.solve(1);
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {0, 1, 1, 0, 1, 0, 0, 1}
        })));
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(PartitionTest.class);
}
/*
 * $Log$
 * Revision 1.10  2004/09/15 12:51:44  yan
 * using basic solver
 *
 * Revision 1.9  2004/05/05 19:36:30  yan
 * externalized problems
 *
 * Revision 1.8  2004/05/05 11:50:10  yan
 * *** empty log message ***
 *
 * Revision 1.7  2003/04/27 17:47:04  yan
 * fixed style
 *
 * Revision 1.6  2002/10/25 09:05:20  yan
 * cleaned category
 *
 * Revision 1.5  2002/10/08 17:17:22  yan
 * various optimizations and cleaning
 *
 * Revision 1.4  2002/05/02 16:16:52  yan
 * moved
 *
 * Revision 1.3  2002/04/22 12:46:45  yan
 * modifications to compile with sun jdk
 *
 * Revision 1.2  2002/04/19 09:53:44  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
