package com.koalog.jcs.variable;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.domain.MinMaxDomain;

/**
 * Tests the TrueBooleanVariable class.
 * @author Yan Georget
 */
public class TrueBooleanVariableTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public TrueBooleanVariableTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class. 
     */
    public void testTrueBooleanVariable() {
        cat.info("testTrueBooleanVariable");
        assertTrue(((MinMaxDomain) new TrueBooleanVariable()
            .getDomain()).getMin() == 1);
        assertTrue(((MinMaxDomain) new TrueBooleanVariable("dummy")
            .getDomain()).getMin() == 1);
    }
    
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(TrueBooleanVariableTest.class);
}
/*
 * $Log$
 * Revision 1.2  2002/10/25 09:05:21  yan
 * cleaned category
 *
 * Revision 1.1  2002/09/16 08:10:57  yan
 * initial revision
 *
 */
