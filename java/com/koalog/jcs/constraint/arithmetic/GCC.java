package com.koalog.jcs.constraint.arithmetic;

import org.apache.log4j.Category;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.constraint.InvalidConstraintException;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.domain.IntegerDomain;
import com.koalog.jcs.variable.IntegerVariable;

/**
 * A Global Cardinality Constraint (GCC).
 *
 * <P>Algorithmic informations 
 * (see 
 * {@link com.koalog.jcs.constraint.Constraint Constraint}
 * for more details on complexity and idempotence):
 * <TABLE border="1">
 * <TR>
 *    <TD><B>Idempotent</B></TD>
 *    <TD align="right">when variables have interval domains</TD>
 * </TR>
 * <TR>
 *    <TD><B>Complexity</B></TD>
 *    <TD align="right">variables.length*ln(variables.length)</TD>
 * </TR>
 * </TABLE>
 *
 * <P>This constraint is based on the algorithm of 
 * Quimper, van Beek, Lopez-Ortiz, Golynski, Sadjad (CP'03 paper).</P>
 *
 * @since 2.5
 * @author Yan Georget
 */
public class GCC extends AllDifferent {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(GCC.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** Stable sets. */
    int[] stableInterval; 
    /** Links elements that potentialy belong to same stable set. */
    int[] potentialStableSets; 
    int[] newMin;
    int firstDomainValue;
    int lastDomainValue;
    PartialSum l;
    PartialSum u;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Main constructor.
     * @param variables an array of integer variables 
     * @param firstDomainValue first admissible value for the variables
     * @param lastDomainValue last admissible value for the variables
     * @param low an array of lower bounds, 
     * each of them is a positive integer
     * @param cap an array of capacities, 
     * each of them is an integer smaller than the number of variables
     */
    public GCC(IntegerVariable[] variables, 
               int firstDomainValue,
               int lastDomainValue,
               int[] low, 
               int[] cap) {
        super(variables);
        final int range = lastDomainValue - firstDomainValue + 1;
        checkArguments(variables, 
                       range, 
                       low, 
                       cap);
        this.name = "gcc"; // do we want to pretty-print this constraint?
        this.firstDomainValue = firstDomainValue;
        this.lastDomainValue = lastDomainValue;
        stableInterval = new int[2*n+2]; 
        potentialStableSets = new int[2*n+2];
        newMin = new int[n];
        l = new PartialSum(firstDomainValue, range, low);
        u = new PartialSum(firstDomainValue, range, cap);
    }

    /**
     * Auxilliary constructor.
     * @param variables an array of integer variables with sparse domains
     * @param firstDomainValue first admissible value for the variables
     * @param lastDomainValue last admissible value for the variables
     * @param low a common lower bound, 
     * a positive integer
     * @param cap a common capacity, 
     * an integer smaller than the number of variables
     */
    public GCC(IntegerVariable[] variables, 
               int firstDomainValue,
               int lastDomainValue,
               int low,
               int cap) {
        this(variables, 
             firstDomainValue,
             lastDomainValue,
             boundsArray(low, lastDomainValue - firstDomainValue + 1), 
             boundsArray(cap, lastDomainValue - firstDomainValue + 1));
    }

   
   /**
     * Auxilliary constructor.
     * @param variables an array of integer variables with sparse domains
     * @param firstDomainValue first admissible value for the variables
     * @param lastDomainValue last admissible value for the variables
     * @param occ an array of occurences
     */
    public GCC(IntegerVariable[] variables, 
               int firstDomainValue,
               int lastDomainValue,
               int[] occ) {
        this(variables, firstDomainValue, lastDomainValue, occ, occ);
    }

    /**
     * Auxilliary constructor.
     * @param variables an array of integer variables with sparse domains
     * @param firstDomainValue first admissible value for the variables
     * @param lastDomainValue last admissible value for the variables
     * @param occ a common occurence
     */
    public GCC(IntegerVariable[] variables, 
               int firstDomainValue,
               int lastDomainValue,
               int occ) {
        this(variables, firstDomainValue, lastDomainValue, occ, occ);
    }

    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    static void checkArguments(IntegerVariable[] variables, 
                               int range,
                               int[] low, 
                               int[] cap) {
        if (range != cap.length || range != low.length) {
            throw new InvalidConstraintException("pb with array sizes");
        }
        for (int i=0; i<low.length; i++) {
            if (low[i] < 0) {
                throw new InvalidConstraintException("low[" + i + "] < 0");
            }
        }
        for (int i=0; i<cap.length; i++) {
            if (cap[i] > variables.length) {
                throw new InvalidConstraintException("cap[" + i + "] > #vars");
            }
        }
    }

    static int[] boundsArray(int v, int l) {
        int[] a = new int[l];
        for (int i=0; i<l; i++) {
            a[i] = v;
        }
        return a;
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp, 
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        // TODO: make it incremental?
        for (int i=n; --i>=0;) {
            iv[i].setMinMax((IntegerDomain) variables[i].getDomain());
        }  
        sort();
        if (l.sum(l.minValue(), minsorted[0].getMin() - 1) > 0 
            || l.sum(maxsorted[n-1].getMax() + 1, l.maxValue()) > 0) {
            fail();
        }
        filterLowerMax();
        filterLowerMin(t, d, h, stableInterval, potentialStableSets, newMin);
        filterUpperMax();
        filterUpperMin(t, d, h, stableInterval, newMin);
        for (int i=n; --i>=0;) {
            ((IntegerVariable) variables[i]).adjustMinMax(cp, 
                                                          cs, 
                                                          this,
                                                          iv[i].getMin(), 
                                                          iv[i].getMax());
        }
    }
    
    final void sort() {
        sortMin();
        sortMax();
        int min = minsorted[0].getMin();
        int max = maxsorted[0].getMax() + 1;
        int last = l.firstValue + 1;
        bounds[0] = last;
        int i = 0;
        int j = 0;
        int nb = 0;
        for (;;) { // merge minsorted[] and maxsorted[] into bounds[]
            if (i<n && min<=max) {      // make sure minsorted exhausted first
                if (min != last) {
                    bounds[++nb] = last = min;
                }
                minsorted[i].minrank = nb;
                if (++i < n) {
                    min = minsorted[i].getMin();
                }
            } else {
                if (max != last) {
                    bounds[++nb] = last = max;
                }
                maxsorted[j].maxrank = nb;
                if (++j == n) {
                    break;
                }
                max = maxsorted[j].getMax() + 1;
            }
        }
        this.nb = nb;
        bounds[nb+1] = u.lastValue + 1;
    }


    /**
     * Shrinks the lower bounds for the max occurrences problem.
     */
    private final void filterLowerMax() 
        throws ConstraintInconsistencyException {
        for (int i=1; i<=nb+1; i++) {
            t[i] = h[i] = i-1;
            d[i] = u.sum(bounds[i-1], bounds[i]-1);
        }
        for (int i=0; i<n; i++) { // visit intervals in increasing max order
            // get interval bounds
            int x = maxsorted[i].minrank; 
            int y = maxsorted[i].maxrank;
            int z = pathmax(t, x+1);
            int j = t[z];
            if (--d[z] == 0) {
                t[z = pathmax(t, t[z]=z+1)] = j;
            }
            pathset(t, x+1, z, z);
            if (d[z] < u.sum(bounds[y], bounds[z] - 1)) {
                fail();
            }
            if (h[x] > x) {
                int w = pathmax(h, h[x]);
                maxsorted[i].setMin(bounds[w]);
                pathset(h, x, w, w);
                // changes = 1;
            }
            if (d[z] == u.sum(bounds[y], bounds[z] - 1)) {
                pathset(h, h[y], j-1, y); // mark hall interval
                h[y] = j-1; 
            }
        }
    }

    /**
     * Shrinks the upper bounds for the max occurrences problem.
     */
    private final void filterUpperMax() 
        throws ConstraintInconsistencyException {
        for (int i=0; i<=nb; i++) {
            d[i] = u.sum(bounds[i], bounds[t[i]=h[i]=i+1]-1);
        }
        for (int i=n; --i>=0; ) { // visit intervals in decreasing min order
            // get interval bounds
            int x = minsorted[i].maxrank; 
            int y = minsorted[i].minrank;
            int z = pathmin(t, x-1);
            int j = t[z];
            if (--d[z] == 0) {
                t[z = pathmin(t, t[z]=z-1)] = j;
            }
            pathset(t, x-1, z, z);
            if (d[z] < u.sum(bounds[z], bounds[y]-1)) {
                fail();
            }
            if (h[x] < x) {
                int w = pathmin(h, h[x]);
                minsorted[i].setMax(bounds[w] - 1);
                pathset(h, x, w, w);
                // changes = 1;
            }
            if (d[z] == u.sum(bounds[z], bounds[y]-1)) {
                pathset(h, h[y], j+1, y);
                h[y] = j+1;
            }
        }
    }

    /** 
     * Shrinks the lower bounds for the min occurrences problem.
     */
    private final void filterLowerMin(int[] tl, 
                                      int[] c,
                                      int[] stableAndUnstableSets,
                                      int[] stableInterval,
                                      int[] potentialStableSets,
                                      int[] newMin) 
        throws ConstraintInconsistencyException {
        int i,j,w,x,y,z,v;
        for (w=i=nb+1; i>0; i--) {
            potentialStableSets[i] = stableInterval[i] = i-1;
            c[i] = l.sum(bounds[i-1], bounds[i]-1);
            // If the capacity between both bounds is zero, we have
            // an unstable set between these two bounds.
            if (c[i] == 0) {
                stableAndUnstableSets[i-1] = w;
            } else {
                w = stableAndUnstableSets[w] = i - 1;
            }
        }
        for (i = w = nb + 1; i >= 0; i--) {
            if (c[i] == 0) {
                tl[i] = w;
            } else {
                w = tl[w] = i;
            }
        }
        for (i = 0; i < n; i++) { // visit intervals in increasing max order
            // Get interval bounds
            x = maxsorted[i].minrank; 
            y = maxsorted[i].maxrank;
            j = tl[z = pathmax(tl, x+1)];
            if (z != x+1) {
                // if bounds[z] - 1 belongs to a stable set,
                // [bounds[x], bounds[z]) is a sub set of this stable set
                v = potentialStableSets[w = pathmax(potentialStableSets, 
                                                    x + 1)];
                pathset(potentialStableSets, x+1, w, w); // path compression
                w = y < z ? y : z;
                pathset(potentialStableSets, potentialStableSets[w], v , w);
                potentialStableSets[w] = v;
            }
            
            if (c[z] <= l.sum(bounds[y], bounds[z] - 1)) {
                // (potentialStableSets[y], y] is a stable set
                w = pathmax(stableInterval, potentialStableSets[y]);
                pathset(stableInterval, 
                        potentialStableSets[y], w, w); // Path compression
                pathset(stableInterval, 
                        stableInterval[y], 
                        v=stableInterval[w], 
                        y);
                stableInterval[y] = v;
            } else {
                // Decrease the capacity between the two bounds
                if (--c[z] == 0) {
                    tl[z = pathmax(tl, tl[z]=z+1)] = j;
                }
                // If the lower bound belongs to an unstable or a stable set,
                // remind the new value we might assigned to the lower bound
                // in case the variable doesn't belong to a stable set.
                if (stableAndUnstableSets[x] > x) {
                    w = newMin[i] = pathmax(stableAndUnstableSets, x);
                    pathset(stableAndUnstableSets, 
                            x, 
                            w, 
                            w); // path compression
                } else {
                    newMin[i] = x; // Do not shrink the variable
                }
                // If an unstable set is discovered
                if (c[z] == l.sum(bounds[y], bounds[z] - 1)) {
                    // Consider stable and unstable sets beyong y
                    if (stableAndUnstableSets[y] > y) { 
                        // Equivalent to pathmax 
                        // since the path is fully compressed
                        y = stableAndUnstableSets[y]; 
                    }
                    pathset(stableAndUnstableSets, 
                            stableAndUnstableSets[y], 
                            j-1, 
                            y); // mark the new unstable set
                    stableAndUnstableSets[y] = j-1;
                }
            }
            pathset(tl, x+1, z, z); // path compression
        }
        // If there is a failure set
        if (stableAndUnstableSets[nb] != 0) {
            fail();
        }
        // Perform path compression over all elements in
        // the stable interval data structure. This data
        // structure will no longer be modified and will be
        // accessed n or 2n times. Therefore, we can afford
        // a linear time compression.
        for (i = nb+1; i > 0; i--) {
            if (stableInterval[i] > i) {
                stableInterval[i] = w;
            } else {
                w = i;
            }
        }
        // For all variables that are not a subset of a stable set, 
        // shrink the lower bound
        for (i=n-1; i>=0; i--) {
            x = maxsorted[i].minrank; 
            y = maxsorted[i].maxrank;
            if (stableInterval[x] <= x || y > stableInterval[x]) {
                maxsorted[i]
                    .setMin(l.skipNonNullElementsRight(bounds[newMin[i]]));
                // changes = 1;
            }
        }
    }
    
    /**
     * Shrinks the upper bounds for the min occurrences problem.
     */
    private final void filterUpperMin(int[] tl, 
                                      int[] c,
                                      int[] stableAndUnstableSets,
                                      int[] stableInterval,
                                      int[] newMax) {
        int i,j,w,x,y,z;
        for (w=i=0; i<=nb; i++) {
            c[i] = l.sum(bounds[i], bounds[i+1]-1);
            if (c[i] == 0) {
                tl[i]=w;
            } else { 
                w=tl[w]=i;
            }
        }
        tl[w]=i;
        for (i = 1, w = 0; i<=nb; i++) {
            if (c[i-1] == 0) {
                stableAndUnstableSets[i] = w;
            } else {
                w = stableAndUnstableSets[w] = i;
            }
        }
        stableAndUnstableSets[w] = i;
        for (i=n; --i>=0; ) { // visit intervals in decreasing min order
            // Get interval bounds
            x = minsorted[i].maxrank; 
            y = minsorted[i].minrank;
            // Solve the lower bound problem
            j = tl[z = pathmin(tl, x-1)];
            // If the variable is not in a discovered stable set
            // Possible optimization: 
            // use the array stableInterval to perform this test
            if (c[z] > l.sum(bounds[z], bounds[y]-1)) {
                if (--c[z] == 0) {
                    tl[z = pathmin(tl, tl[z]=z-1)] = j;
                }
                if (stableAndUnstableSets[x] < x) {
                    newMax[i] = w = pathmin(stableAndUnstableSets, 
                                            stableAndUnstableSets[x]);
                    // path compression
                    pathset(stableAndUnstableSets, x, w, w); 
                } else {
                    newMax[i] = x;
                }
                if (c[z] == l.sum(bounds[z], bounds[y]-1)) {
                    if (stableAndUnstableSets[y] < y) {
                        y = stableAndUnstableSets[y];
                    }
                    pathset(stableAndUnstableSets, 
                            stableAndUnstableSets[y], 
                            j+1, 
                            y);
                    stableAndUnstableSets[y] = j+1;
                }
            }
            pathset(tl, x-1, z, z);
        }
        // For all variables that are not subsets of a stable set, 
        // shrink the lower bound
        for (i=n-1; i>=0; i--) {
            x = minsorted[i].minrank; 
            y = minsorted[i].maxrank;
            if (stableInterval[x] <= x || y > stableInterval[x]) {
                minsorted[i]
                    .setMax(l.skipNonNullElementsLeft(bounds[newMax[i]]-1));
                // changes = 1;
            }
        }
    }
    
   
    
    //------------------------------------------------------------------------
    // INNER CLASSES
    //------------------------------------------------------------------------
    static class PartialSum {
        int firstValue;
        int lastValue;
        int[] sum;
        int[] ds;

        /**
         * Creates a partial sum data structure adapted to the
         * filterLower{Min,Max} and filterUpper{Min,Max} functions.
         * Two elements before and after the element list will be added
         * with a weight of 1. 
         */
        PartialSum(int firstValue, int count, int[] elements) {
            // We add three elements at the beginning
            this.firstValue = firstValue - 3; 
            lastValue = firstValue + count + 1;
            sum = new int[count+5];
            sum[0] = 0;
            sum[1] = 1;
            sum[2] = 2;
            int i;
            for (i = 2; i < count+2; i++) {
                sum[i+1] = sum[i] + elements[i-2];
            }
            sum[i+1] = sum[i] + 1;
            sum[i+2] = sum[i+1] + 1;
            ds = new int[count+5];
            int j=(i=count+3)+1;
            while (i > 0) {
                while (sum[i] == sum[i-1]) {
                    ds[i--]=j;
                }
                j=ds[j]=i--;
            }
            ds[j]=0;
        }

        private final int sum(int from, int to) {
            if (from <= to) {
                return sum[to - firstValue] - sum[from - firstValue - 1];
            } else { 
                return sum[to - firstValue - 1] - sum[from - firstValue];
            }
        }

        private final int minValue() {
            return firstValue + 3;
        }

        private final int maxValue() {
            return lastValue - 2;
        }

        private final int skipNonNullElementsRight(int value) {
            value -= firstValue;
            return (ds[value] < value ? value : ds[value]) + firstValue;
        }
        
        private final int skipNonNullElementsLeft(int value) {
            value -= firstValue;
            return (ds[value] > value ? ds[ds[value]] : value) + firstValue;
        }
    }
}
/*
 * $Log$
 * Revision 1.34  2005/07/27 20:32:44  yan
 * fixed jdoc
 *
 * Revision 1.33  2005/07/25 09:48:21  yan
 * fixed 0021281
 *
 * Revision 1.32  2005/07/20 07:04:35  yan
 * fixed checks
 *
 * Revision 1.31  2005/07/19 20:58:48  yan
 * added checks
 *
 * Revision 1.30  2005/07/19 20:23:26  yan
 * added check
 *
 * Revision 1.29  2005/07/19 17:31:36  yan
 * tiny optim
 *
 * Revision 1.28  2005/07/18 15:32:55  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.27  2005/03/30 10:22:39  yan
 * new GCC (bound)
 *
 */
