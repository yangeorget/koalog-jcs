package com.koalog.jcs.constraint.set;

import java.util.Arrays;
import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.solver.SplitSolver;
import com.koalog.jcs.solver.KeepOrderVariableHeuristic;
import com.koalog.jcs.solver.FromSetMinDomainHeuristic;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.variable.SetVariable;
import com.koalog.jcs.domain.SetDomain;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;

/**
 * Tests the Card class.
 * @author Yan Georget
 */
public class CardTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public CardTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
     /**
     * Tests the class.
     */
    public void testCard() {
        cat.info("testCard");
        Problem p = new BaseProblem();
        IntegerVariable x = new IntegerVariable("x", -3, 3);
        SetVariable y = 
            new SetVariable("y", 
                            new SetDomain(Arrays.asList(new Integer[] {
                                new Integer(0)
                            }),
                                          Arrays.asList(new Integer[] {
                                              new Integer(0), 
                                              new Integer(1), 
                                              new Integer(2), 
                                              new Integer(3)
                                          })));

        p.add(new Card(x, y));
        p.setVariables(new SetVariable[] {y});
        Solver s = new SplitSolver(p,
                                   new KeepOrderVariableHeuristic(),
                                   new FromSetMinDomainHeuristic());
        s.solve();
        assertTrue(s.getSolutions().size() == 7);
    }
    
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(EqTest.class);
}
/*
 * $Log$
 * Revision 1.4  2004/09/17 10:05:56  yan
 * changed internalAdd into add
 *
 * Revision 1.3  2004/09/17 09:34:42  yan
 * using deepAdd instead of add
 *
 * Revision 1.2  2003/06/26 17:15:56  yan
 * fixed imports
 *
 * Revision 1.1  2003/06/23 17:16:14  yan
 * initial revision
 *
 * Revision 1.2  2003/06/19 15:06:11  yan
 * added assertion
 *
 * Revision 1.1  2003/06/19 15:00:16  yan
 * initial revision
 *
 */
