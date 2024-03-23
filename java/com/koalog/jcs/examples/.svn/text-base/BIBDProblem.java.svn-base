package com.koalog.jcs.examples;

import org.apache.log4j.PropertyConfigurator;

import com.koalog.util.matrix.BaseMatrix;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.bool.And;
import com.koalog.jcs.constraint.arithmetic.Exactly;
import com.koalog.jcs.constraint.arithmetic.LexIncreasing;
import com.koalog.jcs.solver.DefaultSplitSolver;

/**
 * The balanced incomplete block design problem.
 * 
 * <P>A BIBD is defined as an arrangement of v distinct objects into b blocks 
 * such that:
 * <UL>
 * <LI>each block contains exactly k distinct objects, 
 * <LI>each object occurs in exactly r different blocks and
 * <LI>every two distinct objects occur together in exactly lambda blocks. 
 * </UL>
 * 
 * <P>Another way of defining a BIBD is in terms of its incidence matrix, 
 * which is a v by b binary matrix with exactly 
 * r ones per row, 
 * k ones per column and 
 * with a scalar product of lambda between any pair of distinct rows.
 *
 * <P>Note: This is problem #28 in CSPLib.  
 *
 * @author Yan Georget
 */
public class BIBDProblem extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param v the number of objects
     * @param b the number of blocks
     * @param r the number of blocks per object
     * @param k the number of objects per block
     * @param lambda number of times 
     * two distinct objects occur together in a block
     */
    public BIBDProblem(int v, int b, int r, int k, int lambda) {
        super();
        BooleanVariable[] elements = new BooleanVariable[v*b];
        for (int i=0; i<v*b; i++) {
            elements[i] = new BooleanVariable();
        }
        BaseMatrix a = new BaseMatrix(v, b, elements);
        for (int i=0; i<v; i++) {
            add(new Exactly(r, (BooleanVariable[]) a.getLine(i),1));
        }
        for (int j=0; j<b; j++) {
            add(new Exactly(k, (BooleanVariable[]) a.getColumn(j),1));
        }
        for (int i1=0; i1<v-1; i1++) {
            for (int i2=i1+1; i2<v; i2++) {
                BooleanVariable[] x = new BooleanVariable[b];
                for (int j=0; j<b; j++) {
                    x[j] = new BooleanVariable();
                    add(new And(x[j], 
                                (BooleanVariable) a.getElement(i1, j),
                                (BooleanVariable) a.getElement(i2, j)));
                }   
                add(new Exactly(lambda, x, 1)); 
                // faster than 
                // add(new ConstantSum(x, lambda));
            }
        }
        // removing symetries on rows and colums
        add(new LexIncreasing(a, true, true));
        setVariables(elements);
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
        new DefaultSplitSolver(new BIBDProblem(6, 60, 30, 3, 12)).solve(1);
    }
}
