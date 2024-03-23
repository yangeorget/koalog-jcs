package com.koalog.jcs.constraint.arithmetic;

import java.util.List;
import java.util.ArrayList;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.InvalidConstraintException;
import org.apache.log4j.Category;

/**
 * Enforces the variables to have a given sum while being different.
 * The number of variables must be between 
 * <CODE>AllDifferentConstantSum.VARS_MIN</CODE> and 
 * <CODE>AllDifferentConstantSum.VARS_MAX</CODE>.
 *
 * <P>Important note: variables are forced to take their values beetween 
 * <CODE>AllDifferentConstantSum.VAR_MIN</CODE> and 
 * <CODE>AllDifferentConstantSum.VAR_MIN</CODE>.
 * The sum is forced to take its values be between 
 * <CODE>AllDifferentConstantSum.SUM_MIN</CODE> and 
 * <CODE>AllDifferentConstantSum.SUM_MAX</CODE>.
 * 
 * @since 3.1 
 * @author Yan Georget
 */
public class AllDifferentSum extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param sum an integer variable
     * @param vars an array containing between 
     * <CODE>VARS_MIN</CODE> and <CODE>VARS_MAX</CODE> variables
     */
    public AllDifferentSum(IntegerVariable sum, IntegerVariable[] vars) {
        super();
        this.name = "alldifferent(x) and s=SIGMA_i x_i";
        checkArguments(vars);
        int n = vars.length;
        this.variables = new IntegerVariable[n + 1];
        this.variables[0] = sum;
        System.arraycopy(vars, 0, variables, 1, n);
        IntegerVariable[] svars = new IntegerVariable[n];
        for (int v=0; v<n; v++) {
            svars[v] = new IntegerVariable(IntegerVariable.getMinMin(vars), 
                                           IntegerVariable.getMaxMax(vars));
        }
        add(new Sum(sum, vars));
        add(new Sum(sum, svars));
        add(new AllDifferentSort(vars, svars));
        IntegerVariable[] svariables = new IntegerVariable[n+1];
        svariables[0] = sum;
        System.arraycopy(svars, 0, svariables, 1, n);
        // TODO: optimize the list of tuples using the bounds of sum
        add(new Relation(svariables, getTuples(n))); 
    }

    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    /**
     * Given a number n,
     * return all combinations of 
     * a sum followed by n distinct and ordered variables leading to this sum.
     * @param n an integer
     * @return a list of tuples
     */
    public static List getTuples(int n) {
        List l = new ArrayList();
        for (int s = AllDifferentConstantSum.SUM_MIN; 
             s<=AllDifferentConstantSum.SUM_MAX; 
             s++) {
            int[][] tuples = AllDifferentConstantSum.getTuples(n, s);
            for (int i=0; i<tuples.length; i++) {
                int[] tuple = new int[n+1];
                tuple[0] = s;
                for (int j=0; j<n; j++) {
                    tuple[j+1] = tuples[i][j];
                }
                l.add(tuple);
            }
        }
        return l;
    }
        
    static void checkArguments(IntegerVariable[] vars) {
        if (vars.length < AllDifferentConstantSum.VARS_MIN) {
            throw new 
                InvalidConstraintException("too few variables:" 
                                           + vars.length 
                                           + " instead of " 
                                           + AllDifferentConstantSum.VARS_MIN);
        }
        if (vars.length > AllDifferentConstantSum.VARS_MAX) {
            throw new 
                InvalidConstraintException("too many variables:" 
                                           + vars.length 
                                           + " instead of " 
                                           + AllDifferentConstantSum.VARS_MAX);
        }
        // TODO: check the bounds of the variables
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(AllDifferentSum.class);
}
