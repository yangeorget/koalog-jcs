package com.koalog.jcs.constraint;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.variable.Variable;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.solver.BacktrackSolver;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.constraint.arithmetic.Add;
import com.koalog.jcs.constraint.arithmetic.Cycle;
import com.koalog.jcs.constraint.bool.True;
import com.koalog.jcs.constraint.arithmetic.Mul;

/**
 * Tests the Problem class.
 * @author Yan Georget
 */
public class ProblemTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
     public ProblemTest(String name) {
        super(name);
    }
   
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the toString method.
     */
    public void testToString() {
        cat.info("testToString");
        // --- code by R. Stansbury:
        BaseProblem b = new BaseProblem("test");
        b.flattenConstraints();
        b.toString();
        // ------------------------
        assertTrue(true);
    }

  
    /**
     * Tests the add method.
     */
    public void testAdd() {
        cat.info("testAdd");
        BooleanVariable x = new BooleanVariable();
        AbstractRelation r0 = new Cycle(new IntegerVariable[] {x, x});
        AbstractRelation r1 = new True(x);
        BaseProblem p = new BaseProblem();
        p.add(r1);
        assertEquals("the problem should contain 1 relation", 
                     1,
                     p.relations.size());
        p.add(r0);
        assertEquals("the problem should contain 2 relations", 
                     2,
                     p.relations.size());
    }

    /**
     * Tests the scheduler.
     */
    public void testScheduler() {
        cat.info("testScheduler");
        Problem p1 = new BaseProblem();
        IntegerVariable d1 = new IntegerVariable("d1",-2,2);
        IntegerVariable x1 = new IntegerVariable("x1",-2,2);
        IntegerVariable y1 = new IntegerVariable("y1",-2,2);
        p1.add(new Add(x1,d1,y1));
        p1.add(new Mul(x1,2,y1));
        p1.add(new Mul(x1,2,d1));
        p1.add(new Mul(x1,0,y1));
        p1.setVariables(new Variable[] {x1,d1,y1});
        BacktrackSolver s1 = new DefaultSplitSolver(p1);
        s1.solve();
        BaseProblem p2 = new BaseProblem();
        IntegerVariable d2 = new IntegerVariable("d2",-2,2);
        IntegerVariable x2 = new IntegerVariable("x2",-2,2);
        IntegerVariable y2 = new IntegerVariable("y2",-2,2);
        p2.add(new Add(x2,d2,y2));
        p2.add(new Mul(x2,2,y2));
        p2.add(new Mul(x2,2,d2));
        p2.add(new Mul(x2,0,y2));
        p2.setVariables(new Variable[] {x2,d2,y2});
        BacktrackSolver s2 = new DefaultSplitSolver(p2);
        s2.setConstraintScheduler(new DefaultConstraintScheduler());
        s2.solve();
        assertTrue(s1.getSolutions().equals(s2.getSolutions()));
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(ProblemTest.class);
}
/*
 * $Log$
 * Revision 1.24  2004/10/08 10:19:13  yan
 * fixed testAdd
 *
 * Revision 1.23  2004/09/17 12:48:17  yan
 * tests now use add and setVariables
 *
 * Revision 1.22  2004/09/17 10:04:59  yan
 * changed internalAdd into add
 *
 * Revision 1.21  2004/09/17 09:33:02  yan
 * using deepAdd instead of add
 *
 * Revision 1.20  2004/05/05 22:48:04  yan
 * added a simple test for the add method
 *
 * Revision 1.19  2004/04/09 14:42:15  yan
 * removal of events
 *
 * Revision 1.18  2004/04/01 19:15:31  yan
 * problem is not a constraint anymore
 *
 * Revision 1.17  2004/03/14 18:03:22  yan
 * changed filter API
 *
 * Revision 1.16  2003/10/02 19:32:39  yan
 * removed broken tests
 *
 * Revision 1.15  2003/04/03 15:10:36  yan
 * fixed testEvents method
 *
 * Revision 1.14  2003/04/01 16:59:39  yan
 * added tests
 *
 * Revision 1.13  2003/04/01 13:53:15  yan
 * various fixes
 *
 * Revision 1.12  2003/03/27 10:53:41  yan
 * fixed wrt events
 *
 * Revision 1.11  2003/03/09 20:07:14  yan
 * flat properties
 *
 * Revision 1.10  2003/03/07 11:18:35  yan
 * fixed style
 *
 * Revision 1.9  2003/02/04 19:21:36  yan
 * reusing constraint scheduler
 *
 * Revision 1.8  2003/02/04 17:04:08  yan
 * removed test on complexity
 *
 * Revision 1.7  2002/11/18 14:17:55  yan
 * improved Scheduler test
 *
 * Revision 1.6  2002/11/18 11:51:28  yan
 * added test for DefaultConstraintScheduler
 *
 * Revision 1.5  2002/10/25 08:59:27  yan
 * cleaned category
 *
 * Revision 1.4  2002/10/08 17:20:25  yan
 * added complexity property
 *
 * Revision 1.3  2002/05/02 16:24:38  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
