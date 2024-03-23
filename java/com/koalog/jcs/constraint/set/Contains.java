package com.koalog.jcs.constraint.set;

import org.apache.log4j.Category;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.BinaryConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.variable.SetVariable;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;

/**
 * This constraint enforces: x is true iff y contains c.
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
 *    <TD align="right">card(y)</TD>
 * </TR>
 * </TABLE>
 *
 * @since 1.4
 * @author Yan Georget
 */
public class Contains extends BinaryConstraint {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(Contains.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    private BooleanVariable x;
    private SetVariable y;
    private Comparable c;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param x a boolean variable
     * @param y a set variable
     * @param c a comparable
     */
    public Contains(BooleanVariable x, SetVariable y, Comparable c) {
        super(x, y);
        this.x = x;
        this.y = y;
        this.c = c;
        name = "x<=>" + c + " in y";
        idempotent = true;
        complexity = y.getMax().size();
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        x.addGroundConstraint(this);
        y.addMinMaxConstraint(this);
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        if (x.isTrue()) { 
            y.adjustMinByIncluding(cp, cs, this, c);
            entailed(cp);
            return;
        } else if (x.isFalse()) { 
            y.adjustMaxByExcluding(cp, cs, this, c);
            entailed(cp);
            return;
        }
        if (!y.getMax().contains(c)) {
            x.adjustFalse(cp, cs, this);
            entailed(cp);
        } else if (y.getMin().contains(c)) {
            x.adjustTrue(cp, cs, this);
            entailed(cp);
        }
    }
}
/*
 * $Log$
 * Revision 1.9  2005/07/25 09:48:21  yan
 * fixed 0021281
 *
 * Revision 1.8  2005/07/18 15:26:25  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.7  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.6  2004/06/03 16:49:43  yan
 * added @since tag
 *
 * Revision 1.5  2004/05/05 11:48:49  yan
 * fixed style
 *
 * Revision 1.4  2004/04/09 14:45:23  yan
 * *** empty log message ***
 *
 * Revision 1.3  2003/10/02 23:47:33  yan
 * introduced complexity computation
 *
 * Revision 1.2  2003/08/03 17:54:12  yan
 * optimized boolean adjust methods
 *
 * Revision 1.1  2003/08/01 08:59:38  yan
 * initial revision
 *
 */
