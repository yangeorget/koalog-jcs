package com.koalog.jcs.constraint.arithmetic;

import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.domain.SparseDomain;
import org.apache.log4j.Category;

/**
 * Enforces the variables to have a given sum while being different.
 *
 * @see AllDifferentSum
 * @since 3.1 
 * @author Yan Georget
 */
public class AllDifferentSum_SPARSE extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param sum an integer variable
     * @param vars an array containing between 
     * <CODE>VARS_MIN</CODE> and <CODE>VARS_MAX</CODE> variables
     */
    public AllDifferentSum_SPARSE(IntegerVariable sum, 
                                  IntegerVariable[] vars) {
        super();
        this.name = "alldifferent(x) and s=SIGMA_i x_i";
        AllDifferentSum.checkArguments(vars);
        int n = vars.length;
        this.variables = new IntegerVariable[n + 1];
        this.variables[0] = sum;
        System.arraycopy(vars, 0, variables, 1, n);
        IntegerVariable[] svars = new IntegerVariable[n];
        for (int v=0; v<n; v++) {
            svars[v] = new IntegerVariable
                (new SparseDomain(IntegerVariable.getMinMin(vars), 
                                  IntegerVariable.getMaxMax(vars)));
        }
        add(new Sum(sum, vars));
        add(new Sum(sum, svars));
        add(new AllDifferentSort_SPARSE(vars, svars));
        IntegerVariable[] svariables = new IntegerVariable[n+1];
        svariables[0] = sum;
        System.arraycopy(svars, 0, svariables, 1, n);
        add(new Relation_SPARSE(svariables, AllDifferentSum.getTuples(n))); 
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
       Category.getInstance(AllDifferentSum_SPARSE.class);
}
