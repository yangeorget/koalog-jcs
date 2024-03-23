package com.koalog.jcs.examples;

import org.apache.log4j.Category;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.VariableInconsistencyException;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.solver.DefaultSplitSolver;

/**
 * A solver for the Golomb problem.
 *
 * @author Yan Georget
 */
public class GolombSolver extends DefaultSplitSolver {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(GolombSolver.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The size of the problem. */
    private int n;
    /** The distances between marks. */
    private IntegerVariable[][] dist;
    /** 
        A reusable array for storing the minimal sum of different integers:
        sum[i] will be the minimal sum of i different integers chosen among a 
        set of possible integers.
    */
    private int[] sum;
    /** 
        A boolean array for marking already existing distances as used numbers.
    */
    private boolean[] used;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param problem a golomb problem
     */
    public GolombSolver(GolombProblem problem) {
        super(problem);
        dist = problem.dist;
        n = problem.n;
        sum = new int[n-1];
        sum[0] = 0;
        used = new boolean[GolombProblem.sum(n-2)+1];
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.solver.BacktrackSolver */
    public void filter() throws ConstraintInconsistencyException {
        prune();
        consistencyFilter();
    }

    /**
     * A method for pruning the search space.
     *
     * @throws VariableInconsistencyException when there is no solution
     */
    public void prune() 
        throws VariableInconsistencyException {
        // we compute the index of the first non instantiated var 
        // at least 1 since dist[0][0] = 0
        int index = 0;
        while (++index < n) {
            if (dist[0][index].isNotInstantiated()) {
                break;
            } 
        }
        if (1 < index && index < n-1) { // otherwise useless
            for (int i=used.length; --i>=0;) {
                used[i] = false;
            }
            // used[0] = true;
            // the following will mark at most sum(n-3) numbers as used
            // hence there will be at least n-2 unused numbers greater than 0
            for (int i=0; i<index-1; i++) {
                final int di = dist[0][i].getMin();
                for (int j=i+1; j<index; j++) {
                    final int d = dist[0][j].getMin() - di;
                    if (d < used.length) {
                        used[d] = true;
                    }
                }
            }
            // computing the sums of non marked numbers
            int l = 0;
            final int sumMaxIdx = n - index; // (n-1)-(index-1)
            for (int j=1; j<=sumMaxIdx; j++) {
                while (used[++l]) {}
                sum[j] = sum[j-1] + l;
            }
            for (int i=index-1; i<n-1; i++) {
                for (int j=i+1; j<n; j++) { 
                    dist[i][j].adjustMin(choicePoints, 
                                         constraintScheduler,
                                         null,
                                         sum[j-i]);
                }
            }
        }
    }
}
