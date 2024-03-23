package com.koalog.jcs.examples;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.BinaryConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.constraint.arithmetic.Neq;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;

/**
 * This is the famous queens problem.
 * It consists in placing n queens on a n*n chessboard 
 * such that they don't threaten each other.
 *
 * @author Yan Georget
 */
public class QueensProblem_ADAPTIVE extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param n the size of the problem
     */
    public QueensProblem_ADAPTIVE(int n) {
        super();
        IntegerVariable q[] = new IntegerVariable[n];
        for (int i=0; i<n; i++) {
            q[i] = new IntegerVariable("q"+i, 0, n-1);
        }
        for (int i=0; i<n-1; i++) {
            for (int j=i+1; j<n; j++) {
                add(new Neq(q[i], q[j]));
                add(new Neq_SHIFT(q[i], q[j], j-i));
            }
        }
        setVariables(q);
    }

    //------------------------------------------------------------------------
    // INNER CLASSES
    //------------------------------------------------------------------------
    /**
     * A fake x<>y+-d constraint.
     */
    static class Neq_SHIFT extends BinaryConstraint {
        int shift;

        /**
         * Sole constructor.
         * @param x an integer variable
         * @param y an integer variable
         * @param shift an int
         */
        Neq_SHIFT(IntegerVariable x, IntegerVariable y, int shift) {
            super(x, y);
            this.shift = shift;
        }
        
        /**
         * Returns the shift.
         * @return an int
         */
        public int getShift() {
            return shift;
        }

        /** @see com.koalog.jcs.constraint.Constraint */
        public void filter(ChoicePointStack cp,
                           ConstraintScheduler cs) 
            throws ConstraintInconsistencyException {
            // not implemented
        }

        /** @see com.koalog.jcs.constraint.Constraint */
        public void updateDependencies() {
        }
    }
}
