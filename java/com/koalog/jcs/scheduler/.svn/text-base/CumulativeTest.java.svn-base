package com.koalog.jcs.scheduler;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.InconsistencyException;
import com.koalog.jcs.solver.BacktrackSolver;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.constraint.DefaultConstraintScheduler;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.domain.MinMaxDomain;

/**
 * Tests the Cumulative class.
 * @author Yan Georget
 */
public class CumulativeTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public CumulativeTest(String name) {
        super(name);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class.
     * <P>Simulates AllDifferent.</P>
     */
    public void testCumulative1() {
        cat.info("testCumulative1");
        Problem p = new BaseProblem();
        Task t1 = new Task("x", 1, 0, 3);
        Task t2 = new Task("y", 1, 0, 3);
        Task t3 = new Task("z", 1, 0, 3);
        p.add(new Cumulative(new Task[] {t1, t2, t3}, 1));
        p.setVariables(new Task[] {t1, t2, t3});
        BacktrackSolver s =  new DefaultSplitSolver(p);
        s.setConstraintScheduler(new DefaultConstraintScheduler());
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {0,1,2}, 
            {0,2,1}, 
            {1,0,2}, 
            {1,2,0},
            {2,0,1}, 
            {2,1,0}})));
    }

    /**
     * Tests the class.
     * <P>Resource of capacity 1.</P>
     */
    public void testCumulative2() {
        cat.info("testCumulative2");
        Problem p = new BaseProblem();
        Task t1 = new Task("x", 4, 0, 7);
        Task t2 = new Task("y", 2, 0, 7);
        Task t3 = new Task("z", 1, 0, 7);
        p.add(new Cumulative(new Task[] {t1, t2, t3}, 1));
        p.setVariables(new Task[] {t1, t2, t3});
        BacktrackSolver s =  new DefaultSplitSolver(p);
        s.setConstraintScheduler(new DefaultConstraintScheduler());
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {0, 4, 6},
            {0, 5, 4},
            {1, 5, 0},
            {2, 0, 6},
            {3, 0, 2},
            {3, 1, 0}
        })));
    }

    /**
     * Tests the filtering.
     * <P>Resource of capacity 1.</P>
     */
    public void testCumulative2bis() {
        cat.info("testCumulative2bis");
        Problem p = new BaseProblem();
        Task t1 = new Task("x", 3, 0, 4);
        Task t2 = new Task("y", 3, 0, 7);
        p.add(new Cumulative(new Task[] {t1, t2}, 1));
        p.setVariables(new Task[] {t1, t2});
        try {
            BacktrackSolver s = new DefaultSplitSolver(p);
            s.initialFilter();
            assertEquals(0, t1.getReleaseMin());
            assertEquals(1, t1.getReleaseMax());
            assertEquals(3, t2.getReleaseMin());
            assertEquals(4, t2.getReleaseMax());
        } catch (InconsistencyException ie) {
            assertTrue(false);
        }
    }

    /**
     * Tests the class.
     */
    public void testCumulative3() {
        cat.info("testCumulative3");
        Problem p = new BaseProblem();
        Task t1 = new Task("x", 2, 0, 6);
        Task t2 = new Task("y", 4, 0, 6);
        Task t3 = new Task("z", 6, 0, 6);
        p.add(new Cumulative(new Task[] {t1, t2, t3}, 2));
        p.setVariables(new Task[] {t1, t2, t3});
        BacktrackSolver s =  new DefaultSplitSolver(p);
        s.setConstraintScheduler(new DefaultConstraintScheduler());
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {0, 2, 0},
            {4, 0, 0}
        })));
    }        

    /**
     * Tests the filtering.
     */
    public void testCumulative4() {
        cat.info("testCumulative4");
        Problem p = new BaseProblem();
        Task t1 = new Task("x", 2, 0, 5);
        Task t2 = new Task("y", 4, 0, 6);
        Task t3 = new Task("z", 6, 0, 6);
        p.add(new Cumulative(new Task[] {t1, t2, t3}, 2));
        p.setVariables(new Task[] {t1, t2, t3});
        try {
            new DefaultSplitSolver(p).initialFilter();
            assertTrue(t1.isInstantiated());
            assertTrue(t1.getReleaseMin() == 0);
            assertTrue(t2.isInstantiated());
            assertTrue(t2.getReleaseMin() == 2);
            assertTrue(t3.isInstantiated());
            assertTrue(t3.getReleaseMin() == 0);
        } catch (InconsistencyException ie) {
            assertTrue(false);
        }
    }        
    
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(CumulativeTest.class);
}
/*
 * $Log$
 * Revision 1.10  2005/07/22 14:52:01  yan
 * removed unused log statement
 *
 * Revision 1.9  2004/09/17 12:49:04  yan
 * tests now use add and setVariables
 *
 * Revision 1.8  2004/09/17 09:36:08  yan
 * using deepAdd instead of add
 *
 * Revision 1.7  2004/07/13 23:15:50  yan
 * fixed style
 *
 * Revision 1.6  2004/07/10 16:17:46  yan
 * adapted to Task new API
 *
 * Revision 1.5  2004/06/24 09:56:47  yan
 * added log
 *
 * Revision 1.4  2004/04/09 14:44:04  yan
 * removal of events
 *
 * Revision 1.3  2004/04/01 19:19:10  yan
 * moved filter methods
 *
 * Revision 1.2  2003/04/03 15:25:03  yan
 * fixed style
 *
 * Revision 1.1  2003/04/03 15:13:27  yan
 * initial revision
 *
 */
