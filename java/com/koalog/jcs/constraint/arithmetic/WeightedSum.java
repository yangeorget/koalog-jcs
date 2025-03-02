package com.koalog.jcs.constraint.arithmetic;

import java.util.Collection;
import java.util.LinkedList;

import com.koalog.jcs.constraint.InvalidConstraintException;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.bool.False;
import com.koalog.jcs.variable.IntegerVariable;

/**
 * This constraint enforces the linear equation 
 * <CODE>s=c_1.x_1 + c_2.x_2 + ...</CODE>.
 *
 * @author Yan Georget
 */
public class WeightedSum extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param s an integer variable
     * @param c an array of integers 
     * @param x an array of integer variables
     */
    public WeightedSum(IntegerVariable s, int[] c, IntegerVariable[] x) {
        super("s=SIGMA_i c_i*x_i"); // do we want to pretty-print this problem?
        checkArguments(c, x);
        Collection l = new LinkedList();
        int cs = 0;
        for(int i=0; i<x.length; i++) {
            if (c[i] != 0) {
                if (x[i].isInstantiated()) {
                    cs += c[i] * ((IntegerVariable) x[i]).getMin();
                } else {
                    if (c[i] == 1) {
                        l.add(x[i]);
                    } else { // c[i] different from 0 and 1
                        IntegerVariable y = new IntegerVariable();
                        add(new Mul(y, c[i], x[i]));
                        l.add(y);
                    }
                }
            }
        }
        if (l.size() != 0) {
            if (cs == 0) {
                add(new Sum(s, l));
            } else {
                IntegerVariable sAux = new IntegerVariable();
                add(new Sum(sAux, l));
                add(new Shift(s, sAux, cs));
            }
        } else {
            if (cs == 0) {
                add(new False(s));
            } else {
                add(new Eq_1(s, cs));
            }
        }
        variables = new IntegerVariable[1+x.length];
        variables[0] = s;
        System.arraycopy(x, 0, variables, 1, x.length);
        // or should we only take into account the variables in l?
    }
    
    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    void checkArguments(int[] c, IntegerVariable[] x) {
        if (c.length != x.length) {
            throw new InvalidConstraintException("#vars != #vals");
        }
        // we could also check that the same variable does not appear twice
    }
}
