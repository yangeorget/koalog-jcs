package com.koalog.jcs.constraint.bool;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.BinaryConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;

/**
 * This constraint enforces x=(if b then c else d), 
 * where b is a variable and where c and d are constants.
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
public class IfThenElse_2 extends BinaryConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    private int c;
    private int d;
    private IntegerVariable x;
    private BooleanVariable b;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param x an integer variable
     * @param b a boolean variable
     * @param c an integer
     * @param d an integer
     */
    public IfThenElse_2(IntegerVariable x, 
                        BooleanVariable b, 
                        int c, 
                        int d) {
        super(x, b);
        this.c = c;
        this.d = d;
        this.x = x;
        this.b = b;
        name = "x=if b then " + c + " else " + d;
        idempotent = true;
        complexity = 1;
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        x.addMinMaxConstraint(this);
        b.addGroundConstraint(this);
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void init(ChoicePointStack cp,
                     ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        if (c < d) {
            x.adjustMinMax(cp, cs, this, c, d);
        } else {
            x.adjustMinMax(cp, cs, this, d, c);
        }
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        if (c < x.getMin() || c > x.getMax()) {
            b.adjustFalse(cp, cs, this);
        } 
        if (d < x.getMin() || d > x.getMax()) {
            b.adjustTrue(cp, cs, this);
        } 
        if (b.isInstantiated()) {
            if (b.isFalse()) { // b=0 thus x=d
                x.adjustValue(cp, cs, this, d);
            } else { // b=1 thus x=c
                x.adjustValue(cp, cs, this, c);
            }
            // no need to call entailed since both variables are instantiated
        }
    }
}
/*
 * $Log$
 * Revision 1.21  2005/07/25 09:48:21  yan
 * fixed 0021281
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
 * Revision 1.17  2004/06/15 19:20:38  yan
 * added back init method
 *
 * Revision 1.16  2004/05/05 11:48:06  yan
 * fixed style
 *
 * Revision 1.15  2004/04/09 14:44:58  yan
 * *** empty log message ***
 *
 * Revision 1.14  2004/03/14 16:43:26  yan
 * various simplifications
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
 * Revision 1.9  2003/03/20 21:06:12  yan
 * used entailed statement
 *
 * Revision 1.8  2003/03/07 11:41:18  yan
 * fixed style
 *
 * Revision 1.7  2003/02/05 09:35:33  yan
 * using adjustMinMax
 *
 * Revision 1.6  2003/02/02 21:09:57  yan
 * modifiedVariables is now an instance variable
 *
 * Revision 1.5  2002/10/04 16:23:51  yan
 * various optimizations
 *
 * Revision 1.4  2002/06/20 12:08:32  yan
 * fixed javadoc typos
 *
 * Revision 1.3  2002/06/16 13:48:23  yan
 * added javadoc
 *
 * Revision 1.2  2002/05/02 16:19:06  yan
 * moved
 *
 * Revision 1.1  2002/04/23 10:46:14  yan
 *  initial revision
 *
 */
