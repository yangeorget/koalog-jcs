package com.koalog.jcs.constraint.arithmetic;

import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.InvalidConstraintException;

/**
 * An abstract constraint used as a superclass for Atleast, Atmost, Exactly.
 * 
 * @author Yan Georget
 */
public abstract class AbstractCheck extends AbstractCount {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The number of times the value is allowed. */
    protected int p;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param p a positive integer
     * @param variables an array of integer variables
     * @param c an integer
     */    
    public AbstractCheck(int p, IntegerVariable[] variables, int c) {
        super(c);
        checkArguments(p, variables);
        this.variables = variables;
        this.p = p;
        complexity = variables.length; 
        idempotent = true;
    }

    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    static void checkArguments(int p, IntegerVariable[] variables) {
        if (p <= 0) {
            throw new InvalidConstraintException("counter <= 0");
        }
        if (p >= variables.length) {
            throw new InvalidConstraintException("counter >= #variables");
        }
    }
}


