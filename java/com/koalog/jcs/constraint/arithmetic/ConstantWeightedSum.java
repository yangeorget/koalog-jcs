package com.koalog.jcs.constraint.arithmetic;

import java.util.List;
import java.util.LinkedList;

import com.koalog.jcs.constraint.InvalidConstraintException;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.variable.IntegerVariable;

/**
 * This constraint enforces the linear equation 
 * <CODE>c_1.x_1 + c_2.x_2 + ... = c</CODE>.
 *
 * @author Yan Georget
 */
public class ConstantWeightedSum extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param values an array of integers 
     * @param variables an array of integer variables
     * @param c an integer
     */
    public ConstantWeightedSum(int[] values, 
                               IntegerVariable[] variables, 
                               int c) {
        super("SIGMA_i c_i*x_i=c"); // do we want to pretty-print this problem?
        checkArguments(values, variables);
        List l = new LinkedList();
        for(int i=0; i<variables.length; i++) {
            if (values[i] != 0) {
                if (variables[i].isInstantiated()) {
                    c -= values[i] * ((IntegerVariable) variables[i]).getMin();
                } else {
                    if (values[i] == 1) {
                        l.add(variables[i]);
                    } else { 
                        IntegerVariable y = new IntegerVariable();
                        add(new Mul(y, values[i], variables[i]));
                        l.add(y);
                    }
                }
            }
        }
        if (l.size() == 1) {
            add(new Eq_1((IntegerVariable) l.get(0), c));
        } else {
            add(new ConstantSum(l, c));
        }
        setVariables(variables); 
        // or should we only take into account the variables in l?
        // TODO: in situations like 2x+3y=0 detect that y is even ...
        // TODO: detect cases where the eq reduces to c=d
    }
    
    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    void checkArguments(int[] values, IntegerVariable[] variables) {
        if (variables.length != values.length) {
            throw new InvalidConstraintException("#vars != #vals");
        }
        boolean allZero = true;
        for (int i=0; i<values.length; i++) {
            if (values[i] != 0) {
                allZero = false;
                break;
            }
        }
        if (allZero) {
            throw new InvalidConstraintException("vals are all null");
        }
        // we could also check that the same variable does not appear twice
    }
}
