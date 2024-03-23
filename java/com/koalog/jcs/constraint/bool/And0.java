package com.koalog.jcs.constraint.bool;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.BinaryConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;

/**
 * This constraint enforces x and y = false.
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
public class And0 extends BinaryConstraint {
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
    public And0(BooleanVariable x, BooleanVariable y) {
        super(x,y);
        name = "x and y = false";
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
        if (x.isTrue()) {
            y.adjustFalse(cp, cs, this);
            // no need to call entailed since both variables are instantiated
            return;
        } 
        if (y.isTrue()) {
            x.adjustFalse(cp, cs, this);
            // no need to call entailed since both variables are instantiated
            return;
        } 
    }
}
/*
 * $Log$
 * Revision 1.15  2005/07/18 15:28:21  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.14  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.13  2004/06/27 13:01:35  yan
 * fixed complexity
 *
 * Revision 1.12  2004/06/10 16:28:07  yan
 * various cleaning
 *
 * Revision 1.11  2004/05/05 11:48:06  yan
 * fixed style
 *
 * Revision 1.10  2004/04/09 14:44:58  yan
 * *** empty log message ***
 *
 * Revision 1.9  2003/08/03 17:56:36  yan
 * optimized boolean adjust methods
 *
 * Revision 1.8  2003/06/17 13:58:37  yan
 * added jdoc about algorithmic infos
 *
 * Revision 1.7  2003/04/01 14:00:15  yan
 * fixed complexity
 *
 * Revision 1.6  2003/03/27 11:04:36  yan
 * added events related method (updateConstraints)
 *
 * Revision 1.5  2003/03/20 21:06:12  yan
 * used entailed statement
 *
 * Revision 1.4  2003/03/07 11:41:18  yan
 * fixed style
 *
 * Revision 1.3  2003/02/18 15:31:09  yan
 * fixed style
 *
 * Revision 1.2  2003/02/02 21:09:57  yan
 * modifiedVariables is now an instance variable
 *
 * Revision 1.1  2003/01/02 09:44:29  yan
 * initial revision
 *
 */
