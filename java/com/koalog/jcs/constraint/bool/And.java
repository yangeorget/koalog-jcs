package com.koalog.jcs.constraint.bool;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.TernaryConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;

/**
 * This constraint enforces x=y and z.
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
public class And extends TernaryConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    private BooleanVariable x;
    private BooleanVariable y;
    private BooleanVariable z;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param x a boolean variable
     * @param y a boolean variable
     * @param z a boolean variable
     */
    public And(BooleanVariable x, BooleanVariable y, BooleanVariable z) {
        super(x,y,z);
        name = "x=y and z";
        this.x = x;
        this.y = y;
        this.z = z;
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
        z.addGroundConstraint(this);
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        if (y.isFalse() || z.isFalse()) {
            x.adjustFalse(cp, cs, this);
            entailed(cp);
            return;
        } 
        if (y.isTrue() && z.isTrue()) {
            x.adjustTrue(cp, cs, this);
            // no need to call entailed 
            // since all the variables are instantiated
            return;
        }
        if (x.isTrue()) {
            y.adjustTrue(cp, cs, this);
            z.adjustTrue(cp, cs, this);
            // no need to call entailed 
            // since all the variables are instantiated
            return;
        }
        if (x.isFalse()) { 
            if (y.isTrue()) {
                z.adjustFalse(cp, cs, this);
                // no need to call entailed 
                // since all the variables are instantiated
                return;
            } 
            if (z.isTrue()) {
                y.adjustFalse(cp, cs, this);
                // no need to call entailed 
                // since all the variables are instantiated
                return;
            }
        }        
    }
}
/*
 * $Log$
 * Revision 1.19  2005/07/18 15:28:21  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.18  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.17  2004/06/27 13:01:35  yan
 * fixed complexity
 *
 * Revision 1.16  2004/06/10 16:28:07  yan
 * various cleaning
 *
 * Revision 1.15  2004/05/05 11:48:06  yan
 * fixed style
 *
 * Revision 1.14  2004/04/09 14:44:58  yan
 * *** empty log message ***
 *
 * Revision 1.13  2003/08/03 17:56:36  yan
 * optimized boolean adjust methods
 *
 * Revision 1.12  2003/06/17 13:58:37  yan
 * added jdoc about algorithmic infos
 *
 * Revision 1.11  2003/04/01 14:00:15  yan
 * fixed complexity
 *
 * Revision 1.10  2003/03/27 11:04:36  yan
 * added events related method (updateConstraints)
 *
 * Revision 1.9  2003/03/21 10:04:14  yan
 * fixed style
 *
 * Revision 1.8  2003/03/20 21:06:12  yan
 * used entailed statement
 *
 * Revision 1.7  2003/03/07 11:41:18  yan
 * fixed style
 *
 * Revision 1.6  2003/02/02 21:09:57  yan
 * modifiedVariables is now an instance variable
 *
 * Revision 1.5  2002/10/04 16:23:51  yan
 * various optimizations
 *
 * Revision 1.4  2002/05/02 16:19:06  yan
 * moved
 *
 * Revision 1.3  2002/04/22 12:45:28  yan
 * optimized computation
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */