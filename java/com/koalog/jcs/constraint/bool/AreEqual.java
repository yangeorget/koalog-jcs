package com.koalog.jcs.constraint.bool;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.constraint.TernaryConstraint;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;
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
 *    <TD align="right">when variables have interval domains</TD>
 * </TR>
 * <TR>
 *    <TD><B>Complexity</B></TD>
 *    <TD align="right">1</TD>
 * </TR>
 * </TABLE>
 *
 * @author Yan Georget
 */
public class AreEqual extends TernaryConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** A boolean variable. */
    protected BooleanVariable b;
    /** An integer variable. */
    protected IntegerVariable x;
    /** An integer variable. */
    protected IntegerVariable y;
    
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param b a boolean variable
     * @param x an integer variable
     * @param y an integer variable
     */
    public AreEqual(BooleanVariable b, IntegerVariable x, IntegerVariable y) {
        super(b, x, y);
        name = "b<=>x=y";
        this.b = b;
        this.x = x;
        this.y = y;
        idempotent = x.isMinMax() && y.isMinMax();
        complexity = 1;
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        b.addGroundConstraint(this);
        x.addMinMaxConstraint(this);
        y.addMinMaxConstraint(this);
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        final IntegerDomain xDom = (IntegerDomain) x.getDomain();
        final IntegerDomain yDom = (IntegerDomain) y.getDomain();
        final int xmin = xDom.getMin(); 
        final int xmax = xDom.getMax(); 
        final int ymin = yDom.getMin(); 
        final int ymax = yDom.getMax(); 
        if (b.isTrue()) {
            x.adjustMinMax(cp, cs, this, ymin, ymax);
            y.adjustMinMax(cp, cs, this, xmin, xmax);
            return;
        } else if (b.isFalse()) {
            if (xDom.isSingleton()) {
                if (xmin == ymin) {
                    y.adjustMin(cp, cs, this, xmin+1);
                    entailed(cp);
                    return;
                }
                if (xmax == ymax){
                    y.adjustMax(cp, cs, this, xmax-1);
                    entailed(cp);
                    return;
                }
            } else if (yDom.isSingleton()) {
                if (ymin == xmin) {
                    x.adjustMin(cp, cs, this, ymin+1);
                    entailed(cp);
                    return;
                } 
                if (xmax == ymax){
                    x.adjustMax(cp, cs, this, ymax-1);
                    entailed(cp);
                    return;
                }
            }
        }
        if (xDom.isSingleton() && yDom.isSingleton() && xmin==ymin) {
            b.adjustTrue(cp, cs, this);
            // no need to call entailed since all the variables are instantiated
        } else if ((xmax < ymin) || (xmin > ymax)) {
            b.adjustFalse(cp, cs, this);
            entailed(cp);
        }
    }
}
/*
 * $Log$
 * Revision 1.20  2005/07/18 15:28:21  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.19  2004/11/23 16:10:42  yan
 * optimized and cleaned a bit
 *
 * Revision 1.18  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.17  2004/06/27 13:01:35  yan
 * fixed complexity
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
 * Revision 1.10  2003/03/20 21:06:12  yan
 * used entailed statement
 *
 * Revision 1.9  2003/03/07 11:41:18  yan
 * fixed style
 *
 * Revision 1.8  2003/02/05 09:35:33  yan
 * using adjustMinMax
 *
 * Revision 1.7  2003/02/02 21:09:57  yan
 * modifiedVariables is now an instance variable
 *
 * Revision 1.6  2002/10/04 16:23:51  yan
 * various optimizations
 *
 * Revision 1.5  2002/06/20 12:08:32  yan
 * fixed javadoc typos
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
