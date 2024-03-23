package com.koalog.jcs.constraint.set;

import java.util.Collection;
import java.util.TreeSet;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.BinaryConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.variable.SetVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;

/**
 * Enforces that the cardinal of an intersection 
 * is smaller than or equal to a given value.
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
 * @since 2.1
 * @author Yan Georget
 */
public class IntersectionMaxSize extends BinaryConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    private SetVariable y;
    private SetVariable z;
    private int c;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param c an integer
     * @param y a set variable
     * @param z a set variable
     */
    public IntersectionMaxSize(int c, SetVariable y, SetVariable z) {
        super(y, z);
        this.y = y;
        this.z = z;
        this.c = c;
        name = "|intersection(y,z)|<=" + c;
        idempotent = true;
        complexity = Math.max(y.getMax().size(), z.getMax().size());
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        y.addMinConstraint(this);
        z.addMinConstraint(this);
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        final Collection intersection = new TreeSet(y.getMin());
        intersection.retainAll(z.getMin());
        final int size = intersection.size();
        if (size > c) {
            fail();
        } 
        if (size == c) {
            final Collection yExc = new TreeSet(z.getMin());
            yExc.removeAll(intersection);
            y.adjustMaxByExcluding(cp, cs, this, yExc);
            final Collection zExc = new TreeSet(y.getMin());
            zExc.removeAll(intersection);
            z.adjustMaxByExcluding(cp, cs, this, zExc);
        }
    }
}
/*
 * $Log$
 * Revision 1.6  2005/07/25 09:48:21  yan
 * fixed 0021281
 *
 * Revision 1.5  2005/07/18 15:26:25  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.4  2004/11/09 17:07:15  yan
 * throwing static exceptions
 *
 * Revision 1.3  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.2  2004/06/03 16:49:43  yan
 * added @since tag
 *
 * Revision 1.1  2004/05/17 13:52:33  yan
 * initial revision
 *
 */
