package com.koalog.jcs.domain;

import java.util.Collections;
import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.util.list.Lists;

/**
 * Tests the SetDomain class.
 * @author Yan Georget
 */
public class SetDomainTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public SetDomainTest(String name) {
        super(name);
    }
   
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests various methods.
     */
    public void testDomainInterface() {
        cat.info("testDomainInterface");
        // tests equals
        SetDomain sd1 = new SetDomain(Collections.EMPTY_SET, 
                                      Lists.toN(2)); // {}..{0,1,2}
        SetDomain sd2 = new SetDomain(Collections.EMPTY_SET, 
                                      Lists.toN(2)); // {}..{0,1,2}
        assertTrue(sd1.equals(sd2));
        // tests copy
        assertTrue(sd1.equals((Domain) sd1.copy()));
        // tests isSingleton
        assertFalse(sd1.isSingleton());
        SetDomain sd = new SetDomain(Collections.EMPTY_SET); // {{}}
        assertTrue(sd.isSingleton());
        // tests isEmpty
        sd = new SetDomain(sd1.getMax(), Collections.EMPTY_SET);
        assertTrue(sd.isEmpty());
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(SetDomainTest.class);
}
/*
 * $Log$
 * Revision 1.5  2004/06/14 15:55:52  yan
 * using storable references and values
 *
 * Revision 1.4  2003/07/31 16:41:30  yan
 * SetDomain's sets are now sorted
 *
 * Revision 1.3  2003/06/18 11:10:56  yan
 * fixed constructors
 *
 * Revision 1.2  2003/06/18 11:03:04  yan
 * fixed style
 *
 * Revision 1.1  2003/06/17 17:07:19  yan
 * initial revision
 *
 */
