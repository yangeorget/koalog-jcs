package com.koalog.jcs.variable;

import com.koalog.jcs.VariableInconsistencyException;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.constraint.Constraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.domain.IntegerDomain;

/**
 * This class represents boolean variables.
 * 
 * <P>A boolean variable is an integer variable 
 * with a domain equal to [0,1].</P>
 *
 * @see IntegerVariable
 * @author Yan Georget
 */
public class BooleanVariable extends IntegerVariable {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------ 
    /**
     * Constructs a boolean variable.
     */
    public BooleanVariable() {
        this(DEFAULT_NAME);
    }

    /**
     * Constructs a boolean variable with a given value (true or false).
     * @param value the value
     */
    protected BooleanVariable(int value) {
        this(DEFAULT_NAME, value);
    }

    /**
     * Constructs a boolean variable with a given name.
     * @param name the name of the boolean variable
     */
    public BooleanVariable(String name) {
        super(name, 0, 1);
    }

    /**
     * Constructs a boolean variable with a given name and a given value.
     * @param name the name of the boolean variable
     * @param value the value
     */
    protected BooleanVariable(String name, int value) {
        super(name, value);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Return true iff this boolean variable is true.
     * @return a boolean
     */
    public final boolean isTrue() {
        return getMin() == 1;
    }

    /**
     * Return false iff this boolean variable is false.
     * @return a boolean
     */
    public final boolean isFalse() {
        return getMax() == 0;
    }

    /**
     * Adjusts the max of the domain of the variable.
     * @param cp the choice point stack
     * @param cs the constraint scheduler
     * @param c the constraint responsible for this variable adjustment     
     * @throws VariableInconsistencyException if the domain becomes empty
     */
    public void adjustFalse(ChoicePointStack cp, 
                            ConstraintScheduler cs,
                            Constraint c) 
        throws VariableInconsistencyException {
        if (isTrue()) {
            fail(c);
        }
        if (isNotInstantiated()) {
            cp.memorize(this);
            ((IntegerDomain) getDomain()).setMax(0);
            updateEventMask(EVENT_MAX);
            cs.update(cp, this);
        }
    }

    /**
     * Adjusts the min of the domain of the variable.
     * @param cp the choice point stack
     * @param cs the constraint scheduler
     * @param c the constraint responsible for this variable adjustment     
     * @throws VariableInconsistencyException if the domain becomes empty
     */
    public void adjustTrue(ChoicePointStack cp, 
                           ConstraintScheduler cs,
                           Constraint c) 
        throws VariableInconsistencyException {
        if (isFalse()) {
            fail(c);
        }
        if (isNotInstantiated()) {
            cp.memorize(this);
            ((IntegerDomain) getDomain()).setMin(1);
            updateEventMask(EVENT_MIN);
            cs.update(cp, this);
        }
    }

    /** @see com.koalog.jcs.variable.IntegerVariable */
    public final boolean isMinMax() {
        return true;
    }

    /** @see com.koalog.jcs.variable.IntegerVariable */
    public final boolean isSparse() {
        return false;
    }

    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    /**
     * Return true iff all the boolean variables are true.
     * @param variables an array of boolean variables
     * @return a boolean
     */
    public static final boolean allTrue(BooleanVariable[] variables) {
        for (int i=variables.length; --i>=0;) {
            if (!variables[i].isTrue()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return true iff all the boolean variables are false.
     * @param variables an array of boolean variables
     * @return a boolean
     */
    public static final boolean allFalse(BooleanVariable[] variables) {
        for (int i=variables.length; --i>=0;) {
            if (!variables[i].isFalse()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return false iff at least one of the boolean variables is true.
     * @param variables an array of boolean variables
     * @return a boolean
     */
    public static final boolean oneTrue(BooleanVariable[] variables) {
        for (int i=variables.length; --i>=0;) {
            if (variables[i].isTrue()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return false iff at least one of the boolean variables is false.
     * @param variables an array of boolean variables
     * @return a boolean
     */
    public static final boolean oneFalse(BooleanVariable[] variables) {
        for (int i=variables.length; --i>=0;) {
            if (variables[i].isFalse()) {
                return true;
            }
        }
        return false;
    }
}
