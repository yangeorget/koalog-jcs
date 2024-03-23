package com.koalog.jcs.variable;

import java.util.SortedSet;
import java.util.Iterator;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.VariableInconsistencyException;
import com.koalog.jcs.domain.IntegerDomain;
import com.koalog.jcs.domain.SparseDomain;
import com.koalog.jcs.domain.MinMaxDomain;
import com.koalog.jcs.domain.DefaultMinMaxDomain;
import com.koalog.jcs.constraint.Constraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.solver.BacktrackSolver;

import org.apache.log4j.Category;

/**
 * This class represents integer variables. 
 * An integer variable has an <CODE>IntegerDomain</CODE> as a domain. 
 * 
 * <P>An integer variable is typically created as follows:
 * <PRE>
 * IntegerVariable var = new IntegerVariable(min, max);
 * </PRE>
 * If some constraints require a sparse domain, 
 * then the variable should be created by:
 * <PRE>
 * IntegerVariable var = new IntegerVariable(new SparseDomain(min, max));
 * </PRE>
 * </P>
 * 
 * <P>Note that the type of the domain, 
 * <CODE>MinMaxDomain</CODE> or <CODE>SparseDomain</CODE>
 * (both are subclasses of <CODE>IntegerDomain</CODE>),
 * is not modified during the computation.
 * This choice was made for efficiency reasons.</P>
 *
 * @see com.koalog.jcs.domain.IntegerDomain
 * @see com.koalog.jcs.domain.MinMaxDomain
 * @see com.koalog.jcs.domain.SparseDomain
 * @see com.koalog.jcs.constraint.Constraint
 * @author Yan Georget
 */
public class IntegerVariable extends MinMaxVariable {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(IntegerVariable.class);
 
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------ 
    /**
     * Constructs an integer variable with a default domain 
     */
    public IntegerVariable() {
        this(DEFAULT_NAME);
    }

    /**
     * Constructs an integer variable with a domain equals to [value, value].
     * @param value the value of the variable
     */
    public IntegerVariable(int value) {
        this(DEFAULT_NAME, value);
    }

    /**
     * Constructs an integer variable with a domain equals to [min,max].
     * @param min the min of the domain
     * @param max the max of the domain
     */
    public IntegerVariable(int min, int max) {
        this(DEFAULT_NAME, min, max);
    }
 
    /**
     * Constructs an integer variable with a given domain.
     * @param domain the domain of the integer variable
     */
    public IntegerVariable(IntegerDomain domain) {
        this(DEFAULT_NAME, domain);
    }

    /**
     * Constructs an integer variable with a given name and a default domain 
     * @param name the name of the integer variable
     */
    public IntegerVariable(String name) {
        this(name, new DefaultMinMaxDomain());
    }

    /**
     * Constructs an integer variable 
     * with a given name and a domain equals to [value, value].
     * @param name the name of the integer variable
     * @param value the value of the variable
     */
    public IntegerVariable(String name, int value) {
        this(name, new MinMaxDomain(value));
    }
    /**
     * Constructs an integer variable 
     * with a given name and a domain equals to [min,max].
     * @param name the name of the integer variable
     * @param min the min of the domain
     * @param max the max of the domain
     */
    public IntegerVariable(String name, int min, int max) {
        this(name, new MinMaxDomain(min, max));
    }

    /**
     * Constructs an integer variable with a given name and domain.
     * @param name the name of the integer variable
     * @param domain the domain of the integer variable
     */
    public IntegerVariable(String name, IntegerDomain domain) {
        super(name, domain);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.variable.MinMaxVariable */
    public void shaveMin(BacktrackSolver solver, boolean deep) 
        throws ConstraintInconsistencyException {
        final ChoicePointStack cp = solver.getChoicePoints();
        final ConstraintScheduler cs = solver.getConstraintScheduler();
        cp.push();
        final int min = getMin();
        adjustMaxToMin(cp, cs, null);
        try {
            solver.consistencyFilter();
            cp.pop(cs);
            setFullEventMask();
            try {
                solver.consistencyFilter();
            } catch (ConstraintInconsistencyException ie) {
                // should not happen
                throw new RuntimeException();
            } 
        } catch(ConstraintInconsistencyException ie) {
            cs.clear();
            cp.pop(cs);
            adjustMin(cp, cs, null, min+1);
            solver.consistencyFilter();
            if (deep) {
                shaveMin(solver, deep);
            }
        }
    }

    /** @see com.koalog.jcs.variable.MinMaxVariable */
    public void shaveMax(BacktrackSolver solver, boolean deep) 
        throws ConstraintInconsistencyException {
        final ChoicePointStack cp = solver.getChoicePoints();
        final ConstraintScheduler cs = solver.getConstraintScheduler();
        cp.push();
        final int max = getMax();
        adjustMinToMax(cp, cs, null);
        try {
            solver.consistencyFilter();
            cp.pop(cs);
            setFullEventMask();
            try {
                solver.consistencyFilter();
            } catch (ConstraintInconsistencyException ie) {
                // should not happen
                throw new RuntimeException();
            }
        } catch(ConstraintInconsistencyException ie) {
            cs.clear();
            cp.pop(cs);
            adjustMax(cp, cs, null, max-1);
            solver.consistencyFilter();
            if (deep) {
                shaveMax(solver, deep);
            }
        }
    }

    /**
     * Shaves the values of the variable.
     * @param solver a solver for enforcing consistency
     * @throws ConstraintInconsistencyException when there is no solution
     * @since 3.1 
     */
    public void shaveValues(BacktrackSolver solver) 
        throws ConstraintInconsistencyException {
        final ChoicePointStack cp = solver.getChoicePoints();
        final ConstraintScheduler cs = solver.getConstraintScheduler();
        for (Iterator i = ((IntegerDomain) getDomain()).iterator(); 
             i.hasNext();) {
            cp.push();
            final Integer v = (Integer) i.next();
            final int iv = v.intValue();
            adjustValue(cp, cs, null, iv);
            try {
                solver.consistencyFilter();
                cp.pop(cs);
                setFullEventMask();
                try {
                    solver.consistencyFilter();
                } catch (ConstraintInconsistencyException ie) {
                    // should not happen
                    throw new RuntimeException();
                } 
            } catch(ConstraintInconsistencyException ie) {
                cs.clear();
                cp.pop(cs);
                adjustDifferent(cp, cs, null, v, i);
                solver.consistencyFilter();
            }
        }
    }

    /**
     * Adjusts the domain of the variable to be included in a given set.
     * @param cp the choice point stack
     * @param cs the constraint scheduler
     * @param c the constraint responsible for this variable adjustment     
     * @param values a fresh sorted set
     * @throws VariableInconsistencyException if the domain becomes empty
     */
    public void adjustDomain(ChoicePointStack cp, 
                             ConstraintScheduler cs,
                             Constraint c,
                             SortedSet values) 
        throws VariableInconsistencyException {
        final SparseDomain sparseDomain = (SparseDomain) getDomain();
        values.retainAll(sparseDomain.getValues());
        final int size = values.size();
        if (size == 0) {
            fail(c);
        } else if (sparseDomain.size() != size) {
            cp.memorize(this);
            final int oldMin = sparseDomain.getMin();
            final int oldMax = sparseDomain.getMax();        
            sparseDomain.setValues(values);
            if (oldMin != sparseDomain.getMin()) {
                if (oldMax != sparseDomain.getMax()) {
                    updateEventMask(EVENT_MINMAX);
                } else {
                    updateEventMask(EVENT_MIN);
                }
            } else if (oldMax != sparseDomain.getMax()) {
                updateEventMask(EVENT_MAX);
            } else {
                updateEventMask(EVENT_DOM);
            }
            cs.update(cp, this);
        }
    }
    
    /**
     * Excludes a value from the domain of the variable 
     * (the variable must then have a sparse domain).
     * @param cp the choice point stack
     * @param cs the constraint scheduler
     * @param c the constraint responsible for this variable adjustment     
     * @param value the value that should be excluded 
     * from the domain of the variable
     * @throws VariableInconsistencyException if the domain becomes empty
     */
    public void adjustDifferent(ChoicePointStack cp, 
                                ConstraintScheduler cs,
                                Constraint c,
                                int value) 
        throws VariableInconsistencyException {
        final SparseDomain sparseDomain = (SparseDomain) getDomain();
        if (sparseDomain.contains(value)) {
            if (sparseDomain.isSingleton()) {
                fail(c);
            } 
            cp.memorize(this);
            if (value == sparseDomain.getMin()) {
                updateEventMask(EVENT_MIN);
            } else if (value == sparseDomain.getMax()) {
                updateEventMask(EVENT_MAX);
            } else {
                updateEventMask(EVENT_DOM);
            }
            sparseDomain.remove(value);
            cs.update(cp, this);

        }
    }

    /**
     * Excludes a value from the domain of the variable 
     * (the variable must then have a sparse domain).
     * @param cp the choice point stack
     * @param cs the constraint scheduler
     * @param c the constraint responsible for this variable adjustment     
     * @param value the value that should be excluded 
     * from the domain of the variable
     * @param iterator an integer domain iterator 
     * pointing to the value to remove
     * @throws VariableInconsistencyException if the domain becomes empty
     * @since 2.2
     */
    public void adjustDifferent(ChoicePointStack cp, 
                                ConstraintScheduler cs,
                                Constraint c,
                                Integer value,
                                Iterator iterator) 
        throws VariableInconsistencyException {
        final SparseDomain sparseDomain = (SparseDomain) getDomain();
        // the test sparseDomain.contains(value) is useless here
        if (sparseDomain.isSingleton()) {
            fail(c);
        } 
        cp.memorize(this);
        final int val = value.intValue();
        if (val == sparseDomain.getMin()) {
            updateEventMask(EVENT_MIN);
        } else if (val == sparseDomain.getMax()) {
            updateEventMask(EVENT_MAX);
        } else {
            updateEventMask(EVENT_DOM);
        }
        iterator.remove();
        cs.update(cp, this);
    }

    /**
     * Adjusts the domain of the variable.
     * @param cp the choice point stack
     * @param cs the constraint scheduler
     * @param c the constraint responsible for this variable adjustment     
     * @param value the new value of the variable
     * @throws VariableInconsistencyException if the domain becomes empty
     */
    public void adjustValue(ChoicePointStack cp, 
                            ConstraintScheduler cs,
                            Constraint c,
                            int value) throws VariableInconsistencyException {
        final IntegerDomain integerDomain = (IntegerDomain) getDomain();
        if (integerDomain.contains(value)) {
            if (integerDomain.isNotSingleton()) {
                cp.memorize(this);
                if (value == integerDomain.getMin()) {
                    integerDomain.setMax(value);
                    updateEventMask(EVENT_MAX);
                } else if (value == integerDomain.getMax()) {
                    integerDomain.setMin(value);
                    updateEventMask(EVENT_MIN);
                } else {
                    integerDomain.setValue(value);
                    updateEventMask(EVENT_MINMAX);
                }
                cs.update(cp, this);
            }
        } else {
            fail(c);
        }
    }

    /**
     * Adjusts the max of the domain of the variable.
     * @param cp the choice point stack
     * @param cs the constraint scheduler
     * @param c the constraint responsible for this variable adjustment     
     * @param max the new max of the domain of the variable
     * @throws VariableInconsistencyException if the domain becomes empty
     */
    public void adjustMax(ChoicePointStack cp, 
                          ConstraintScheduler cs,
                          Constraint c,
                          int max) throws VariableInconsistencyException {
        final IntegerDomain integerDomain = (IntegerDomain) getDomain();
        if (max < integerDomain.getMin()) {
            fail(c);
        }
        if (max < integerDomain.getMax()) {
            cp.memorize(this);
            integerDomain.setMax(max);
            updateEventMask(EVENT_MAX);
            cs.update(cp, this);
        }
    }

    /**
     * Adjusts the max of the domain of the variable to its min.
     * @param cp the choice point stack
     * @param cs the constraint scheduler
     * @param c the constraint responsible for this variable adjustment     
     * @since 3.0
     */
    public void adjustMaxToMin(ChoicePointStack cp, 
                               ConstraintScheduler cs,
                               Constraint c) {
        final IntegerDomain integerDomain = (IntegerDomain) getDomain();
        if (integerDomain.isNotSingleton()) {
            cp.memorize(this);
            integerDomain.setMaxToMin();
            updateEventMask(EVENT_MAX);
            cs.update(cp, this);
        }
    }

    /**
     * Adjusts the min of the domain of the variable.
     * @param cp the choice point stack
     * @param cs the constraint scheduler
     * @param c the constraint responsible for this variable adjustment     
     * @param min the new min of the domain of the variable
     * @throws VariableInconsistencyException if the domain becomes empty
     */
    public void adjustMin(ChoicePointStack cp, 
                          ConstraintScheduler cs,
                          Constraint c,
                          int min) throws VariableInconsistencyException {
        final IntegerDomain integerDomain = (IntegerDomain) getDomain();
        if (min > integerDomain.getMax()) {
            fail(c);
        }
        if (min > integerDomain.getMin()) {
            cp.memorize(this);
            integerDomain.setMin(min);
            updateEventMask(EVENT_MIN);
            cs.update(cp, this);
        }
    }

    /**
     * Adjusts the min of the domain of the variable to its max.
     * @param cp the choice point stack
     * @param cs the constraint scheduler
     * @param c the constraint responsible for this variable adjustment     
     */
    public void adjustMinToMax(ChoicePointStack cp, 
                               ConstraintScheduler cs,
                               Constraint c) {
        final IntegerDomain integerDomain = (IntegerDomain) getDomain();
        if (integerDomain.isNotSingleton()) {
            cp.memorize(this);
            integerDomain.setMinToMax();
            updateEventMask(EVENT_MIN);
            cs.update(cp, this);
        }
    }

    /**
     * Adjusts the min and the max of the domain of the variable.
     * @param cp the choice point stack
     * @param cs the constraint scheduler
     * @param c the constraint responsible for this variable adjustment     
     * @param min the new min of the domain of the variable
     * @param max the new max of the domain of the variable
     * @throws VariableInconsistencyException if the domain becomes empty
     */
    public void adjustMinMax(ChoicePointStack cp, 
                             ConstraintScheduler cs,
                             Constraint c,
                             int min,
                             int max) throws VariableInconsistencyException {
        if (min == max) {
            adjustValue(cp, cs, c, min);
        } else if (max < min) {
            fail(c);
        } else { // min < max
            final IntegerDomain integerDomain = (IntegerDomain) getDomain();
            final int dMin = integerDomain.getMin();
            final int dMax = integerDomain.getMax();
            if (max < dMin || min > dMax) {
                fail(c);
            }
            if (min > dMin) {
                cp.memorize(this);
                if (max < dMax) {
                    integerDomain.setMinMax(min, max);
                    // the only case where we may empty the domain
                    if (integerDomain.isEmpty()) {
                        fail(c);
                    }
                    updateEventMask(EVENT_MINMAX);
                } else {
                    integerDomain.setMin(min);
                    updateEventMask(EVENT_MIN);
                }
                cs.update(cp, this);
            } else if (max < dMax) {
                cp.memorize(this);
                integerDomain.setMax(max);
                updateEventMask(EVENT_MAX);
                cs.update(cp, this);
            }
        }
    }
    /*
    public void adjustMinMax(ChoicePointStack cp, 
                             ConstraintScheduler cs,
                             Constraint c,
                             int min,
                             int max) throws VariableInconsistencyException {
        if (max < min) {
            fail(c);
        }
        final IntegerDomain integerDomain = (IntegerDomain) getDomain();
        final int dMin = integerDomain.getMin();
        final int dMax = integerDomain.getMax();
        if (max >= dMax) { // setting only the min
            if (min > dMin) {
                if (min > dMax) {
                    fail(c);
                }
                cp.memorize(this);
                integerDomain.setMin(min);
                updateEventMask(EVENT_MIN);
                cs.update(cp, this);
            }
        } else if (min <= dMin) { // setting only the max
            // we already now that max < dMax
            if (max < dMin) {
                fail(c);
            }
            cp.memorize(this);
            integerDomain.setMax(max);
            updateEventMask(EVENT_MAX);
            cs.update(cp, this);
        } else { // setting both
            cp.memorize(this);
            // the only case where we may empty the domain
            integerDomain.setMinMax(min, max);
            if (integerDomain.isEmpty()) {
                fail(c);
            }
            updateEventMask(EVENT_MINMAX);
            cs.update(cp, this);
        }
    }
    */

    /**
     * Chooses a value.
     * @param cp the choice point stack
     * @param cs the constraint scheduler
     * @param value the new value of the variable
     */
    public void chooseValue(ChoicePointStack cp, 
                            ConstraintScheduler cs, 
                            int value) {
        final SparseDomain sparseDomain = (SparseDomain) getDomain();
        if (value == sparseDomain.getMin()) {
            cp.memorize(this, sparseDomain.copyWithMin(value+1));
            sparseDomain.setValue(value);
            updateEventMask(EVENT_MAX);
        } else if (value == sparseDomain.getMax()) {
            cp.memorize(this, sparseDomain.copyWithMax(value-1));
            sparseDomain.setValue(value);
            updateEventMask(EVENT_MIN);
        } else {
            final SparseDomain d = (SparseDomain) sparseDomain.copy();
            d.remove(value);
            cp.memorize(this, d);
            sparseDomain.setValue(value);
            updateEventMask(EVENT_MINMAX);
        }  
        cs.update(cp, this);
    }
  
    /**
     * Chooses a new max.
     * @param cp the choice point stack
     * @param cs the constraint scheduler
     * @param max the new max of the domain of the variable
     */
    public void chooseMax(ChoicePointStack cp, 
                          ConstraintScheduler cs, 
                          int max) {
        final IntegerDomain integerDomain = (IntegerDomain) getDomain();
        cp.memorize(this, integerDomain.copyWithMin(max+1));
        integerDomain.setMax(max);
        updateEventMask(EVENT_MAX);
        cs.update(cp, this);
    }

    /**
     * Chooses a new min.
     * @param cp the choice point stack
     * @param cs the constraint scheduler
     * @param min the new min of the domain of the variable
     */
    public void chooseMin(ChoicePointStack cp, 
                          ConstraintScheduler cs, 
                          int min) {
        final IntegerDomain integerDomain = (IntegerDomain) getDomain();
        cp.memorize(this, integerDomain.copyWithMax(min-1));
        integerDomain.setMin(min);
        updateEventMask(EVENT_MIN);
        cs.update(cp, this);
    }

    /** 
     * Gets the min of the variable.
     * @return an int
     */
    public final int getMin() {
       return ((IntegerDomain) getDomain()).getMin();
    }
  
    /** 
     * Gets the max of the variable.
     * @return an int
     */
    public final int getMax() {
        return ((IntegerDomain) getDomain()).getMax();
    }

    /**
     * Returns true iff the domain of the variable is a MinMaxDomain.
     * @return a boolean
     */
    public boolean isMinMax() {
        return getDomain() instanceof MinMaxDomain;
    }

    /**
     * Returns true iff the domain of the variable is a SparseDomain.
     * @return a boolean
     */
    public boolean isSparse() {
        return getDomain() instanceof SparseDomain;
    }

    //------------------------------------------------------------------------
    // STATIC METHODS 
    //------------------------------------------------------------------------
    /**
     * Returns the greatest domain size for an array of variables.
     * @param variables an array of integer variables
     * @return an integer
     */
    public static int getMaxDomainSize(IntegerVariable[] variables) {
        int maxDomSize = 0;
        for (int i=0; i<variables.length; i++) {
            int domSize = ((IntegerDomain) variables[i].getDomain()).size();
            if (domSize > maxDomSize) {
                maxDomSize = domSize;
            }
        }
        return maxDomSize;
    }

    /**
     * Returns the greatest max for an array of variables.
     * @param variables an array of integer variables
     * @return an integer
     */
    public static int getMaxMax(IntegerVariable[] variables) {
        int maxMax = Integer.MIN_VALUE;
        for (int i=0; i<variables.length; i++) {
            int max = variables[i].getMax();
            if (max > maxMax) {
                maxMax = max;
            }
        }
        return maxMax;
    }

    /**
     * Returns the smallest min for an array of variables.
     * @param variables an array of integer variables
     * @return an integer
     */
    public static int getMinMin(IntegerVariable[] variables) {
        int minMin = Integer.MAX_VALUE;
        for (int i=0; i<variables.length; i++) {
            int min = variables[i].getMin();
            if (min < minMin) {
                minMin = min;
            }
        }
        return minMin;
    }

    /**
     * Returns true iff at least one of the variables has a sparse domain.
     * @param variables an array of integer variables
     * @return a boolean
     */
    public static boolean oneSparse(IntegerVariable[] variables) {
        for (int i=variables.length; --i>=0;) {
            if (variables[i].isSparse()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true iff all the variables have a sparse domain.
     * @param variables an array of integer variables
     * @return a boolean
     * @since 2.1
     */
    public static boolean allSparse(IntegerVariable[] variables) {
        for (int i=variables.length; --i>=0;) {
            if (!variables[i].isSparse()) {
                return false;
            }
        }
        return true;
    }
}
