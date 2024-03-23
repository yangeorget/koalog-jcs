package com.koalog.jcs.constraint.arithmetic;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.BaseConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.domain.IntegerDomain;
import com.koalog.jcs.choicepoint.ChoicePointStack;

/**
 * This constraint enforces m=MAX_i x_i.
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
 *    <TD align="right">variables.length</TD>
 * </TR>
 * </TABLE>
 *
 * @author Yan Georget
 */
public class Max extends BaseConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    private IntegerVariable m;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param m an integer variable
     * @param x an array of integer variables
     */    
    public Max(IntegerVariable m, IntegerVariable[] x) {
        super();
        this.name = "m=MAX_i x_i";
        this.m = m;
        this.variables = new IntegerVariable[x.length + 1];
        this.variables[0] = m;
        System.arraycopy(x, 0, variables, 1, x.length);
        complexity = variables.length; 
        idempotent = m.isMinMax() && !IntegerVariable.oneSparse(x);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        for (int i=0; i<variables.length; i++) {
            ((IntegerVariable) variables[i]).addMinMaxConstraint(this);
        }
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        final IntegerDomain mDom = (IntegerDomain) m.getDomain();
        final int max = mDom.getMax();
        for (int j=variables.length; --j>0;) {
            final IntegerVariable x = ((IntegerVariable) variables[j]);
            x.adjustMax(cp, cs, this, max);
            m.adjustMin(cp, cs, this, x.getMin());
        }
        final int min = mDom.getMin();
        int nbEqual = 0; // nb possibly equal
        int jEqual = -1; // last possibly equal
        for (int j=variables.length; --j>0;) {
            if (min <= ((IntegerVariable) variables[j]).getMax()) {
                nbEqual++;
                jEqual = j;
            }
        }
        if (nbEqual == 0) {
            fail();
        } else if (nbEqual == 1) {
            final IntegerVariable x = (IntegerVariable) variables[jEqual];
            final IntegerDomain xDom = (IntegerDomain) x.getDomain();
            m.adjustMinMax(cp, cs, this, xDom.getMin(), xDom.getMax());
            x.adjustMinMax(cp, cs, this, mDom.getMin(), mDom.getMax());
        }
    }
}
/*
 * $Log$
 * Revision 1.2  2005/07/18 15:30:17  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.1  2005/06/13 08:58:55  yan
 * initial revision
 *
 */
