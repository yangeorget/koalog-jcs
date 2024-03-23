package com.koalog.jcs.examples;

import org.apache.log4j.PropertyConfigurator;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.arithmetic.WeightedSum;
import com.koalog.jcs.constraint.arithmetic.Prod;
import com.koalog.jcs.constraint.arithmetic.Sum;
import com.koalog.jcs.constraint.arithmetic.Add;
import com.koalog.jcs.constraint.arithmetic.InDomain;

/**
 * Find the unique answer to:
 * <PRE>
 *      OEE
 *       EE
 *     ----
 *     EOEE
 *     EOE
 *     ----
 *     OOEE
 * </PRE>
 *
 * where E=even, O=odd.
 *
 * @author Peter Van Roy
 * @author Yan Georget
 */
public class CryptoMultiplicationProblem extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     */
    public CryptoMultiplicationProblem() {
        super();
        int[] odd = new int[] {1, 3, 5, 7, 9};
        int[] even = new int[] {0, 2, 4, 6, 8};
        int[] leven = new int[] {2, 4, 6, 8};
        /*
        this modelling could also be possible:
        IntegerVariable x0 = new IntegerVariable("x0",new SparseDomain(odd));
        IntegerVariable x1 = new IntegerVariable("x1",new SparseDomain(even));
        IntegerVariable x2 = new IntegerVariable("x2",new SparseDomain(even));
        IntegerVariable x3 = new IntegerVariable("x3",new SparseDomain(leven));
        IntegerVariable x4 = new IntegerVariable("x4",new SparseDomain(even));
        IntegerVariable x5 = new IntegerVariable("x5",new SparseDomain(leven));
        IntegerVariable x6 = new IntegerVariable("x6",new SparseDomain(odd));
        IntegerVariable x7 = new IntegerVariable("x7",new SparseDomain(even));
        IntegerVariable x8 = new IntegerVariable("x8",new SparseDomain(even));
        IntegerVariable x9 = new IntegerVariable("x9",new SparseDomain(leven));
        IntegerVariable x10 = new IntegerVariable("x10",new SparseDomain(odd));
        IntegerVariable x11 = new IntegerVariable("x11",new SparseDomain(even));
        IntegerVariable x12 = new IntegerVariable("x12",new SparseDomain(odd));
        IntegerVariable x13 = new IntegerVariable("x13",new SparseDomain(odd));
        IntegerVariable x14 = new IntegerVariable("x14",new SparseDomain(even));
        */
        IntegerVariable x0 = new IntegerVariable("x0", 1, 9);
        IntegerVariable x1 = new IntegerVariable("x1", 0, 8);
        IntegerVariable x2 = new IntegerVariable("x2", 0, 8);
        IntegerVariable x3 = new IntegerVariable("x3", 2, 8);
        IntegerVariable x4 = new IntegerVariable("x4", 0, 8);
        IntegerVariable x5 = new IntegerVariable("x5", 2, 8);
        IntegerVariable x6 = new IntegerVariable("x6", 1, 9);
        IntegerVariable x7 = new IntegerVariable("x7", 0, 8);
        IntegerVariable x8 = new IntegerVariable("x8", 0, 8);
        IntegerVariable x9 = new IntegerVariable("x9", 2, 8);
        IntegerVariable x10 = new IntegerVariable("x10", 1, 9);
        IntegerVariable x11 = new IntegerVariable("x11", 0, 8);
        IntegerVariable x12 = new IntegerVariable("x12", 1, 9);
        IntegerVariable x13 = new IntegerVariable("x13", 1, 9);
        IntegerVariable x14 = new IntegerVariable("x14", 0, 8);
        add(new InDomain(x0, odd));
        add(new InDomain(x1, even));
        add(new InDomain(x2, even));
        add(new InDomain(x3, leven));
        add(new InDomain(x4, even));
        add(new InDomain(x5, leven));
        add(new InDomain(x6, odd));
        add(new InDomain(x7, even));
        add(new InDomain(x8, even));
        add(new InDomain(x9, leven));
        add(new InDomain(x10, odd));
        add(new InDomain(x11, even));
        add(new InDomain(x12, odd));
        add(new InDomain(x13, odd));
        add(new InDomain(x14, even));
        // x15 equals x8
        IntegerVariable c1 = new IntegerVariable("c1", 0,9);
        IntegerVariable c2 = new IntegerVariable("c2", 0,9);
        digitMul(null, x4, x2, x8, c1);
        digitMul(c1, x4, x1, x7, c2);
        digitMul(c2, x4, x0, x6, x5);
        IntegerVariable c3 = new IntegerVariable("c3", 0,9);
        IntegerVariable c4 = new IntegerVariable("c4", 0,9);
        digitMul(null, x3, x2, x11, c3);
        digitMul(c3, x3, x1, x10, c4);
        digitMul(c4, x3, x0, x9, null);
        IntegerVariable d1 = new IntegerVariable("d1", 0, 2);
        IntegerVariable d2 = new IntegerVariable("d2", 0, 2);
        digitAdd(null, x7, x11, x14, d1);
        digitAdd(d1, x6, x10, x13, d2);
        digitAdd(d2, x5, x9, x12, null);
        setVariables(new IntegerVariable[] {x0, 
                                            x1, 
                                            x2, 
                                            x3, 
                                            x4, 
                                            x5, 
                                            x6, 
                                            x7, 
                                            x8, 
                                            x9, 
                                            x10, 
                                            x11, 
                                            x12, 
                                            x13, 
                                            x14});
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * When multiplying a and b with carry in, we get c with carry out.
     */
    void digitMul(IntegerVariable in, 
                  IntegerVariable a, 
                  IntegerVariable b, 
                  IntegerVariable c, 
                  IntegerVariable out) {
        IntegerVariable m = new IntegerVariable(0, 81);
        add(new Prod(m, a, b));
        if (in == null) {
            add(new WeightedSum(m,
                                new int[] {10, 1},
                                new IntegerVariable[] {out, c}));
        } else if (out == null) {
            add(new Add(c, m, in));
        } else {
            add(new WeightedSum(m,
                                new int[] {10, 1, -1},
                                new IntegerVariable[] {out, c, in}));
        }
    }
    
    /**
     * When adding a and b with carry in, we get c with carry out.
     */
    void digitAdd(IntegerVariable in, 
                  IntegerVariable a, 
                  IntegerVariable b, 
                  IntegerVariable c, 
                  IntegerVariable out) {
        if (in == null) {
            add(new WeightedSum(c,
                                new int[] {-10, 1, 1},
                                new IntegerVariable[] {out, a, b}));
        } else if (out == null) {
            add(new Sum(c, new IntegerVariable[] {in, a, b}));
        } else {
            add(new WeightedSum(c,
                                new int[] {-10, 1, 1, 1},
                                new IntegerVariable[] {out, in, a, b}));
        }
    }

    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    /**
     * Runs the problem.
     * @param args the command line arguments
     * args[0] must contain a log4j properties file location
     */
    public static void main(String[] args) {
        PropertyConfigurator.configure(args[0]);
        new DefaultSplitSolver(new CryptoMultiplicationProblem()).solve(2);
    }
}



