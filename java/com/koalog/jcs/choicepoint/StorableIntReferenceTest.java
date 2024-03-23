package com.koalog.jcs.choicepoint;

import junit.framework.TestCase;
import org.apache.log4j.Category;

/**
 * Tests the StorableIntReference class.
 *.
 * @author Yan Georget
 */
public class StorableIntReferenceTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
     public StorableIntReferenceTest(String name) {
        super(name);
    }
   
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class.
     */
    public void testStorableIntReference() {
        ChoicePointStack cp = new BaseChoicePointStack();
        StorableIntReference x = new StorableIntReference(1);
        cp.memorize(x);
        assertEquals(1, x.getIntValue());
        x.setIntValue(2);
        assertEquals(2, x.getIntValue());
        cp.pop(null);
        assertEquals(1, x.getIntValue());
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(StorableIntReferenceTest.class);
}
/*
 * $Log$
 * Revision 1.1  2004/06/15 14:07:30  yan
 * initial revision
 *
 */
