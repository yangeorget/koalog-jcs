package com.koalog.jcs.constraint;

import java.util.LinkedList;
import org.apache.log4j.Category;
import com.koalog.jcs.choicepoint.ChoicePointStack;

/**
 * This class is a default constraint scheduler.
 *
 * @see Constraint
 * @author Yan Georget
 */
public class DefaultConstraintScheduler extends BaseConstraintScheduler {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(DefaultConstraintScheduler.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------ 
    /** The stack of the constraints. */
    private LinkedList stack;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Main constructor.
     */
    public DefaultConstraintScheduler() {
        super();
        stack = new LinkedList();
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** 
     * Converts the constraint scheduler to a string.
     * @return a string 
     */
    public String toString() {
        return stack.toString();
    }

    /** @see com.koalog.jcs.constraint.ConstraintScheduler */
    public void clear() {
        super.clear();
        stack.clear();
    }

    /** @see com.koalog.jcs.constraint.ConstraintScheduler */
    public Constraint pop(ChoicePointStack cp) {
        if (stack.isEmpty()) {
            constraint = null;
        } else {
            constraint = (Constraint) stack.removeLast();
            constraint.setDateScheduled(-1);
        }
        return constraint;
    }
    
    /** @see com.koalog.jcs.constraint.ConstraintScheduler */
    protected void internalAdd(Constraint c) {
        // we know that c is not in the scheduler
        stack.addLast(c);
        c.setDateScheduled(date);
    }
}
/*
 * $Log$
 * Revision 1.15  2005/09/20 17:32:20  yan
 * fixes according to the ones in base class
 *
 * Revision 1.14  2004/11/10 09:31:11  yan
 * made some local variables final
 *
 * Revision 1.13  2004/09/16 12:24:59  yan
 * cleaned API for adding constraints
 *
 * Revision 1.12  2004/06/29 16:09:08  yan
 * avoiding use of stacks
 *
 * Revision 1.11  2004/06/24 14:19:51  yan
 * set of scheduled constraints encoded in the constraints
 *
 * Revision 1.10  2004/04/09 14:42:15  yan
 * removal of events
 *
 * Revision 1.9  2004/04/01 19:15:31  yan
 * problem is not a constraint anymore
 *
 * Revision 1.8  2003/10/02 19:33:20  yan
 * changed scheduler update API
 *
 * Revision 1.7  2003/06/18 19:21:34  yan
 * added clear method
 *
 * Revision 1.6  2003/03/20 18:07:06  yan
 * added entailment check
 *
 * Revision 1.5  2003/03/07 11:18:35  yan
 * fixed style
 *
 * Revision 1.4  2003/02/04 17:05:41  yan
 * cleaned constructors
 *
 * Revision 1.3  2002/10/25 08:59:27  yan
 * cleaned category
 *
 * Revision 1.2  2002/10/09 13:52:38  yan
 * various cleaning
 *
 * Revision 1.1  2002/10/09 11:05:16  yan
 * initial revision
 *
 */
