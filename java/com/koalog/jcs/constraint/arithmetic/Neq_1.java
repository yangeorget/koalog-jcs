package com.koalog.jcs.constraint.arithmetic;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.constraint.UnaryConstraint;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.domain.IntegerDomain;

/**
 * This constraint enforces x<>c.
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
public class Neq_1 extends UnaryConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    private int c;
    private IntegerVariable x;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param x an integer variable
     * @param c a constant
     */
    public Neq_1(IntegerVariable x, int c) {
        super(x);
        name = "x<>" + c;
        this.c = c;
        this.x = x;
        idempotent = true;
        complexity = 1;
    }
  
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        x.addMinMaxConstraint(this);
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        final IntegerDomain xDom = (IntegerDomain) x.getDomain();
        if (c == xDom.getMin()) {
            x.adjustMin(cp, cs, this, c+1);
            entailed(cp);
        } else if (c == xDom.getMax()) {
            x.adjustMax(cp, cs, this, c-1);
            entailed(cp);
        } 
    }
}
/*
 * $Log$
 * Revision 1.2  2005/07/22 14:53:24  yan
 * pretty variable name
 *
 * Revision 1.1  2005/07/22 12:02:32  yan
 * initial revision
 *
 */
