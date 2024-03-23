package com.koalog.jcs.constraint.arithmetic;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Category;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.BaseConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.constraint.InvalidConstraintException;
import com.koalog.jcs.constraint.bool.IsSmaller;
import com.koalog.jcs.constraint.bool.IsSmaller_3;
import com.koalog.jcs.domain.IntegerDomain;

/**
 * A constraint for sorting an array of integer variables.
 *
 * @since 1.2
 * @author Yan Georget
 */
public class Sort extends BaseProblem {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(Sort.class);

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Main constructor, which requires a permutation.
     * @param vars an array of non sorted variables
     * @param sortedVars an array of sorted variables
     * @param permutation the corresponding permutation, 
     * this means that <CODE>vars[i]</CODE> is the 
     * <CODE>permutation[i]</CODE>th variable of <CODE>sortedVars</CODE>
     */
    public Sort(IntegerVariable[] vars, 
                IntegerVariable[] sortedVars, 
                IntegerVariable[] permutation) {
        super("sort");
        checkArguments(vars, sortedVars, permutation);
        postConstraints(vars, sortedVars, permutation);
        List l = new ArrayList(Arrays.asList(vars)); // for addAll
        l.addAll(Arrays.asList(sortedVars));
        l.addAll(Arrays.asList(permutation));
        setVariables((IntegerVariable[]) l.toArray(new IntegerVariable[] {}));
    }

    /**
     * Another constructor not showing the permutation 
     * which is internally defined.
     * @param vars an array of non sorted variables
     * @param sortedVars an array of sorted variables
     */
    public Sort(IntegerVariable[] vars, 
                IntegerVariable[] sortedVars) {
        super("sort");
        checkArguments(vars, sortedVars);
        IntegerVariable[] permutation = new IntegerVariable[vars.length];
        for (int i=0; i<vars.length; i++) {
            permutation[i] = new IntegerVariable(0, vars.length-1);
        }
        add(new AllDifferent(permutation));
        postConstraints(vars, sortedVars, permutation);
        List l = new ArrayList(Arrays.asList(vars)); // for addAll
        l.addAll(Arrays.asList(sortedVars));
        setVariables((IntegerVariable[]) l.toArray(new IntegerVariable[] {}));
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Posts the constraints.
     * @param vars the variables
     * @param sortedVars the sorted variables
     * @param permutation the permutation
     */
    protected void postConstraints(IntegerVariable[] vars, 
                                   IntegerVariable[] sortedVars, 
                                   IntegerVariable[] permutation) {
        postIncreasing(sortedVars);
        for(int i=0; i<vars.length; i++) {
            for (int j=0; j<vars.length; j++) {
                BooleanVariable a1 = new BooleanVariable(); 
                BooleanVariable b1 = new BooleanVariable(); 
                add(new IsSmaller(a1, permutation[i], j+1));
                add(new IsSmaller_3(b1, vars[i], sortedVars[j], 1));
                add(new Leq(a1, b1));
                BooleanVariable a2 = new BooleanVariable(); 
                BooleanVariable b2 = new BooleanVariable(); 
                add(new IsSmaller_3(a2, vars[i], sortedVars[j], 0));
                add(new IsSmaller(b2, permutation[i], j));
                add(new Leq(a2, b2));
            }
        }
        add(new Sort_AUX(vars, sortedVars));
    }

    /**
     * Enforces that the sorted variables are increasing.
     * @param sortedVars an array of sorted variables
     */
    protected void postIncreasing(IntegerVariable[] sortedVars) {
        add(new Increasing(sortedVars));
    }

    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    static void checkArguments(IntegerVariable[] vars, 
                               IntegerVariable[] sortedVars, 
                               IntegerVariable[] permutation) {
        if (vars.length != sortedVars.length 
            || vars.length != permutation.length) {
            throw new InvalidConstraintException("sizes of arrays differ");
        }
    }

    static void checkArguments(IntegerVariable[] vars, 
                               IntegerVariable[] sortedVars) {
        if (vars.length != sortedVars.length) {
            throw new InvalidConstraintException("sizes of arrays differ");
        }
    }

    //------------------------------------------------------------------------
    // INNER CLASSES
    //------------------------------------------------------------------------
    /**
     * A redundant constraint.
     */
    static class Sort_AUX extends BaseConstraint { 
        private int n;

        /**
         * Sole constructor.
         * @param x an array of integer variables
         * @param y an array of integer variables
         */
        public Sort_AUX(IntegerVariable[] x, IntegerVariable[] y) {
            super();
            this.n = x.length;
            variables = new IntegerVariable[2*n];
            System.arraycopy(x, 0, variables, 0, n); 
            System.arraycopy(y, 0, variables, n, n); 
            name = "sort_AUX";
            complexity = (int) (variables.length*Math.log(variables.length)); 
            idempotent = true;
        }

        /** @see com.koalog.jcs.constraint.Constraint */
        public void updateDependencies() {
            for (int i=0; i<variables.length; i++) {
                ((IntegerVariable) variables[i]).addMinMaxConstraint(this);
            }
        }
        
        public void filter(ChoicePointStack cp,
                           ConstraintScheduler cs) 
            throws ConstraintInconsistencyException {
            int[] mins = new int[n];
            int[] maxs = new int[n];
            for (int i=n; --i>=0;) {
                final IntegerDomain xDom = 
                    (IntegerDomain) variables[i].getDomain();
                mins[i] = xDom.getMin();
                maxs[i] = xDom.getMax();
            }
            Arrays.sort(mins);
            Arrays.sort(maxs);
            for (int i=n; --i>=0;) {
                ((IntegerVariable) variables[i+n]).adjustMinMax(cp, 
                                                                cs, 
                                                                this, 
                                                                mins[i], 
                                                                maxs[i]);
            }
        }
    }
}
