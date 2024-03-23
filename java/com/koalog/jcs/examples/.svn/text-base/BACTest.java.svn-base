package com.koalog.jcs.examples;

import junit.framework.TestCase;

import org.apache.log4j.Category;

import com.koalog.jcs.optimizer.Minimizer;

/**
 * Tests the Balanced Academic Curriculum.
 * 
 * @author Yan Georget
 */
public class BACTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public BACTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests instance 1.
     */
    public void testBAC1() {
        cat.info("testBAC1");
        Minimizer m = new BACMinimizer(new BACProblem(BACProblem.INSTANCE1));
        m.optimize();
        assertTrue(m.getOptimum() == 17);
    }

    /**
     * Runs instance 2.
     */
    public void runBAC2() {
        cat.info("testBAC2");
        Minimizer m = new BACMinimizer(new BACProblem(BACProblem.INSTANCE2));
        m.optimize();
    }

   //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(BACTest.class);
}
