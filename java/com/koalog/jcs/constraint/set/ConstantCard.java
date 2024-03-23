package com.koalog.jcs.constraint.set;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.UnaryConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.variable.SetVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;

/**
 * This constraint enforces c=card(y).
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
 * @author Yan Georget
 */
public class ConstantCard extends UnaryConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    private int c;
    private SetVariable y;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param c a positive integer
     * @param y a set variable
     */
    public ConstantCard(int c, SetVariable y) {
        super(y);
        this.c = c;
        this.y = y;
        name = "card(y)=" + c;
        idempotent = true;
        complexity = y.getMax().size();
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        y.addMinMaxConstraint(this);
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        final int yMinSize = y.getMin().size();
        final int yMaxSize = y.getMax().size();
        if (yMinSize == c) {
            y.adjustMaxToMin(cp, cs, this);
            entailed(cp);
        } else if (yMaxSize == c) {
            y.adjustMinToMax(cp, cs, this);
            entailed(cp);
        } else if (yMinSize > c || yMaxSize < c) {
            fail();
        } 
    }
}
/*
 * $Log$
 * Revision 1.13  2005/07/25 09:48:21  yan
 * fixed 0021281
 *
 * Revision 1.12  2005/07/18 15:26:25  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.11  2004/11/09 17:07:15  yan
 * throwing static exceptions
 *
 * Revision 1.10  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.9  2004/05/05 11:48:49  yan
 * fixed style
 *
 * Revision 1.8  2004/04/09 14:45:23  yan
 * *** empty log message ***
 *
 * Revision 1.7  2003/10/02 23:47:33  yan
 * introduced complexity computation
 *
 * Revision 1.6  2003/07/31 16:42:13  yan
 * fixed complexity
 *
 * Revision 1.5  2003/07/21 12:29:00  yan
 * fixed style
 *
 * Revision 1.4  2003/07/21 12:21:49  yan
 * various optims
 *
 * Revision 1.3  2003/07/19 18:30:35  yan
 * fixed complexity (1)
 *
 * Revision 1.2  2003/07/19 11:29:45  yan
 * fixed style
 *
 * Revision 1.1  2003/07/12 22:54:16  yan
 * initial revision
 *
 */
