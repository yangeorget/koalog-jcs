package com.koalog.jcs.domain;

import junit.framework.TestCase;
import org.apache.log4j.Category;

/**
 * Tests the MinMaxDomain class.
 * @author Yan Georget
 */
public class MinMaxDomainTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public MinMaxDomainTest(String name) {
        super(name);
    }
   
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests various methods.
     */
    public void testMinMaxDomain() {
        cat.info("testMinMaxDomain");
        MinMaxDomain d1 = new MinMaxDomain(1,6);
        assertTrue(d1.toSet().size() == 6);
        assertTrue(d1.contains(4));
        assertTrue(!d1.contains(7));
        d1.setValue(4);
        assertTrue(d1.equals(new MinMaxDomain(4)));
        MinMaxDomain d2 = new MinMaxDomain(1,6);
        MinMaxDomain d3 = new MinMaxDomain(2,5);
        SparseDomain d4 = new SparseDomain(d3);
        assertTrue(d3.equals(d4));
        assertTrue(d2.intersects(d4));
        d2.intersect(d4);
        assertEquals(2, d2.getMin());
        assertEquals(5, d2.getMax());
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(MinMaxDomainTest.class);
}
/*
 * $Log$
 * Revision 1.8  2004/11/23 13:08:20  yan
 * fixed test
 *
 * Revision 1.7  2004/04/13 15:30:47  yan
 * remove methods are not available in MinMaxDomain anymore
 *
 * Revision 1.6  2003/04/21 10:02:55  yan
 * increased coverage
 *
 * Revision 1.5  2003/03/27 11:08:26  yan
 * allowing values removal
 *
 * Revision 1.4  2002/11/18 11:49:51  yan
 * added test for remove
 *
 * Revision 1.3  2002/10/25 09:03:14  yan
 * cleaned category
 *
 * Revision 1.2  2002/10/07 10:17:40  yan
 * fixed style
 *
 * Revision 1.1  2002/09/16 08:10:56  yan
 * initial revision
 *
 */
