package com.koalog.jcs.constraint.arithmetic;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.BinaryConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.domain.IntegerDomain;

/**
 * This constraint enforces x&le;y+c.
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
 * @since 1.1
 * @author Yan Georget
 */
public class LeqShift extends BinaryConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    private IntegerVariable x;
    private IntegerVariable y;
    private int c;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param x an integer variable
     * @param y an integer variable
     * @param c an integer
     */
    public LeqShift(IntegerVariable x, IntegerVariable y, int c) {
        super(x, y);
        this.x = x;
        this.y = y;
        this.c = c;
        name = "x<=y+" + c;
        // even if one of the variables has a sparse domain
        idempotent = true;
        complexity = 1;
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        x.addMinConstraint(this);
        y.addMaxConstraint(this);
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        final IntegerDomain yDom = (IntegerDomain) y.getDomain();
        x.adjustMax(cp, cs, this, yDom.getMax()+c);
        final IntegerDomain xDom = (IntegerDomain) x.getDomain();
        y.adjustMin(cp, cs, this, xDom.getMin()-c);
        if (xDom.getMax() <= yDom.getMin()+c) {
            entailed(cp);
        }
    }
}
/*
 * $Log$
 * Revision 1.15  2005/07/25 09:48:21  yan
 * fixed 0021281
 *
 * Revision 1.14  2005/07/18 15:30:17  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.13  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.12  2004/06/27 13:00:07  yan
 * fixed complexity
 *
 * Revision 1.11  2004/06/21 16:04:35  yan
 * optimized
 *
 * Revision 1.10  2004/06/03 16:50:22  yan
 * added @since tag
 *
 * Revision 1.9  2004/05/05 11:46:49  yan
 * fixed style
 *
 * Revision 1.8  2004/04/09 14:42:50  yan
 * removal of events
 *
 * Revision 1.7  2003/06/17 13:36:20  yan
 * added javadoc about algorithmic infos
 *
 * Revision 1.6  2003/04/01 13:58:01  yan
 * fixed complexity
 *
 * Revision 1.5  2003/03/27 11:01:48  yan
 * added events related method (updateConstraints)
 *
 * Revision 1.4  2003/03/20 19:17:03  yan
 * added entailment statement
 *
 * Revision 1.3  2003/02/11 10:30:42  yan
 * fixed style
 *
 * Revision 1.2  2003/02/02 21:14:55  yan
 * modifiedVariables is now an instance variable
 *
 * Revision 1.1  2002/10/08 17:20:54  yan
 * initial revision
 *
 */
