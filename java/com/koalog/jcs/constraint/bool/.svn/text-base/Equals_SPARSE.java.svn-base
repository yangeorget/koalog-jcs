package com.koalog.jcs.constraint.bool;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.domain.SparseDomain;

/**
 * This constraint enforces b<=>x=c.
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
public class Equals_SPARSE extends Equals {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param b a boolean variable
     * @param x an integer variable with a sparse domain
     * @param c an integer
     */
    public Equals_SPARSE(BooleanVariable b, IntegerVariable x, int c) {
        super(b,x,c);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        b.addGroundConstraint(this);
        x.addDomConstraint(this);
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        if (b.isFalse()) {
            x.adjustDifferent(cp, cs, this, c);
            entailed(cp);
            return;
        } else if (b.isTrue()) { 
            x.adjustValue(cp, cs, this, c);
            // no need to call entailed 
            // since both variables are instantiated
            return;
        } else {
            SparseDomain xDom = (SparseDomain) x.getDomain();
            if (!xDom.contains(c)) {
                b.adjustFalse(cp, cs, this);
                entailed(cp);
            } else if (xDom.isSingleton()){
                b.adjustTrue(cp, cs, this);
                // no need to call entailed 
                // since both variables are instantiated
            }
        }
    }
}
/*
 * $Log$
 * Revision 1.14  2005/07/18 15:28:21  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.13  2004/11/23 16:10:42  yan
 * optimized and cleaned a bit
 *
 * Revision 1.12  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.11  2004/06/27 13:01:35  yan
 * fixed complexity
 *
 * Revision 1.10  2004/05/05 11:48:06  yan
 * fixed style
 *
 * Revision 1.9  2004/04/09 14:44:58  yan
 * *** empty log message ***
 *
 * Revision 1.8  2003/10/02 23:46:46  yan
 * introduced complexity computation
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
 * Revision 1.3  2003/03/21 10:04:14  yan
 * fixed style
 *
 * Revision 1.2  2003/03/20 21:06:12  yan
 * used entailed statement
 *
 * Revision 1.1  2003/03/19 19:10:02  yan
 * initial revision
 *
 */
