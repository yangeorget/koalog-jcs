package com.koalog.jcs.constraint.arithmetic;

import org.apache.log4j.Category;
import com.koalog.jcs.variable.IntegerVariable;

/**
 * A constraint for sorting an array of integer variables 
 * which are all different.
 *
 * @since 3.1
 * @author Yan Georget
 */
public class AllDifferentSort_SPARSE extends Sort_SPARSE {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
       Category.getInstance(AllDifferentSort_SPARSE.class);

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Main constructor.
     * @param vars an array of non sorted variables
     * @param sortedVars an array of sorted variables
     */
    public AllDifferentSort_SPARSE(IntegerVariable[] vars, 
                                   IntegerVariable[] sortedVars) {
        super(vars, sortedVars);
        name = "alldifferent and sort";
        add(new AllDifferent_SPARSE(vars));
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Enforces that the sorted variables are increasing.
     * @param sortedVars an array of sorted variables
     */
    protected void postIncreasing(IntegerVariable[] sortedVars) {
        add(new StrictlyIncreasing(sortedVars));
    }
}
