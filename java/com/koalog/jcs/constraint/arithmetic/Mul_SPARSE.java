package com.koalog.jcs.constraint.arithmetic;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.BinaryConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.constraint.bool.False;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.domain.SparseDomain;
import com.koalog.jcs.domain.IntegerDomain;

/**
 * This constraint enforces x=c*y.
 *
 * @since 3.1
 * @author Yan Georget
 */
public class Mul_SPARSE extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param x an integer variable
     * @param c an integer
     * @param y an integer variable
     */
    public Mul_SPARSE(IntegerVariable x, int c, IntegerVariable y) {
        super("x=" + c + "*y");
        if (c == 0) {
            add(new False(x));
        } else if (c == 1) {
            add(new Eq_SPARSE(x, y));
        } else { 
            add(new Mul_AUX(x, c, y));
        }
        setVariables(new IntegerVariable[] {x, y});
    }

    //------------------------------------------------------------------------
    // INNER CLASSES
    //------------------------------------------------------------------------
    static class Mul_AUX extends BinaryConstraint {
        /** An integer variable. */
        IntegerVariable x;
        /** An integer variable. */
        IntegerVariable y;
        /** The constant as an int. */
        int c;

        /**
         * Sole constructor.
         * @param x an integer variable
         * @param c an integer
         * @param y an integer variable
         */
        public Mul_AUX(IntegerVariable x, int c, IntegerVariable y) {
            super(x, y);
            this.name = "x=" + c + "*y";
            this.c = c;
            this.x = x;
            this.y = y;
            idempotent = true;
            complexity = Math.max(((IntegerDomain) x.getDomain()).size(),
                                  ((IntegerDomain) y.getDomain()).size());
        }
    
        /** @see com.koalog.jcs.constraint.Constraint */
        public void updateDependencies() {
            x.addDomConstraint(this);
            y.addDomConstraint(this);
        }
            
        /** @see com.koalog.jcs.constraint.Constraint */
        public void filter(ChoicePointStack cp,
                           ConstraintScheduler cs) 
            throws ConstraintInconsistencyException {
            final SortedSet xvalues = new TreeSet();
            for (Iterator i = ((SparseDomain) y.getDomain()).iterator();
                 i.hasNext();) {
                xvalues.add(new Integer(c*((Integer) i.next()).intValue()));
            }
            x.adjustDomain(cp, cs, this, xvalues);
            final SortedSet yvalues = new TreeSet();
            for (Iterator i = ((SparseDomain) x.getDomain()).iterator();
                 i.hasNext();) {
                final int val = ((Integer) i.next()).intValue();
                if (val % c == 0) {
                    yvalues.add(new Integer(val/c));
                }
            }
            y.adjustDomain(cp, cs, this, yvalues);
        }
    }
}
