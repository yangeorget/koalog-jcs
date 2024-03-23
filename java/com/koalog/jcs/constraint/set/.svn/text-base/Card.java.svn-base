package com.koalog.jcs.constraint.set;

import org.apache.log4j.Category;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.BinaryConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.variable.SetVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;

/**
 * This constraint enforces x=card(y).
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
public class Card extends BinaryConstraint {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(Card.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    private IntegerVariable x;
    private SetVariable y;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param x an integer variable
     * @param y a set variable
     */
    public Card(IntegerVariable x, SetVariable y) {
        super(x,y);
        this.x = x;
        this.y = y;
        name = "x=card(y)";
        idempotent = true;
        complexity = y.getMax().size();
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        x.addMinMaxConstraint(this);
        y.addMinMaxConstraint(this);
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        final int yMinSize = y.getMin().size();
        final int yMaxSize = y.getMax().size();
        x.adjustMinMax(cp, cs, this, yMinSize, yMaxSize);
        if (x.isInstantiated()) {
            int size = x.getMin();
            if (yMinSize == size) {
                y.adjustMaxToMin(cp, cs, this);
                entailed(cp);
            } else if (yMaxSize == size) {
                y.adjustMinToMax(cp, cs, this);
                entailed(cp);
            }
        }
    }
}
/*
 * $Log$
 * Revision 1.10  2005/07/18 15:26:25  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.9  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.8  2004/06/03 16:49:43  yan
 * added @since tag
 *
 * Revision 1.7  2004/05/05 11:48:49  yan
 * fixed style
 *
 * Revision 1.6  2004/04/09 14:45:23  yan
 * *** empty log message ***
 *
 * Revision 1.5  2003/10/02 23:47:33  yan
 * introduced complexity computation
 *
 * Revision 1.4  2003/07/31 16:42:13  yan
 * fixed complexity
 *
 * Revision 1.3  2003/07/21 12:21:49  yan
 * various optims
 *
 * Revision 1.2  2003/07/19 18:30:35  yan
 * fixed complexity (1)
 *
 * Revision 1.1  2003/06/23 17:16:14  yan
 * initial revision
 *
 */
