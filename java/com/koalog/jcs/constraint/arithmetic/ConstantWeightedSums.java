package com.koalog.jcs.constraint.arithmetic;

import org.apache.log4j.Category;

import com.koalog.util.matrix.LinearProblem;
import com.koalog.jcs.constraint.InvalidConstraintException;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.variable.IntegerVariable;

/**
 * This constraint enforces the linear equations 
 * <CODE>AX=C</CODE> where:
 * <UL>
 * <LI>A is a nxm matrix of integers, 
 * <LI>X is a vector of integer variables of size m and
 * <LI>C is a vector of integers of size n.
 * </UL>
 *
 * @since 3.1
 * @author Yan Georget
 */
public class ConstantWeightedSums extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTANTS
    //------------------------------------------------------------------------
    /** A mode for posting original constraints. */
    public static final int ORIGINAL = 1;
    /** A mode for posting reduced (Gauss reduction) constraints. */
    public static final int REDUCED = 2;
    /** A mode for posting both constraints. */
    public static final int BOTH = 3;

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
       Category.getInstance(ConstantWeightedSums.class);

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param a a matrix of integers as an array of arrays
     * @param variables an array of integer variables
     * @param c an array of integers
     * @param mode an integer indicating which constraints to post
     */
    public ConstantWeightedSums(int[][] a, 
                                IntegerVariable[] variables, 
                                int[] c,
                                int mode) {
        super("AX=C"); // do we want to pretty-print this problem?
        checkArguments(a, variables, c);
        if ((mode & REDUCED) == REDUCED) {
            LinearProblem p = new LinearProblem(a, c);
            p.reduce(); 
            // cat.info("\n" + p.toString());
            checkArguments(p);
            int[][] ar = p.getMatrix();
            int[] cr = p.getVector();
            for(int i=0; i<p.getPivotNb(); i++) {
                add(new ConstantWeightedSum(ar[i], variables, cr[i]));
            }
        } 
        if ((mode & ORIGINAL) == ORIGINAL) {
            for(int i=0; i<a.length; i++) {
                add(new ConstantWeightedSum(a[i], variables, c[i]));
            }
        }
        setVariables(variables); 
    }
    
    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    void checkArguments(int[][] a, IntegerVariable[] variables, int[] c) {
        if (a.length != c.length) {
            throw new InvalidConstraintException("height(a) != #c");
        }
        if (a[0].length != variables.length) {
            throw new InvalidConstraintException("width(a) != #variables");
        }
        // we could also check that the same variable does not appear twice
    }

    void checkArguments(LinearProblem p) {
        if (p.hasNoSolution()) {
            throw new InvalidConstraintException("no solution");
        }
    }
}
