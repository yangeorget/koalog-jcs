package com.koalog.jcs.constraint.set;

import java.util.SortedSet;
import java.util.TreeSet;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.constraint.TernaryConstraint;
import com.koalog.jcs.variable.SetVariable;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;

/**
 * This constraint enforces: x is true iff y intersects z.
 *
 * <P>Algorithmic informations 
 * (see 
 * {@link com.koalog.jcs.constraint.Constraint Constraint}
 * for more details on complexity and idempotence):
 * <TABLE border="1">
 * <TR>
 *    <TD><B>Idempotent</B></TD>
 *    <TD align="right">yes</TD>
 * </TR>
 * <TR>
 *    <TD><B>Complexity</B></TD>
 *    <TD align="right">max(card(y),card(z))</TD>
 * </TR>
 * </TABLE>
 *
 * @since 1.4
 * @author Yan Georget
 */
public class Intersects extends TernaryConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    private BooleanVariable x;
    private SetVariable y;
    private SetVariable z;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param x a boolean variable
     * @param y a set variable
     * @param z a set variable
     */
    public Intersects(BooleanVariable x, SetVariable y, SetVariable z) {
        super(x, y, z);
        this.x = x;
        this.y = y;
        this.z = z;
        name = "x=intersects(y,z)";
        idempotent = true;
        complexity = Math.max(y.getMax().size(), z.getMax().size());
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        x.addGroundConstraint(this);
        y.addMinMaxConstraint(this);
        z.addMinMaxConstraint(this);
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        if (x.isTrue()) { // intersection
            final SortedSet intersection = new TreeSet(y.getMax());
            intersection.retainAll(z.getMax());
            final int size = intersection.size();
            if (size == 0) {
                fail();
            } else if (size == 1) { // the intersection is known
                y.adjustMin(cp, cs, this, intersection);
                z.adjustMin(cp, cs, this, intersection);
                entailed(cp);
            }
            return;
        } else if (x.isFalse()) { // no intersection
            y.adjustMaxByExcluding(cp, cs, this, z.getMin());
            z.adjustMaxByExcluding(cp, cs, this, y.getMin());
            // can we be sure there won't be any intersection?
            // Iterator i = y.getMax().iterator();
            // while (i.hasNext()) {
            //     if (z.getMax().contains(i.next())) {
            //         return modifiedVariables;
            //     }
            // }
            // entailed(cp);
            return;
        }
        SortedSet intersection = new TreeSet(y.getMax());
        intersection.retainAll(z.getMax());
        if (intersection.isEmpty()) {
            x.adjustFalse(cp, cs, this);
            entailed(cp);
        } else {
            intersection = new TreeSet(y.getMin());
            intersection.retainAll(z.getMin());
            if (!intersection.isEmpty()) {
                x.adjustTrue(cp, cs, this);
                entailed(cp);
            }
        }
    }
}
/*
 * $Log$
 * Revision 1.12  2005/07/18 15:26:25  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.11  2004/11/09 17:07:15  yan
 * throwing static exceptions
 *
 * Revision 1.10  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.9  2004/06/03 16:49:43  yan
 * added @since tag
 *
 * Revision 1.8  2004/05/05 11:48:50  yan
 * fixed style
 *
 * Revision 1.7  2004/04/09 14:45:23  yan
 * *** empty log message ***
 *
 * Revision 1.6  2003/10/02 23:47:33  yan
 * introduced complexity computation
 *
 * Revision 1.5  2003/08/03 17:54:12  yan
 * optimized boolean adjust methods
 *
 * Revision 1.4  2003/07/31 16:42:13  yan
 * fixed complexity
 *
 * Revision 1.3  2003/07/21 12:29:00  yan
 * fixed style
 *
 * Revision 1.2  2003/07/21 12:21:49  yan
 * various optims
 *
 * Revision 1.1  2003/07/19 18:29:55  yan
 * initial revision
 *
 */
