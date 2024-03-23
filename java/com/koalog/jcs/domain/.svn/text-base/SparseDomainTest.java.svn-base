package com.koalog.jcs.domain;

import java.util.Arrays;
import java.util.Iterator;
import junit.framework.TestCase;
import org.apache.log4j.Category;

/**
 * Tests the SparseDomain class.
 * @author Yan Georget
 */
public class SparseDomainTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public SparseDomainTest(String name) {
        super(name);
    }
   
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests various methods.
     */
    public void testSparseDomain() {
        cat.info("testSparseDomain");
        SparseDomain d1 = new SparseDomain(new int[] {1, 3, 6, 10, 12});
        assertTrue(Arrays.equals(d1.toArray(),new int[] {1, 3, 6, 10, 12}));
        SparseDomain d3 = new SparseDomain(1, 6);
        assertTrue(Arrays.equals(d3.toArray(),new int[] {1, 2, 3, 4, 5, 6}));
        assertTrue(d3.toSet().size() == 6);
        assertTrue(d3.contains(3));
        assertTrue(!d3.contains(0));
        d1.intersect(d3);
        assertTrue(Arrays.equals(d1.toArray(),new int[] {1, 3, 6}));
        assertTrue(d1.intersects(new MinMaxDomain(3,4)));
        assertTrue(!d1.intersects(new MinMaxDomain(4,5)));
    }

    /**
     * Tests the iterator.
     */
    public void testIterator() {
        SparseDomain d = new SparseDomain(new int[] {1, 3, 5, 7, 9});
        Iterator i = d.iterator();
        assertEquals(new Integer(1), i.next());
        assertEquals(new Integer(3), i.next());
        i.remove();
        assertTrue(Arrays.equals(d.toArray(),new int[] {1, 5, 7, 9}));
        assertEquals(new Integer(5), i.next());
        assertEquals(new Integer(7), i.next());
        assertEquals(new Integer(9), i.next());
        i.remove();
        assertTrue(Arrays.equals(d.toArray(),new int[] {1, 5, 7}));
        SparseDomain d2 = new SparseDomain(new int[] {2, 4, 6});
        Iterator j = d2.iterator();
        assertEquals(new Integer(2), j.next());
        j.remove();
        assertEquals(new Integer(4), j.next());
        j.remove();
        cat.info(d2);
        assertTrue(d2.isSingleton());
        j.next();
        j.remove();
        assertTrue(d2.isEmpty());
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(SparseDomainTest.class);
}
/*
 * $Log$
 * Revision 1.8  2004/11/25 09:37:33  yan
 * fixed style
 *
 * Revision 1.7  2004/11/23 13:21:56  yan
 * fixed test
 *
 * Revision 1.6  2004/11/23 13:08:41  yan
 * fixed test
 *
 * Revision 1.5  2002/10/25 09:03:14  yan
 * cleaned category
 *
 * Revision 1.4  2002/09/16 08:04:06  yan
 * *** empty log message ***
 *
 * Revision 1.3  2002/05/02 16:15:22  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
