package com.koalog.jcs.constraint.set;

import org.apache.log4j.Category;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.BinaryConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.variable.SetVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;

/**
 * This constraint enforces: y contains x.
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
 *    <TD align="right">max(card(x),card(y))</TD>
 * </TR>
 * </TABLE>
 *
 * @author Yan Georget
 */
public class Leq extends BinaryConstraint {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(Leq.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    private SetVariable x;
    private SetVariable y;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param x a set variable
     * @param y a set variable
     */
    public Leq(SetVariable x, SetVariable y) {
        super(x,y);
        this.x = x;
        this.y = y;
        name = "x<=y";
        idempotent = true;
        complexity = Math.max(x.getMax().size(), y.getMax().size());
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
        x.adjustMax(cp, cs, this, y.getMax());
        y.adjustMin(cp, cs, this, x.getMin());
        if (y.getMin().containsAll(x.getMax())) {
            entailed(cp);
        }
    }
}
/*
 * $Log$
 * Revision 1.8  2005/07/18 15:26:25  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.7  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.6  2004/05/05 11:48:50  yan
 * fixed style
 *
 * Revision 1.5  2004/04/09 14:45:23  yan
 * *** empty log message ***
 *
 * Revision 1.4  2003/10/02 23:47:33  yan
 * introduced complexity computation
 *
 * Revision 1.3  2003/07/31 16:42:13  yan
 * fixed complexity
 *
 * Revision 1.2  2003/07/19 18:30:35  yan
 * fixed complexity (1)
 *
 * Revision 1.1  2003/07/12 17:55:28  yan
 * initial revision
 *
 */
