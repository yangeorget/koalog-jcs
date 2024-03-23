package com.koalog.jcs.constraint;

import java.util.List;

import org.apache.log4j.Category;

import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.variable.MinMaxVariable;
import com.koalog.jcs.variable.Variable;

/**
 * This abstract class is a base constraint scheduler.
 *
 * @see Constraint
 * @author Yan Georget
 */
public abstract class BaseConstraintScheduler implements ConstraintScheduler {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(BaseConstraintScheduler.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The current date. */
    protected int date; // date is equal to 0 at the beginning
    /** The last constraint popped. */ 
    protected Constraint constraint; // constraint is null at the beginning

    //------------------------------------------------------------------------
    // ACCESSORS 
    //------------------------------------------------------------------------ 
    /** @see com.koalog.jcs.constraint.ConstraintScheduler */
    public final Constraint getConstraint() {
        return constraint;
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.ConstraintScheduler */
    public void clear() {
        constraint = null;
        date++;
    }

    /** @see com.koalog.jcs.constraint.ConstraintScheduler */
    public final void add(ChoicePointStack cp, List l) {
        final int cpDate = cp.getDate();
        if (constraint != null && constraint.isIdempotent()) {
            for (int i = l.size(); --i>=0;) {
                final BaseConstraint c = (BaseConstraint) l.get(i);
                // we test the equality of references (instead of the unids)
                if (c != constraint 
                    && cpDate > c.dateEntailed 
                    && date > c.dateScheduled) {
                    internalAdd(c);
                }
            }
        } else {
            for (int i = l.size(); --i>=0;) {
                final BaseConstraint c = (BaseConstraint) l.get(i);
                if (cpDate > c.dateEntailed && date > c.dateScheduled) {
                    internalAdd(c);
                }
            }
        }
    }

    /** @see com.koalog.jcs.constraint.ConstraintScheduler */
    public final void add(ChoicePointStack cp, Constraint c) {
        if (cp.getDate() > c.getDateEntailed() && date > c.getDateScheduled()) {
            internalAdd(c);
        }
    }

    /** @see com.koalog.jcs.constraint.ConstraintScheduler */
    public void update(ChoicePointStack cp, Variable v) {
        final int mask = v.getEventMask();
        if ((mask & MinMaxVariable.EVENT_MINMAX) == 0) {
            add(cp, v.getDomConstraints());
        } else {
            MinMaxVariable var = (MinMaxVariable) v; // TODO: fix this
            if (var.isInstantiated()) {
                add(cp, var.getGroundConstraints());
            }
            add(cp, var.getMinMaxConstraints());            
            if ((mask & MinMaxVariable.EVENT_MIN) == 0) {
                add(cp, var.getMaxConstraints());
            } else {
                add(cp, var.getMinConstraints());
            }
        }
        v.clearEventMask();
    }

    //------------------------------------------------------------------------
    // ABSTRACT METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.ConstraintScheduler */
    protected abstract void internalAdd(Constraint c);
}
/*
 * $Log$
 * Revision 1.19  2005/09/20 17:31:05  yan
 * various fixes for better reuse
 *
 * Revision 1.18  2005/07/18 09:47:50  yan
 * tiny optim
 *
 * Revision 1.17  2004/11/10 09:31:11  yan
 * made some local variables final
 *
 * Revision 1.16  2004/10/01 15:20:11  yan
 * improved style (replaced while by for)
 *
 * Revision 1.15  2004/09/16 12:24:59  yan
 * cleaned API for adding constraints
 *
 * Revision 1.14  2004/06/24 14:19:45  yan
 * set of scheduled constraints encoded in the constraints
 *
 * Revision 1.13  2004/05/05 11:44:47  yan
 * fixed style
 *
 * Revision 1.12  2004/04/09 14:42:15  yan
 * removal of events
 *
 * Revision 1.11  2004/04/01 19:15:30  yan
 * problem is not a constraint anymore
 *
 * Revision 1.10  2003/10/02 19:33:20  yan
 * changed scheduler update API
 *
 * Revision 1.9  2003/08/03 16:22:35  yan
 * added getProblem method
 *
 * Revision 1.8  2003/07/21 12:29:00  yan
 * fixed style
 *
 * Revision 1.7  2003/07/19 13:19:34  yan
 * removed add method
 *
 * Revision 1.6  2003/07/12 17:56:57  yan
 * added commented debug statements
 *
 * Revision 1.5  2003/03/27 10:52:31  yan
 * added events related methods
 *
 * Revision 1.4  2003/03/20 18:05:57  yan
 * added entailment check
 *
 * Revision 1.3  2003/02/11 10:24:34  yan
 * fixed style
 *
 * Revision 1.2  2002/10/25 08:59:26  yan
 * cleaned category
 *
 * Revision 1.1  2002/10/09 16:29:44  yan
 * initial revision
 *
 */
