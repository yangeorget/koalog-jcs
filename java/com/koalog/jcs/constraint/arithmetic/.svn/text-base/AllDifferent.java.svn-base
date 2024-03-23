package com.koalog.jcs.constraint.arithmetic;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.constraint.BaseConstraint;
import com.koalog.jcs.domain.IntegerDomain;
import com.koalog.jcs.domain.MinMaxDomain;
import com.koalog.jcs.variable.IntegerVariable;

/**
 * This constraint enforces that all of its variables are different.
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
 * Lopez-Ortiz, Quimper, Tromp, van Beek (IJCAI'03 paper).</P>
 *
 * @author Yan Georget
 */
public class AllDifferent extends BaseConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    int nb;
    int n;
    RankDomain[] minsorted;
    RankDomain[] maxsorted;
    RankDomain[] iv;
    int[] bounds;
    int[] t;
    int[] d;
    int[] h;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param variables an array of integer variables
     */
    public AllDifferent(IntegerVariable[] variables) {
        super(variables);
        this.name = "alldifferent";
        complexity = (int) (variables.length*Math.log(variables.length));
        idempotent = !IntegerVariable.oneSparse(variables);
        n = variables.length;
        iv = new RankDomain[n];
        minsorted = new RankDomain[n];
        maxsorted = new RankDomain[n];
        for (int i=n; --i>=0;) {
            minsorted[i] = maxsorted[i] = iv[i] = 
                new RankDomain((IntegerDomain) variables[i].getDomain());
        }
        bounds = new int[2*n+2];
        t = new int[2*n+2];
        d = new int[2*n+2];
        h = new int[2*n+2];
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        for (int i=0; i<variables.length; i++) {
            ((IntegerVariable) variables[i]).addMinMaxConstraint(this);
        }
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        for (int i=n; --i>=0;) {
            iv[i].setMinMax((IntegerDomain) variables[i].getDomain());
        }
        sort();
        filterLower();
        filterUpper();
        for (int i=n; --i>=0;) {
            ((IntegerVariable) variables[i]).adjustMinMax(cp, 
                                                          cs, 
                                                          this,
                                                          iv[i].getMin(), 
                                                          iv[i].getMax());
        }
    }

    private void filterLower() throws ConstraintInconsistencyException {
        for (int i=0; i<=nb; i++) {
            final int i1 = i+1;
            t[i1] = h[i1] = i;
            d[i1] = bounds[i1] - bounds[i];
        }
        for (int i=0; i<n; i++) { // visit intervals in increasing max order
            final int x = maxsorted[i].minrank;
            final int y = maxsorted[i].maxrank;
            final int x1 = x+1;
            int z = pathmax(t, x1);
            final int j = t[z];
            if (--d[z] == 0) {
                t[z] = z+1;
                z = pathmax(t, t[z]);
                t[z] = j;
            }
            pathset(t, x1, z, z); // path compression
            if (d[z] < bounds[z]-bounds[y]) {
                fail();
            }
            if (h[x] > x) {
                final int w = pathmax(h, h[x]);
                maxsorted[i].setMin(bounds[w]);
                pathset(h, x, w, w); // path compression
            }
            if (d[z] == bounds[z]-bounds[y]) {
                final int j1 = j-1;
                pathset(h, h[y], j1, y); // mark hall interval
                h[y] = j1; // hall interval [bounds[j],bounds[y]]
            }
        }
    }

    private void filterUpper() throws ConstraintInconsistencyException {
        for (int i=0; i<=nb; i++) {
            final int i1= i+1;
            t[i] = h[i] = i1;
            d[i] = bounds[i1] - bounds[i];
        }
        for (int i=n; --i>=0;) { // visit intervals in decreasing min order
            final int x = minsorted[i].maxrank;
            final int y = minsorted[i].minrank;
            final int x1 = x-1;
            int z = pathmin(t, x1);
            final int j = t[z];
            if (--d[z] == 0) {
                t[z] = z-1;
                z = pathmin(t, t[z]);
                t[z] = j;
            }
            pathset(t, x1, z, z); // path compression
            if (d[z] < bounds[y]-bounds[z]) {
                fail();
            }
            if (h[x] < x) {
                final int w = pathmin(h, h[x]);
                minsorted[i].setMax(bounds[w] - 1);
                pathset(h, x, w, w); // path compression
            }
            if (d[z] == bounds[y]-bounds[z]) {
                final int j1= j+1;
                pathset(h, h[y], j1, y); // mark hall interval
                h[y] = j1; // hall interval [bounds[j],bounds[y]]
            }
        }
    }

    void sortMin() {
        int current = n-1;
        boolean nonSorted = true;
        while( nonSorted ) {
            nonSorted = false;
            for(int i = 0; i < current; i++) {
                final int i1 = i+1;
                if( minsorted[i].getMin() > minsorted[i1].getMin() ) {
                    final RankDomain t = minsorted[i];
                    minsorted[i] = minsorted[i1];
                    minsorted[i1] = t;
                    nonSorted = true;
                }
            }
            current--;
        }
    }

    void sortMax() {
        int current = 0;
        boolean nonSorted = true;
        while( nonSorted ) {
            nonSorted = false;
            for(int i = n-1; i > current; i--) {
                final int i1= i-1;
                if( maxsorted[i].getMax() < maxsorted[i1].getMax() ) {
                    final RankDomain t = maxsorted[i];
                    maxsorted[i] = maxsorted[i1];
                    maxsorted[i1] = t;
                    nonSorted = true;
                }
            }
            current++;
        }
    }

    void sort() {
        sortMin();
        sortMax();
        int min = minsorted[0].getMin();
        int max = maxsorted[0].getMax() + 1;
        int last = min-2;
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
        bounds[nb+1] = bounds[nb] + 2;
    }

    final void pathset(int[] t, int start, int end, int to) {
        int k, l=start;
        while((k=l) != end) {
            l = t[k];
            t[k] = to;
        }
    }
    
    final int pathmin(int[] t, int i) {
        while (t[i] < i) {
            i = t[i];
        }
        return i;
    }
    
    final int pathmax(int[]t, int i) {
        while (t[i] > i) {
            i=t[i];
        }
        return i;
    }

    //------------------------------------------------------------------------
    // INNERCLASSES
    //------------------------------------------------------------------------
    static class RankDomain extends MinMaxDomain {
        int minrank;
        int maxrank;

        RankDomain(IntegerDomain dom) {
            super(dom);
        }
    }
}
/*
 * $Log$
 * Revision 1.25  2005/07/18 15:32:55  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.24  2005/03/30 10:23:34  yan
 * changed private into package for some methods
 *
 * Revision 1.23  2005/02/02 14:09:40  yan
 * fixed style
 *
 * Revision 1.22  2004/11/10 10:19:27  yan
 * various optims
 *
 * Revision 1.21  2004/11/09 17:07:15  yan
 * throwing static exceptions
 *
 * Revision 1.20  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.19  2004/08/25 14:14:56  yan
 * fixed style
 *
 * Revision 1.18  2004/06/27 15:15:54  yan
 * small optims
 *
 * Revision 1.17  2004/05/05 11:46:48  yan
 * fixed style
 *
 * Revision 1.16  2004/04/09 14:42:50  yan
 * removal of events
 *
 * Revision 1.15  2003/10/02 23:49:00  yan
 * introduced complexity computation
 *
 * Revision 1.14  2003/08/15 10:08:31  yan
 * fixed javadoc
 *
 * Revision 1.13  2003/08/04 17:30:19  yan
 * new algo
 *
 */
