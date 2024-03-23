package com.koalog.jcs.examples;

import java.util.*;
import org.apache.log4j.Category;
import com.koalog.jcs.*;
import com.koalog.jcs.variable.*;
import com.koalog.jcs.solver.*;
import com.koalog.jcs.domain.*;

/**
 *
 * @author Yan Georget
 */
public class CMC05Solver extends SplitSolver {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(CMC05Problem.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    int[][] s;
    int m;
    int n;
    IntegerVariable[] p;
    BooleanVariable[][] o;
    BooleanVariable[][] c;
    IntegerVariable max;
    int[] pos;
    
    boolean[][] b;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /** 
     * Sole constructor
     * @param p a problem
     */
    public CMC05Solver(CMC05Problem prob) {
        super(prob, 
              new SmallestDomainVariableHeuristic(),
              new SplitLowDomainHeuristic());
        m = prob.m;
        n = prob.n;
        p = prob.p;
        s = prob.s;
        o = prob.o;
        c = prob.c;
        max = prob.max;
        b = new boolean[n][m];
        // a sequence of positions to explore
        pos = new int[m];
        if (m%2 == 0) {
            pos[0] = m/2;
            for (int j=1; j<m/2; j++) {
                pos[2*j-1] = m/2 - j;
                pos[2*j] = m/2 + j;
            }
            pos[m-1] = 0;
        } else {
            pos[0] = (m-1)/2;
            for (int j=1; j<(m-1)/2; j++) {
                pos[2*j-1] = (m-1)/2 - j;
                pos[2*j] = (m-1)/2 + j;
            }
        }
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    public boolean choice() {
        // ((CMC05Problem) problem).dump();
        for (int j=0; j<m; j++) { // we iterate over the products
            List positions = new ArrayList();
            for (int k=0; k<m; k++) {
                IntegerDomain dom = (IntegerDomain) p[pos[k]].getDomain();
                if (!dom.isSingleton() && dom.contains(j)) {
                    positions.add(new Integer(pos[k]));
                }
            }
            // now we pick the best position
            if (!positions.isEmpty()) {
                int cost = Integer.MAX_VALUE;
                int position = -1;
                for (Iterator l = positions.iterator(); l.hasNext();) {
                    int pos = ((Integer) l.next()).intValue();
                    int c = cost(j, pos);
                    if (c < cost) {
                        cost = c;
                        position = pos;
                    }
                }
                /*
                if (cost>max.getMax()) {
                    cat.info("for product " + j + ":" + cost + ">" + max.getMax());
                    ((CMC05Problem) problem).dump();
                }
                */
                choicePoints.push();
                p[position].chooseValue(choicePoints, 
                                        constraintScheduler, 
                                        j);
                return true;
            }
        }
        return false;
    }

    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.solver.BacktrackSolver */
    public void filter() throws ConstraintInconsistencyException {
        super.filter();
        prune();
    }

    /**
     * A method for pruning the search space.
     *
     * @throws VariableInconsistencyException when there is no solution
     */
    public void prune() throws VariableInconsistencyException {
        for (int j=0; j<m; j++) {
            IntegerDomain dom = (IntegerDomain) p[j].getDomain();
            for (Iterator k = dom.iterator(); k.hasNext();) {
                Integer product = (Integer) k.next();
                if (testCost(product.intValue(), j, max.getMax())) {
                    p[j].adjustDifferent(choicePoints, 
                                         constraintScheduler,
                                         null,
                                         product,
                                         k);
                }
            }
        }
    }

    private boolean testCost(int product, int position, int max) {
        matrix(product, position);
        return testCost(max);

    }

    private int cost(int product, int position) {
        matrix(product, position);
        return cost();

    }

    public void matrix(int product, int position) {
        for (int j=m; --j>=0;) {
            for (int i=n; --i>=0;) {
                b[i][j] = c[i][j].isTrue();
            }
        }
        for (int i=n; --i>=0;) {
            if (s[i][product] == 1) {
                b[i][position] = true;
            }
        }
        for (int i=0; i<n; i++) {
            int min = 0;
            while(min < m && !b[i][min]) {
                min++;
            }
            int max = m-1;
            while (max >=0 && !b[i][max]) {
                max--;
            }
            for (int j=min; j<=max; j++) {
                b[i][j] = true;
            }
        }
    }

    public boolean testCost(int max) {
        for (int j=m; --j>=0;) {
            int sum = 0;
            for (int i=n; --i>=0;) {
                if (b[i][j] || o[i][j].isTrue()) {
                    sum++;
                }
            }
            if (sum > max) {
                return true;
            }
        }
        return false;
    }

    public int cost() {
        int max = Integer.MIN_VALUE;
        for (int j=m; --j>=0;) {
            int sum = 0;
            for (int i=n; --i>=0;) {
                if (b[i][j] || o[i][j].isTrue()) {
                    sum++;
                }
            }
            if (sum > max) {
                max = sum;
            }
        }
        return max;
    }

    public void solutionFound() {
        super.solutionFound();
        ((CMC05Problem) problem).dump();
    }
}
