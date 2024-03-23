package com.koalog.jcs.constraint.arithmetic;

import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.BaseProblem;

/**
 * This relation enforces that its variables are increasing.
 *
 * @author Yan Georget
 */
public class Increasing extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param variables an array of integer variables
     */    
    public Increasing(IntegerVariable[] variables) {
        super("x_0<=...<=x_n-1");
        for(int i=1; i<variables.length; i++) {
            add(new Leq(variables[i-1],variables[i]));
        }
        setVariables(variables);
    }
}
