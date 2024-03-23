package com.koalog.jcs.solver;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.domain.SparseDomain;

/**
 * Tests the RandomOrderDomainHeuristic class.
 * @author Yan Georget
 */
public class RandomOrderDomainHeuristicTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
     public RandomOrderDomainHeuristicTest(String name) {
        super(name);
    }
   
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class.
     */
    public void testRandomOrderDomainHeuristic() {
        cat.info("testRandomOrderDomainHeuristic");
        Problem p = new BaseProblem();
        IntegerVariable x = new IntegerVariable("x", new SparseDomain(-3, 3));
        IntegerVariable y = new IntegerVariable("y", new SparseDomain(-3, 3));
        IntegerVariable z = new IntegerVariable("z", new SparseDomain(-3, 3));
        p.setVariables(new IntegerVariable[] {x,y,z});
        BacktrackSolver s = new SplitSolver(p, 
                                            new KeepOrderVariableHeuristic(), 
                                            new RandomOrderDomainHeuristic());
        s.solve();
        assertEquals(7*7*7, s.getSolutions().size());
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(RandomOrderDomainHeuristicTest.class);
}
/*
 * $Log$
 * Revision 1.1  2005/09/23 07:51:58  yan
 * initial revision
 *
 */
