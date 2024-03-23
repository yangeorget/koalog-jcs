package com.koalog.jcs.examples;

import org.apache.log4j.PropertyConfigurator;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.arithmetic.Mul;
import com.koalog.jcs.constraint.arithmetic.WeightedSum;
import com.koalog.jcs.constraint.arithmetic.SmallerSum;

/**
 * This is the famous Knapsack problem.
 * Given:
 * <ul>
 * <li>n objects of given volumes and weights,</li>
 * <li>a bag of a given capacity (volume),</li>
 * it consists in putting in the bag as much weight as possible.
 * </ul>
 *
 * @author Yan Georget
 */
public class KnapsackProblem extends BaseProblem {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    IntegerVariable weight;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param volumes the volumes
     * @param weights the weights
     * @param capacity the capacity
     */
    public KnapsackProblem(int[] volumes, int[] weights, int capacity) {
        int n = volumes.length;
        BooleanVariable[] b = new BooleanVariable[n];
        IntegerVariable[] v = new IntegerVariable[n];
        for (int i=0; i<n; i++) {
            b[i] = new BooleanVariable("b" + i);
            v[i] = new IntegerVariable("v" + i);
            add(new Mul(v[i], volumes[i], b[i]));
        }
        weight = new IntegerVariable("weight");
        add(new WeightedSum(weight, weights, b));
        add(new SmallerSum(v, capacity+1));
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
    public static void main(String args[]) {
        PropertyConfigurator.configure(args[0]);
        int[] volumes = new int[] {
            40,40,38,38,36,36,34,34,32,32,30,30,28,28,26,26,24,24,22,22
        };
        int[] weights = new int[] {
            40,40,38,38,36,36,34,34,32,32,30,30,28,28,26,26,24,24,22,22
        };
        new KnapsackMaximizer(new KnapsackProblem(volumes, 
                                                  weights, 
                                                  55)).optimize();
    }
}
