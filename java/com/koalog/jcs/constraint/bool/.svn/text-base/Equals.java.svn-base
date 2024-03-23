package com.koalog.jcs.constraint.bool;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.BinaryConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.domain.IntegerDomain;

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
public class Equals extends BinaryConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    int c;
    BooleanVariable b;
    IntegerVariable x;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param b a boolean variable
     * @param x an integer variable
     * @param c an integer
     */
    public Equals(BooleanVariable b, IntegerVariable x, int c) {
        super(b,x);
        this.c = c;
        this.b = b;
        this.x = x;
        name = "b<=>x=" + c;
        idempotent = true;
        complexity = 1;
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        b.addGroundConstraint(this);
        x.addMinMaxConstraint(this);
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        final IntegerDomain xDom = (IntegerDomain) x.getDomain(); 
        if (b.isFalse()) {
            if (c == xDom.getMin()) {
                x.adjustMin(cp, cs, this, c+1);
                entailed(cp);
                return;
            } else if (c == xDom.getMax()){
                x.adjustMax(cp, cs, this, c-1);
                entailed(cp);
                return;
            }
        } else if (b.isTrue()){ 
            x.adjustValue(cp, cs, this, c);
            // no need to call entailed 
            // since both variables are instantiated
            return;
        } else {
            if (c < xDom.getMin() || c > xDom.getMax()) {
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
 * Revision 1.21  2005/07/22 17:03:51  yan
 * pretty name
 *
 * Revision 1.20  2005/07/18 15:28:21  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.19  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.18  2004/06/27 13:01:35  yan
 * fixed complexity
 *
 * Revision 1.17  2004/06/21 16:02:17  yan
 * reusing variables properties
 *
 * Revision 1.16  2004/05/05 11:48:06  yan
 * fixed style
 *
 * Revision 1.15  2004/04/09 14:44:58  yan
 * *** empty log message ***
 *
 * Revision 1.14  2003/08/03 17:56:36  yan
 * optimized boolean adjust methods
 *
 * Revision 1.13  2003/06/17 13:58:37  yan
 * added jdoc about algorithmic infos
 *
 * Revision 1.12  2003/04/01 14:00:15  yan
 * fixed complexity
 *
 * Revision 1.11  2003/03/27 11:04:36  yan
 * added events related method (updateConstraints)
 *
 * Revision 1.10  2003/03/21 10:04:14  yan
 * fixed style
 *
 * Revision 1.9  2003/03/20 21:06:12  yan
 * used entailed statement
 *
 * Revision 1.8  2003/03/19 10:21:30  yan
 * fixed style
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
