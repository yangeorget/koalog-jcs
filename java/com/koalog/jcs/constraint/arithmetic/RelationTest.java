package com.koalog.jcs.constraint.arithmetic;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.domain.MinMaxDomain;

/**
 * Tests the Relation class.
 * @author Yan Georget
 */
public class RelationTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public RelationTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class.
     */    
    public void testRelation() {
        cat.info("testRelation");
        Problem p = new BaseProblem();
        IntegerVariable[] vars = new IntegerVariable[] {
            new IntegerVariable("x",0,3),
            new IntegerVariable("y",0,3),
            new IntegerVariable("z",1,8)
        };
        p.add(new Relation(vars,
                                   new int[][] {
                                       {0,1,0},
                                       {0,2,0},
                                       {0,3,0},
                                       {1,1,1},
                                       {1,2,2},
                                       {1,3,3},
                                       {2,1,2},
                                       {2,2,4},
                                       {2,3,6},
                                       {3,1,3},
                                       {3,2,6},
                                       {3,3,9}
                                   }));
        p.setVariables(vars);
        Solver s = new DefaultSplitSolver(p);
        s.solve();
        assertTrue(s.getSolutions().equals(MinMaxDomain.toDomains(new int[][] {
            {1,1,1},
            {1,2,2},
            {1,3,3},
            {2,1,2},
            {2,2,4},
            {2,3,6},
            {3,1,3},
            {3,2,6},
        })));
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(RelationTest.class);
}
/*
 * $Log$
 * Revision 1.3  2004/09/17 10:05:24  yan
 * changed internalAdd into add
 *
 * Revision 1.2  2004/09/17 09:33:02  yan
 * using deepAdd instead of add
 *
 * Revision 1.1  2003/09/30 14:28:26  yan
 * initial revision
 *
 */
