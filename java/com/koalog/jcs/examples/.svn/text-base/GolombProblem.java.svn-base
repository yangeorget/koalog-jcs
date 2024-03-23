package com.koalog.jcs.examples;

import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.PropertyConfigurator;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.arithmetic.AllDifferent;
import com.koalog.jcs.constraint.arithmetic.Add;
import com.koalog.jcs.constraint.arithmetic.Less;
import com.koalog.jcs.constraint.arithmetic.LeqShift;

/**
 * This is the famous Golomb ruler problem.
 * It consists in finding n integers mark_i such that:
 * <ul>
 * <li>mark_0 = 0,</li>
 * <li>mark_0 <...< mark_n-1,</li>
 * <li>for all i<j, mark_j-mark_i are different,</li>
 * <li>mark_n-1 is minimal.</li>
 * </ul>
 * 
 * <P>Note: This is problem #6 in CSPLib.
 * 
 * @author Yan Georget
 */
public class GolombProblem extends BaseProblem {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    // optimal golomb rulers of small size
    private static int[] golomb = 
        new int[] {0, // golomb 0 (0 mark!) 
                   0, // golomb 1 (1 mark!)
                   1, // golomb 2
                   3, // golomb 3
                   6, // golomb 4
                   11, // golomb 5
                   17, // golomb 6
                   25, // golomb 7
                   34, // golomb 8
                   44, // golomb 9
                   55, // golomb 10
                   72, // golomb 11
                   85, // golomb 12
                   106, // golomb 13
                   127, // golomb 14
        };


    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The number of marks. */
    int n;
    /** 
        The distances between marks. 
        The mark i is modelled as dist[0][i].
     */
    IntegerVariable[][] dist;
    
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param n the size of the problem
     */
    public GolombProblem(int n) {
        this.n = n;
        // list of distances between marks
        List l = new ArrayList(n*(n-1)/2);
        // distance variables
        dist = new IntegerVariable[n][n];
        dist[0][0] = new IntegerVariable("0_0", 0);
        //l.add(dist[0][0]);
        for (int i=0; i<n-1; i++) {
            for (int j=i+1; j<n; j++) {
                // we can always use sum(j-i) as a lower bound
                // dist[i][j] is indeed the sum of j-i distinct integers
                // moreover, we can use golomb[j-i+1] 
                // (which is a smaller golomb) as a lower bound
                dist[i][j] = 
                    new IntegerVariable(i + "_" + j, 
                                        j-i<n-1 ? golomb[j-i+1] : sum(j-i), 
                                        Integer.MAX_VALUE/n);
                l.add(dist[i][j]);
                if (i > 0) {
                    add(new Add(dist[0][j], dist[0][i], dist[i][j]));
                }
            }
        }
        add(new AllDifferent((IntegerVariable[]) 
                                     l.toArray(new IntegerVariable[] {})));
        // redundant constraints
        for (int i=0; i<n-1; i++) {
            for (int j=i+1; j<n; j++) {
                // dist[i][j] = length - (sum of n-1-j+i distinct integers) 
                if (j-i < n-1) {
                    add(new LeqShift(dist[i][j], 
                                             dist[0][n-1], 
                                             -sum(n-1-j+i))); 
                }
            }
        }
        // to remove symetrical solutions
        add(new Less(dist[0][1], dist[n-2][n-1])); 
        // definition of the problem variables (variables to be labelled)
        // here, the marks
        setVariables(dist[0]);
        // TODO: use AllDifferentSum here?
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
        new GolombMinimizer(new GolombProblem(10)).optimize();
    }

    /**
     * Returns 1 + ... + n.
     * @param n an integer
     * @return an integer
     */
    static int sum(int n) {
        return n*(n+1)/2;
    }
}
