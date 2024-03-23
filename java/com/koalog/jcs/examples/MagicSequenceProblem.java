package com.koalog.jcs.examples;

import org.apache.log4j.PropertyConfigurator;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.arithmetic.ConstantWeightedSum;
import com.koalog.jcs.constraint.arithmetic.ConstantSum;
import com.koalog.jcs.constraint.arithmetic.Count;

/**
 * Find a sequence x_0, ... x_n-1 
 * such that each x_i is the number of occurence of i in the sequence.
 *
 * <P>Note: This is problem #19 in CSPLib.  
 *
 * @author Yan Georget
 */
public class MagicSequenceProblem extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param n the size of the sequence
     */
    public MagicSequenceProblem(int n) {
        super();
        IntegerVariable[] x = new IntegerVariable[n];
        int c[] = new int[n];
        c[0] = 0;
        x[0] = new IntegerVariable("x0", 1, n-2);
        c[1] = 1;
        x[1] = new IntegerVariable("x1", 0, n-2);
        for (int i=2; i<n-1; i++) {
            c[i] = i;
            x[i] = new IntegerVariable("x" + i, 0, (int) ((n-1)/i));
        }
        c[n-1] = n-1;
        x[n-1] = new IntegerVariable("x" + (n-1), 0);
        for (int i=0; i<n-1; i++) {
            add(new Count(x[i], x, i));
        }
        // redundant constraints
        add(new ConstantSum(x, n));
        add(new ConstantWeightedSum(c, x, n));
        // we inverse the order of the variables
        List l = Arrays.asList(x);
        Collections.reverse(l);
        setVariables((IntegerVariable[]) l.toArray(new IntegerVariable[] {}));
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
        new DefaultSplitSolver(new MagicSequenceProblem(800)).solve(1);
    }
}
