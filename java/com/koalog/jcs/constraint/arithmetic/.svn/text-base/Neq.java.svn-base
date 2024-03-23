package com.koalog.jcs.constraint.arithmetic;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.BinaryConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.domain.IntegerDomain;

/**
 * This constraint enforces x&ne;y.
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
public class Neq extends BinaryConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** An integer variable. */
    protected IntegerVariable x;
    /** An integer variable. */
    protected IntegerVariable y;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param x an integer variable
     * @param y an integer variable
     */
    public Neq(IntegerVariable x, IntegerVariable y) {
        super(x,y);
        name = "x<>y";
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
        final IntegerDomain xDom = (IntegerDomain) x.getDomain();
        final IntegerDomain yDom = (IntegerDomain) y.getDomain();
        if (yDom.isSingleton()) {
            final int value = yDom.getMin();
            if (value == xDom.getMin()) {
                x.adjustMin(cp, cs, this, value+1);
                entailed(cp);
            } else if (value == xDom.getMax()) {
                x.adjustMax(cp, cs, this, value-1);
                entailed(cp);
            } 
        } else if (xDom.isSingleton()) {
            final int value = xDom.getMin();
            if (value == yDom.getMin()) {
                y.adjustMin(cp, cs, this, value+1);
                entailed(cp);
            } else if (value == yDom.getMax()) {
                y.adjustMax(cp, cs, this, value-1);
                entailed(cp);
            } 
        }
    }
}
/*
 * $Log$
 * Revision 1.16  2005/07/18 15:30:17  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.15  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.14  2004/06/27 13:00:07  yan
 * fixed complexity
 *
 * Revision 1.13  2004/05/05 11:46:49  yan
 * fixed style
 *
 * Revision 1.12  2004/04/09 14:42:50  yan
 * removal of events
 *
 * Revision 1.11  2003/06/17 13:36:20  yan
 * added javadoc about algorithmic infos
 *
 * Revision 1.10  2003/04/01 13:58:01  yan
 * fixed complexity
 *
 * Revision 1.9  2003/03/27 11:01:48  yan
 * added events related method (updateConstraints)
 *
 * Revision 1.8  2003/03/20 18:07:06  yan
 * added entailment check
 *
 * Revision 1.7  2003/02/11 10:30:42  yan
 * fixed style
 *
 * Revision 1.6  2003/02/02 21:12:52  yan
 * modifiedVariables is now an instance variable
 *
 * Revision 1.5  2002/10/04 16:25:10  yan
 * various optimizations
 *
 * Revision 1.4  2002/06/20 12:08:32  yan
 * fixed javadoc typos
 *
 * Revision 1.3  2002/05/02 16:21:23  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
