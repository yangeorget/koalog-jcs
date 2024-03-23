package com.koalog.jcs.constraint.arithmetic;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Category;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.domain.SparseDomain;

/**
 * A constraint for sorting an array of integer variables.
 *
 * @see Sort
 * @author Yan Georget
 */
public class Sort_SPARSE extends BaseProblem {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(Sort_SPARSE.class);

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Main constructor, which requires a permutation.
     * @param vars an array of non sorted variables
     * @param sortedVars an array of sorted variables
     * @param permutation the corresponding permutation (sparse domains)
     */
    public Sort_SPARSE(IntegerVariable[] vars, 
                       IntegerVariable[] sortedVars, 
                       IntegerVariable[] permutation) {
        super("sort");
        Sort.checkArguments(vars, sortedVars, permutation);
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
    public Sort_SPARSE(IntegerVariable[] vars, 
                       IntegerVariable[] sortedVars) {
        super("sort");
        Sort.checkArguments(vars, sortedVars);
        IntegerVariable[] permutation = new IntegerVariable[vars.length];
        for (int i=0; i<vars.length; i++) {
            permutation[i] = 
                new IntegerVariable(new SparseDomain(0, vars.length-1));
        }
        add(new AllDifferent_SPARSE(permutation));
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
            add(new Element_3SPARSE(vars[i], permutation[i], sortedVars));
        }
        add(new Sort.Sort_AUX(vars, sortedVars));
    }
    
    /**
     * Enforces that the sorted variables are increasing.
     * @param sortedVars an array of sorted variables
     */
    protected void postIncreasing(IntegerVariable[] sortedVars) {
        add(new Increasing(sortedVars));
    }
}
