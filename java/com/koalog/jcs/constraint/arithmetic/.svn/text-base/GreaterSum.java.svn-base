package com.koalog.jcs.constraint.arithmetic;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.BaseConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.variable.IntegerVariable;

/**
 * This constraint enforces that 
 * the sum of its variables is greater than a given constant.
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
public class GreaterSum extends BaseConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    private int c1;
    private int[] maxs;
    private int min;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param variables an array of integer variables
     * @param c an integer
     */
    public GreaterSum(IntegerVariable[] variables, int c) {
        super(variables);
        this.name = "SIGMA_i x_i>" + c;
        this.c1 = c+1;
        complexity = variables.length; 
        idempotent = !IntegerVariable.oneSparse(variables);
        maxs = new int[variables.length];
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        for (int i=0; i<variables.length; i++) {
            ((IntegerVariable) variables[i]).addMaxConstraint(this);
        }
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        min = c1;
        for (int j=variables.length; --j>=0;) {
            min -= maxs[j] = ((IntegerVariable) variables[j]).getMax();
        }
        for (int j=variables.length; --j>=0;) { 
            ((IntegerVariable) variables[j]).adjustMin(cp, 
                                                       cs,
                                                       this,
                                                       min+maxs[j]);
        }
    }
}
