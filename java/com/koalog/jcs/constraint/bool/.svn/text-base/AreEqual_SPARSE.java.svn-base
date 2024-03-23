package com.koalog.jcs.constraint.bool;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.domain.SparseDomain;
import com.koalog.jcs.domain.IntegerDomain;

/**
 * This constraint enforces b<=>(x=y) where x and y are integer variables.
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
 *    <TD align="right">max(dom(x),dom(y))</TD>
 * </TR>
 * </TABLE>
 *
 * @author Yan Georget
 */
public class AreEqual_SPARSE extends AreEqual {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param b a boolean variable
     * @param x an integer variable with a sparse domain
     * @param y an integer variable with a sparse domain
     */
    public AreEqual_SPARSE(BooleanVariable b, 
                           IntegerVariable x, 
                           IntegerVariable y) {
        super(b, x, y);
        idempotent = true;
        complexity = Math.max(((IntegerDomain) x.getDomain()).size(),
                              ((IntegerDomain) y.getDomain()).size());
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        b.addGroundConstraint(this);
        x.addDomConstraint(this);
        y.addDomConstraint(this);
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        final SparseDomain xDom = (SparseDomain) x.getDomain();
        final SparseDomain yDom = (SparseDomain) y.getDomain();
        if (b.isTrue()) {
            x.adjustDomain(cp, 
                           cs, 
                           this, 
                           yDom.toSet());
            y.adjustDomain(cp, 
                           cs, 
                           this, 
                           xDom.toSet());
            return;
        } else if (b.isFalse()) {
            if (xDom.isSingleton()) {
                y.adjustDifferent(cp, cs, this, xDom.getMin());
                entailed(cp);
                return;
            } 
            if (yDom.isSingleton()) {
                x.adjustDifferent(cp, cs, this, yDom.getMin());
                entailed(cp);
                return;
            }
        } 
        if (xDom.isSingleton() 
            && yDom.isSingleton() 
            && x.getMin()==y.getMin()) {
            b.adjustTrue(cp, cs, this);
            // no need to call entailed since all the variables are instantiated
        } else if (!xDom.intersects(yDom)) {
            b.adjustFalse(cp, cs, this);
            entailed(cp);
        }
    }
}
/*
 * $Log$
 * Revision 1.10  2005/07/18 15:28:21  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.9  2004/11/23 16:10:42  yan
 * optimized and cleaned a bit
 *
 * Revision 1.8  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.7  2004/05/05 11:48:06  yan
 * fixed style
 *
 * Revision 1.6  2004/04/09 14:44:58  yan
 * *** empty log message ***
 *
 * Revision 1.5  2003/10/02 23:46:46  yan
 * introduced complexity computation
 *
 * Revision 1.4  2003/08/03 17:56:36  yan
 * optimized boolean adjust methods
 *
 * Revision 1.3  2003/06/17 13:58:37  yan
 * added jdoc about algorithmic infos
 *
 * Revision 1.2  2003/04/01 14:00:15  yan
 * fixed complexity
 *
 * Revision 1.1  2003/03/27 11:05:25  yan
 * initial revision
 *
 */
