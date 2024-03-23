package com.koalog.jcs.constraint.arithmetic;

import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.domain.SparseDomain;

/**
 * Enforces the variables to have a constant sum while being different.
 *
 * @see AllDifferentConstantSum
 * @since 3.1 
 * @author Yan Georget
 */
public class AllDifferentConstantSum_SPARSE extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param vars an array containing between 
     * <CODE>VARS_MIN</CODE> and <CODE>VARS_MAX</CODE> variables
     * @param sum an integer between 
     * <CODE>SUM_MIN</CODE> and <CODE>SUM_MAX</CODE>
     */
    public AllDifferentConstantSum_SPARSE(IntegerVariable[] vars, int sum) {
        super();
        this.name = "alldifferent and SIGMA_i x_i=" + sum;
        AllDifferentConstantSum.checkArguments(vars, sum);
        int n = vars.length;
        IntegerVariable[] svars = new IntegerVariable[n];
        for (int v=0; v<n; v++) {
            svars[v] = 
                new IntegerVariable(new SparseDomain(AllDifferentConstantSum
                                                     .VAR_MIN, 
                                                     AllDifferentConstantSum
                                                     .VAR_MAX));
        }
        add(new ConstantSum(vars, sum));
        add(new ConstantSum(svars, sum));
        add(new AllDifferentSort_SPARSE(vars, svars));
        add(new Relation_SPARSE(svars, 
                                AllDifferentConstantSum.getTuples(n, sum)));
        // TODO: extends the limits
        setVariables(vars);
    }
}
