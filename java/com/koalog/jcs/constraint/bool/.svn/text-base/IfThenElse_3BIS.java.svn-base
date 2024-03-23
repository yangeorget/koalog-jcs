package com.koalog.jcs.constraint.bool;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.constraint.TernaryConstraint;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;

/**
 * This constraint enforces x=(if b then y else c), 
 * where b and y are variables and where c is a constant.
 *
 * <P>Algorithmic informations 
 * (see 
 * {@link com.koalog.jcs.constraint.Constraint Constraint}
 * for more details on complexity and idempotence):
 * <TABLE border="1">
 * <TR>
 *    <TD><B>Idempotent</B></TD>
 *    <TD align="right">when variables have interval domains</TD>
 * </TR>
 * <TR>
 *    <TD><B>Complexity</B></TD>
 *    <TD align="right">1</TD>
 * </TR>
 * </TABLE>
 *
 * @author Yan Georget
 */
public class IfThenElse_3BIS extends TernaryConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    private int c;
    private IntegerVariable x;
    private IntegerVariable y;
    private BooleanVariable b;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param x an integer variable
     * @param b a boolean variable
     * @param y an integer variable
     * @param c an integer
     */
    public IfThenElse_3BIS(IntegerVariable x, 
                           BooleanVariable b, 
                           IntegerVariable y,
                           int c) {
        super(x, b, y);
        this.c = c;
        this.x = x;
        this.y = y;
        this.b = b;
        name = "x=if b then y else " + c;
        idempotent = x.isMinMax() && y.isMinMax();
        complexity = 1;
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        x.addMinMaxConstraint(this);
        b.addGroundConstraint(this);
        y.addMinMaxConstraint(this);
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        x.adjustMinMax(cp, 
                       cs, this, 
                       Math.min(y.getMin(), c), 
                       Math.max(y.getMax(), c));
        if ((x.getMax() < y.getMin()) || (x.getMin() > y.getMax())) {
            b.adjustFalse(cp, cs, this);
        }
        if (c < x.getMin() || c > x.getMax()) {
            b.adjustTrue(cp, cs, this);
        } 
        if (b.isTrue()) { // b=1 thus x=y
            x.adjustMinMax(cp, cs, this, y.getMin(), y.getMax());
            y.adjustMinMax(cp, cs, this, x.getMin(), x.getMax());
        } else if (b.isFalse()) { // b=0 thus x=c
            x.adjustValue(cp, cs, this, c);
            entailed(cp);
        }
    }
}
/*
 * $Log$
 * Revision 1.18  2005/07/25 09:48:21  yan
 * fixed 0021281
 *
 * Revision 1.17  2005/07/18 15:28:21  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.16  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.15  2004/06/27 13:01:35  yan
 * fixed complexity
 *
 * Revision 1.14  2004/05/05 11:48:06  yan
 * fixed style
 *
 * Revision 1.13  2004/04/09 14:44:58  yan
 * *** empty log message ***
 *
 * Revision 1.12  2003/08/03 17:56:36  yan
 * optimized boolean adjust methods
 *
 * Revision 1.11  2003/06/17 13:58:37  yan
 * added jdoc about algorithmic infos
 *
 * Revision 1.10  2003/04/01 14:00:15  yan
 * fixed complexity
 *
 * Revision 1.9  2003/03/27 11:04:36  yan
 * added events related method (updateConstraints)
 *
 * Revision 1.8  2003/03/20 21:06:12  yan
 * used entailed statement
 *
 * Revision 1.7  2003/03/07 11:41:18  yan
 * fixed style
 *
 * Revision 1.6  2003/02/05 09:35:33  yan
 * using adjustMinMax
 *
 * Revision 1.5  2003/02/02 21:09:57  yan
 * modifiedVariables is now an instance variable
 *
 * Revision 1.4  2002/10/04 16:23:51  yan
 * various optimizations
 *
 * Revision 1.3  2002/06/20 12:08:32  yan
 * fixed javadoc typos
 *
 * Revision 1.2  2002/05/02 16:19:06  yan
 * moved
 *
 * Revision 1.1  2002/04/23 10:46:14  yan
 *  initial revision
 *
 */
