package com.koalog.jcs.examples;

import junit.framework.TestCase;
import org.apache.log4j.Category;
import com.koalog.jcs.optimizer.Maximizer;

/**
 * Tests the Knapsack problem.
 *
 * @author Yan Georget
 */
public class KnapsackTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public KnapsackTest(String name) {
        super(name);
    }
    
    /**
     * Tests an instance.
     */
    public void testKnapsack() {
        cat.info("testKnapsack");
        int[] volumes = new int[] {
            40,40,38,38,36,36,34,34,32,32,30,30,28,28,26,26,24,24,22,22
        }; 
        int[] weights = new int[] {
            40,40,38,38,36,36,34,34,32,32,30,30,28,28,26,26,24,24,22,22
        };
        Maximizer m = new KnapsackMaximizer(new KnapsackProblem(volumes, 
                                                                weights, 
                                                                55));
        m.optimize();
        assertEquals(54, m.getOptimum());
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(KnapsackTest.class);
}
/*
 * $Log$
 * Revision 1.10  2005/07/27 20:33:30  yan
 * added test
 *
 * Revision 1.9  2004/05/05 19:36:30  yan
 * externalized problems
 *
 * Revision 1.8  2003/04/27 17:47:04  yan
 * fixed style
 *
 * Revision 1.7  2002/12/10 10:33:56  yan
 * fixed pb
 *
 * Revision 1.6  2002/10/25 09:05:20  yan
 * cleaned category
 *
 * Revision 1.5  2002/10/01 14:32:46  yan
 * cleaned a bit
 *
 * Revision 1.4  2002/06/16 13:56:48  yan
 * *** empty log message ***
 *
 * Revision 1.3  2002/05/02 16:16:52  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:44  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
