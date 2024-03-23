package com.koalog.jcs.variable;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.domain.MinMaxDomain;

/**
 * Tests the FalseBooleanVariable class.
 * @author Yan Georget
 */
public class FalseBooleanVariableTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public FalseBooleanVariableTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class. 
     */
    public void testFalseBooleanVariable() {
        cat.info("testFalseBooleanVariable");
        assertTrue(((MinMaxDomain) new FalseBooleanVariable()
            .getDomain()).getMax() == 0);
        assertTrue(((MinMaxDomain) new FalseBooleanVariable("dummy")
            .getDomain()).getMax() == 0);
    }
    
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(FalseBooleanVariableTest.class);
}
/*
 * $Log$
 * Revision 1.3  2002/10/25 09:10:34  yan
 * fixed bug in category used
 *
 * Revision 1.2  2002/10/25 09:05:21  yan
 * cleaned category
 *
 * Revision 1.1  2002/09/16 08:10:57  yan
 * initial revision
 *
 */
