package com.koalog.jcs.constraint.arithmetic;

import java.util.Collection;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.InvalidConstraintException;
import com.koalog.jcs.constraint.BaseConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.domain.IntegerDomain;
import com.koalog.jcs.variable.IntegerVariable;

/**
 * This constraint enforces that 
 * the sum of its variables is equal to a given constant.
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
public class ConstantSum extends BaseConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The constant sum. */
    private int sum;
    private int[] mins;
    private int[] maxs;
    private int min;
    private int max;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Main constructor.
     * @param variables an array of integer variables
     * @param sum an integer
     */
    public ConstantSum(IntegerVariable[] variables, int sum) {
        super(variables);
        checkArguments(variables);
        this.name = "SIGMA_i x_i=" + sum;
        this.sum = sum;
        complexity = variables.length; 
        idempotent = !IntegerVariable.oneSparse(variables);
        mins = new int[variables.length];
        maxs = new int[variables.length];
    }

    /**
     * Auxilliary constructor.
     * @param variables a collection of integer variables
     * @param sum an integer
     */
    public ConstantSum(Collection variables, int sum) {
        this((IntegerVariable[]) variables.toArray(new IntegerVariable[] {}), 
             sum);
    }

    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------
    /**
     * Returns the constant sum.
     * @return an int
     */
    public final int getSum() {
        return sum;
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
        min = max = sum;
        for (int j=variables.length; --j>=0;) {
            final IntegerDomain dom = (IntegerDomain) variables[j].getDomain();
            max -= mins[j] = dom.getMin();
            min -= maxs[j] = dom.getMax();
        }
        for (int j=variables.length; --j>=0;) { 
            ((IntegerVariable) variables[j]).adjustMinMax(cp, 
                                                          cs,
                                                          this,
                                                          min+maxs[j],
                                                          max+mins[j]);
        }
    }
    
    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    static void checkArguments(IntegerVariable[] variables) {
        if (variables.length == 0) {
            throw new InvalidConstraintException("#variables = 0");
        }
    }
}
