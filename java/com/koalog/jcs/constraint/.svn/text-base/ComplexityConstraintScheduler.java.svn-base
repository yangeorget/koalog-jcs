package com.koalog.jcs.constraint;

import java.util.LinkedList;
import org.apache.log4j.Category;
import com.koalog.util.heap.Heap;
import com.koalog.jcs.choicepoint.ChoicePointStack;

/**
 * This class is a constraint scheduler based on constraint complexities.
 *
 * @see Constraint
 * @author Yan Georget
 */
public class ComplexityConstraintScheduler extends BaseConstraintScheduler {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(ComplexityConstraintScheduler.class);

    //------------------------------------------------------------------------
    // CONSTANTS
    //------------------------------------------------------------------------
    /** The default capacity of the scheduler. */
    public static final int DEFAULT_CAPACITY = 65536;

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The heap of the constraints of high complexities. */
    private Heap high;
    /** The dequeue of the constraints of low complexities. */
    private LinkedList low;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Main constructor.
     */
    public ComplexityConstraintScheduler() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Auxilliary constructor.
     * @param capacity an int
     * @since 2.1
     */
    public ComplexityConstraintScheduler(int capacity) {
        super();
        low = new LinkedList();
        high = new Heap(capacity) {
                public final boolean smaller(Object i, Object j) {
                    return ((Constraint) i).getComplexity() 
                        < ((Constraint) j).getComplexity();
                }   

            };
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.ConstraintScheduler */
    public void clear() {
        super.clear();
        low.clear();
        high.clear();
    }

    /** 
     * Converts the constraint scheduler to a string.
     * @return a string 
     */
    public String toString() {
        StringBuffer buf = new StringBuffer(low.toString());
        buf.append(high);
        return buf.toString();
    }

    /** @see com.koalog.jcs.constraint.ConstraintScheduler */
    public Constraint pop(ChoicePointStack cp) {
        if (low.isEmpty()) {
            constraint = (Constraint) high.pop();
            if (constraint != null) {
                constraint.setDateScheduled(-1);
            }
        } else { // low not empty
            constraint = (Constraint) low.removeFirst();
            constraint.setDateScheduled(-1);
        }        
        return constraint;
    }

    /** @see com.koalog.jcs.constraint.ConstraintScheduler */
    protected void internalAdd(Constraint c) {
        // we know that c is not in the scheduler
        if (c.hasConstantComplexity()) {
            // LIFO
            low.addLast(c);
        } else {
            // TODO: put a try/catch here?
            high.add(c);
        }
        c.setDateScheduled(date); 
    }
}
/*
 * $Log$
 * Revision 1.24  2005/09/20 17:32:20  yan
 * fixes according to the ones in base class
 *
 * Revision 1.23  2004/11/10 09:09:08  yan
 * using queue differently
 *
 * Revision 1.22  2004/09/16 12:24:59  yan
 * cleaned API for adding constraints
 *
 * Revision 1.21  2004/06/30 16:43:37  yan
 * factorized some code
 *
 * Revision 1.20  2004/06/29 17:09:45  yan
 * fixed doc
 *
 * Revision 1.19  2004/06/29 16:40:36  yan
 * FIFO for low complexity constraints
 *
 * Revision 1.18  2004/06/29 16:08:59  yan
 * avoiding use of stacks
 *
 * Revision 1.17  2004/06/24 14:19:38  yan
 * set of scheduled constraints encoded in the constraints
 *
 * Revision 1.16  2004/06/17 13:55:09  yan
 * VERSION
 *
 * Revision 1.15  2004/06/16 09:52:37  yan
 * added a bigger default capacity
 *
 * Revision 1.14  2004/05/06 17:07:36  yan
 * bigger heap
 *
 * Revision 1.13  2004/04/09 14:42:15  yan
 * removal of events
 *
 * Revision 1.12  2004/04/01 19:15:30  yan
 * problem is not a constraint anymore
 *
 * Revision 1.11  2003/10/02 23:57:35  yan
 * fixed javadoc
 *
 * Revision 1.10  2003/10/02 23:53:11  yan
 * using heap
 *
 * Revision 1.9  2003/10/02 20:25:40  yan
 * removed remove method
 *
 * Revision 1.8  2003/06/18 19:21:34  yan
 * added clear method
 *
 * Revision 1.7  2003/04/01 16:59:39  yan
 * added tests
 *
 * Revision 1.6  2003/03/20 18:05:57  yan
 * added entailment check
 *
 * Revision 1.5  2003/03/07 11:18:35  yan
 * fixed style
 *
 * Revision 1.4  2003/02/04 19:21:36  yan
 * reusing constraint scheduler
 *
 * Revision 1.3  2003/02/04 17:05:41  yan
 * cleaned constructors
 *
 * Revision 1.2  2002/10/25 08:59:27  yan
 * cleaned category
 *
 * Revision 1.1  2002/10/09 16:29:44  yan
 * initial revision
 *
 */

