package com.koalog.jcs.constraint.bool;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.constraint.TernaryConstraint;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;

/**
 * This constraint enforces b<=>(x&lt;y+c) where x and y are integer variables.
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
public class IsSmaller_3 extends TernaryConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    private BooleanVariable b;
    private IntegerVariable x;
    private IntegerVariable y;
    private int c;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param b a boolean variable
     * @param x an integer variable
     * @param y an integer variable
     * @param c an int
     */
    public IsSmaller_3(BooleanVariable b, 
                       IntegerVariable x, 
                       IntegerVariable y,
                       int c) {
        super(b, x, y);
        this.b = b;
        this.x = x;
        this.y = y;
        this.c = c;
        name = "b<=>x<y+" + c;
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
        if (b.isFalse()) {
            x.adjustMin(cp, cs, this, y.getMin()+c);
            y.adjustMax(cp, cs, this, x.getMax()-c);
        } else if (b.isTrue()) {
            x.adjustMax(cp, cs, this, y.getMax()+c-1);
            y.adjustMin(cp, cs, this, x.getMin()-c+1);
        } else if (x.getMax() < y.getMin()+c) {
            b.adjustTrue(cp, cs, this);
            entailed(cp);
        } else if (x.getMin() >= y.getMax()+c) {
            b.adjustFalse(cp, cs, this);
            entailed(cp);
        }
    }
}
/*
 * $Log$
 * Revision 1.15  2005/07/25 09:48:21  yan
 * fixed 0021281
 *
 * Revision 1.14  2005/07/18 15:26:25  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.13  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.12  2004/07/13 20:44:12  yan
 * added another arg for shifting y
 *
 * Revision 1.11  2004/06/27 13:01:35  yan
 * fixed complexity
 *
 * Revision 1.10  2004/05/05 11:48:07  yan
 * fixed style
 *
 * Revision 1.9  2004/04/09 14:44:58  yan
 * *** empty log message ***
 *
 * Revision 1.8  2003/08/03 17:56:36  yan
 * optimized boolean adjust methods
 *
 * Revision 1.7  2003/06/17 13:58:37  yan
 * added jdoc about algorithmic infos
 *
 * Revision 1.6  2003/04/01 14:00:15  yan
 * fixed complexity
 *
 * Revision 1.5  2003/03/27 11:04:36  yan
 * added events related method (updateConstraints)
 *
 * Revision 1.4  2003/03/20 21:06:12  yan
 * used entailed statement
 *
 * Revision 1.3  2003/03/07 11:41:18  yan
 * fixed style
 *
 * Revision 1.2  2003/02/02 21:12:51  yan
 * modifiedVariables is now an instance variable
 *
 * Revision 1.1  2002/11/25 12:42:07  yan
 * initial revision
 *
 */
