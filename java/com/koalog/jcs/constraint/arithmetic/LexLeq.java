package com.koalog.jcs.constraint.arithmetic;

import org.apache.log4j.Category;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.domain.IntegerDomain;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.constraint.BaseConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.constraint.InvalidConstraintException;
import com.koalog.jcs.variable.IntegerVariable;

/**
 * Enforces lexicographic ordering.
 *
 * <P>It uses the algorithm of Mats Carlsson and Nicolas Beldiceanu
 * as described in 
 * <CITE>Revisiting the lexicographic ordering constraint</CITE>.
 *
 * @author Yan Georget
 * @since 3.1
 */
public class LexLeq extends BaseConstraint {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(LexLeq.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    int n;
    int i;
    int q;
    int r;
    int s;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param x an array of integer variables
     * @param y an array of integer variables
     */
    public LexLeq(IntegerVariable[] x, IntegerVariable[] y) {
        super();
        checkArguments(x, y);
        n = x.length;
        variables = new IntegerVariable[2*n];
        System.arraycopy(x, 0, variables, 0, n);
        System.arraycopy(y, 0, variables, n, n);
        name = "lexleq"; // TODO: precise name
        complexity = n; 
        idempotent = !IntegerVariable.oneSparse(x) 
            && !IntegerVariable.oneSparse(y);
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
        i=q=r=s=0;
        // TODO: make incremental
        filterState1(cp, cs);
    }

    void filterState1(ChoicePointStack cp, 
                      ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        while (i<n) {
            final IntegerVariable x = (IntegerVariable) variables[i];
            final IntegerVariable y = (IntegerVariable) variables[i+n];
            final int xMin = x.getMin();
            final int yMax = y.getMax();
            if (xMin != yMax) {
                break;
            } else { // enforce xi = yi
                x.adjustMax(cp, cs, this, yMax);
                y.adjustMin(cp, cs, this, xMin);
                q = ++i;
            }
        }
        if (i == n) {
            entailed(cp);
            return;
        }
        final IntegerVariable x = (IntegerVariable) variables[i];
        final IntegerVariable y = (IntegerVariable) variables[i+n];
        final IntegerDomain xDom = (IntegerDomain) x.getDomain();
        final IntegerDomain yDom = (IntegerDomain) y.getDomain();
        if (xDom.getMax() < yDom.getMin()) {
            entailed(cp);
            return;
        }
        // enforce xq <= yq
        x.adjustMax(cp, cs, this, yDom.getMax());
        y.adjustMin(cp, cs, this, xDom.getMin());
        if (r > i+1) {
            i = r;
        } else {
            r = ++i;
        }
        filterState2(cp, cs);
    }

    void filterState2(ChoicePointStack cp, 
                      ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        while (i<n) {
            final IntegerDomain xDom = 
                (IntegerDomain) variables[i].getDomain();
            final IntegerDomain yDom = 
                (IntegerDomain) variables[i+n].getDomain();
            if (xDom.isNotSingleton() 
                || yDom.isNotSingleton()
                || xDom.getMin() != yDom.getMin()) {
                break;
            } else {
                r = ++i;
            }
        }
        if (i == n) {
            // xq<=yq
            final IntegerVariable xq = (IntegerVariable) variables[q];
            final IntegerVariable yq = (IntegerVariable) variables[q+n];
            final IntegerDomain yqDom = (IntegerDomain) yq.getDomain();
            xq.adjustMax(cp, cs, this, yqDom.getMax());
            final IntegerDomain xqDom = (IntegerDomain) xq.getDomain();
            yq.adjustMin(cp, cs, this, xqDom.getMin());
            if (xqDom.getMax() <= yqDom.getMin()) {
                entailed(cp);
            }
            return;
        }
        final IntegerDomain xDom = (IntegerDomain) variables[i].getDomain();
        final IntegerDomain yDom = (IntegerDomain) variables[i+n].getDomain();
        final int xMax = xDom.getMax();
        final int yMin = yDom.getMin();
        if (xMax < yMin) {
            // xq<=yq
            final IntegerVariable xq = (IntegerVariable) variables[q];
            final IntegerVariable yq = (IntegerVariable) variables[q+n];
            final IntegerDomain yqDom = (IntegerDomain) yq.getDomain();
            xq.adjustMax(cp, cs, this, yqDom.getMax());
            final IntegerDomain xqDom = (IntegerDomain) xq.getDomain();
            yq.adjustMin(cp, cs, this, xqDom.getMin());
            if (xqDom.getMax() <= yqDom.getMin()) {
                entailed(cp);
            }
            return;
        } 
        final int xMin = xDom.getMin();
        final int yMax = yDom.getMax();
        if (xMin > yMax) {
            // xq<yq
            final IntegerVariable xq = (IntegerVariable) variables[q];
            final IntegerVariable yq = (IntegerVariable) variables[q+n];
            final IntegerDomain yqDom = (IntegerDomain) yq.getDomain();
            xq.adjustMax(cp, cs, this, yqDom.getMax()-1);
            final IntegerDomain xqDom = (IntegerDomain) xq.getDomain();
            yq.adjustMin(cp, cs, this, xqDom.getMin()+1);
            if (xqDom.getMax() < yqDom.getMin()) {
                entailed(cp);
            }
            return;
        } 
        if (xMax == yMin && xMin < yMax) {
            if (s > i+1) {
                i = s;
            } else {
                s = ++i;
            }
            filterState3(cp, cs);
            return;
        }
        if (xMin == yMax && xMax > yMin) {
            if (s > i+1) {
                i = s;
            } else {
                s = ++i;
            }
            filterState4(cp, cs);
            return;
        }
        // u = 2;
    }

    void filterState3(ChoicePointStack cp, 
                      ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        while (i<n 
               && ((IntegerVariable) variables[i]).getMax() 
               == ((IntegerVariable) variables[i+n]).getMin()) {
            s = ++i;
        }
        if (i == n 
            || ((IntegerVariable) variables[i]).getMax() 
               < ((IntegerVariable) variables[i+n]).getMin()) {
            // xq<=yq
            final IntegerVariable xq = (IntegerVariable) variables[q];
            final IntegerVariable yq = (IntegerVariable) variables[q+n];
            final IntegerDomain yqDom = (IntegerDomain) yq.getDomain();
            xq.adjustMax(cp, cs, this, yqDom.getMax());
            final IntegerDomain xqDom = (IntegerDomain) xq.getDomain();
            yq.adjustMin(cp, cs, this, xqDom.getMin());
            if (xqDom.getMax() <= yqDom.getMin()) {
                entailed(cp);
            }
            return;
        } 
        // u = 3;
    }

    void filterState4(ChoicePointStack cp, 
                      ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        while (i<n 
               && ((IntegerVariable) variables[i]).getMin() 
               == ((IntegerVariable) variables[i+n]).getMax()) {
            s = ++i;
        }
        if (i < n 
            && ((IntegerVariable) variables[i]).getMin() 
               > ((IntegerVariable) variables[i+n]).getMax()) {
            // xq<yq
            final IntegerVariable xq = (IntegerVariable) variables[q];
            final IntegerVariable yq = (IntegerVariable) variables[q+n];
            final IntegerDomain yqDom = (IntegerDomain) yq.getDomain();
            xq.adjustMax(cp, cs, this, yqDom.getMax()-1);
            final IntegerDomain xqDom = (IntegerDomain) xq.getDomain();
            yq.adjustMin(cp, cs, this, xqDom.getMin()+1);
            if (xqDom.getMax() < yqDom.getMin()) {
                entailed(cp);
            }
            return;
        } 
        // u = 4;
    }
    
    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    static void checkArguments(IntegerVariable[] x, IntegerVariable[] y) {
        if (x.length != y.length) {
            throw new InvalidConstraintException("#x != #y");
        }
    }   
}
