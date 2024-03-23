package com.koalog.jcs.constraint.bool;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.BinaryConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;

/**
 * This constraint enforces x or y = true.
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
 *    <TD align="right">1</TD>
 * </TR>
 * </TABLE>
 *
 * @author Yan Georget
 */
public class Or1 extends BinaryConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    private BooleanVariable x;
    private BooleanVariable y;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param x a boolean variable
     * @param y a boolean variable
     */
    public Or1(BooleanVariable x, BooleanVariable y) {
        super(x,y);
        name = "x or y = true";
        this.x = x;
        this.y = y;
        idempotent = true;
        complexity = 1;
    }
  
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        x.addGroundConstraint(this);
        y.addGroundConstraint(this);
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        if (x.isFalse()) {
            y.adjustTrue(cp, cs, this);
            // no need to call entailed since both variables are instantiated
        } else if (y.isFalse()) {
            x.adjustTrue(cp, cs, this);
            // no need to call entailed since both variables are instantiated
        } 
    }
}
/*
 * $Log$
 * Revision 1.12  2005/07/18 15:26:25  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.11  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.10  2004/06/27 13:01:35  yan
 * fixed complexity
 *
 * Revision 1.9  2004/05/05 11:48:07  yan
 * fixed style
 *
 * Revision 1.8  2004/04/09 14:44:58  yan
 * *** empty log message ***
 *
 * Revision 1.7  2003/08/03 17:56:36  yan
 * optimized boolean adjust methods
 *
 * Revision 1.6  2003/06/17 13:58:37  yan
 * added jdoc about algorithmic infos
 *
 * Revision 1.5  2003/04/01 14:00:15  yan
 * fixed complexity
 *
 * Revision 1.4  2003/03/27 11:04:36  yan
 * added events related method (updateConstraints)
 *
 * Revision 1.3  2003/03/20 21:06:12  yan
 * used entailed statement
 *
 * Revision 1.2  2003/03/07 11:41:18  yan
 * fixed style
 *
 * Revision 1.1  2003/02/18 15:29:11  yan
 * initial revision
 *
 */
