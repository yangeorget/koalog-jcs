package com.koalog.jcs.examples;

import org.apache.log4j.PropertyConfigurator;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.arithmetic.ConstantWeightedSum;
import com.koalog.jcs.constraint.arithmetic.Exactly;

/**
 * This is a partition problem.
 * Given the set S = {1, 2, ..., n}, 
 * it consists in finding two sets A and B such that:
 * <ul>
 * <li>A U B = S,</li>
 * <li>|A| = |B|,</li>
 * <li>sum(A) = sum(B),</li>
 * <li>sum_squares(A) = sum_squares(B).</li>
 * </ul>
 *
 * @author Yan Georget
 */
public class PartitionProblem extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param n the size of the problem
     */
    public PartitionProblem(int n) {
        super();
        int[] s = new int[n];
        int[] ss = new int[n];
        // b[i] <=> i+1 is in set A
        BooleanVariable[] b = new BooleanVariable[n];
        for (int i=0; i<n; i++) {
            s[i] = i+1;
            ss[i] = s[i]*s[i];
            b[i] = new BooleanVariable("_" + s[i]);
        }
        add(new Exactly(n/2, b, 1));
        add(new ConstantWeightedSum(s, b, n*(n+1)/4));
        add(new ConstantWeightedSum(ss, b, n*(n+1)*(2*n+1)/12));
        setVariables(b);
    }

    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    /**
     * Runs the problem.
     * @param args the command line arguments:
     * args[0] must contain a log4j properties file location
     */
    public static void main(String[] args) {
        PropertyConfigurator.configure(args[0]);
        new DefaultSplitSolver(new PartitionProblem(8)).solve(1);
    }
}
