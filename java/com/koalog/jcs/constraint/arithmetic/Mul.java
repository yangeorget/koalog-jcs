package com.koalog.jcs.constraint.arithmetic;

import com.koalog.util.Arithmetic;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.BinaryConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.constraint.bool.False;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.domain.IntegerDomain;

/**
 * This constraint enforces x=c*y.
 *
 * @author Yan Georget
 */
public class Mul extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param x an integer variable
     * @param c an integer
     * @param y an integer variable
     */
    public Mul(IntegerVariable x, int c, IntegerVariable y) {
        super("x=" + c + "*y");
        if (c == 0) {
            add(new False(x));
        } else if (c == 1) {
            add(new Eq(x, y));
        } else if (c == -1) {
            add(new Opp(x, y));
        } else if (c > 0) { // c > 1
            add(new Mul_POS(x, c, y));
        } else { // c < -1
            add(new Mul_NEG(x, c, y));
        }
        setVariables(new IntegerVariable[] {x, y});
    }

    //------------------------------------------------------------------------
    // INNER CLASSES
    //------------------------------------------------------------------------
    abstract static class Mul_AUX extends BinaryConstraint {
        /** An integer variable. */
        protected IntegerVariable x;
        /** An integer variable. */
        protected IntegerVariable y;
        /** The constant as an int. */
        protected int c;

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
            idempotent = x.isMinMax() && y.isMinMax();
            complexity = 1;
        }
    
        /** @see com.koalog.jcs.constraint.Constraint */
        public void updateDependencies() {
            x.addMinMaxConstraint(this);
            y.addMinMaxConstraint(this);
        }
    }

    static class Mul_POS extends Mul_AUX {
        /**
         * Sole constructor.
         * @param x an integer variable
         * @param c an integer strictly greater than 1
         * @param y an integer variable
         */
        public Mul_POS(IntegerVariable x, int c, IntegerVariable y) {
            super(x, c, y);
        }
            
        /** @see com.koalog.jcs.constraint.Constraint */
        public void filter(ChoicePointStack cp,
                           ConstraintScheduler cs) 
            throws ConstraintInconsistencyException {
            final IntegerDomain xDom = (IntegerDomain) x.getDomain();
            y.adjustMinMax(cp, 
                           cs, 
                           this,
                           Arithmetic.ceilDivide_SG1(xDom.getMin(), c),
                           Arithmetic.floorDivide_SG1(xDom.getMax(), c));
            final IntegerDomain yDom = (IntegerDomain) y.getDomain();
            x.adjustMinMax(cp, 
                           cs,
                           this,
                           yDom.getMin() * c,
                           yDom.getMax() * c);
        }
    }

    static class Mul_NEG extends Mul_AUX {
        /**
         * Sole constructor.
         * @param x an integer variable
         * @param c an integer strictly smaller than 0
         * @param y an integer variable
         */
        public Mul_NEG(IntegerVariable x, int c, IntegerVariable y) {
            super(x, c, y);
        }

        /** @see com.koalog.jcs.constraint.Constraint */
        public void filter(ChoicePointStack cp,
                           ConstraintScheduler cs) 
            throws ConstraintInconsistencyException {
            final IntegerDomain xDom = (IntegerDomain) x.getDomain();
            y.adjustMinMax(cp, 
                           cs,
                           this, 
                           Arithmetic.ceilDivide_SS1(xDom.getMax(), c),
                           Arithmetic.floorDivide_SS1(xDom.getMin(), c));
            final IntegerDomain yDom = (IntegerDomain) y.getDomain();
            x.adjustMinMax(cp, 
                           cs,
                           this,
                           yDom.getMax() * c,
                           yDom.getMin() * c);
        }
    }
}
