package com.koalog.jcs.variable;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Collection;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.VariableInconsistencyException;
import com.koalog.jcs.domain.SetDomain;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.constraint.Constraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.solver.BacktrackSolver;

/**
 * This class represents set variables. 
 * A set variable has an <CODE>SetDomain</CODE> as a domain. 
 *
 * @see com.koalog.jcs.domain.SetDomain
 * @author Yan Georget
 */
public class SetVariable extends MinMaxVariable {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------ 
    /**
     * Constructs a set variable with a domain equals to the value given 
     * as a parameter.
     * @param value a value for the set domain (ie a collection)
     */
    public SetVariable(Collection value) {
        this(DEFAULT_NAME, value);
    }

    /**
     * Constructs an set variable with a given domain.
     * @param domain the domain of the set variable
     */
    public SetVariable(SetDomain domain) {
        this(DEFAULT_NAME, domain);
    }

    /**
     * Constructs an set variable 
     * with a given name and a domain equals to the value given as a parameter.
     * @param name the name of the integer variable
     * @param value a value for the set domain (ie a collection)
     */
    public SetVariable(String name, Collection value) {
        this(name, new SetDomain(value));
    }

    /**
     * Constructs an set variable with a given name and domain.
     * @param name the name of the set variable
     * @param domain the domain of the set variable
     */
    public SetVariable(String name, SetDomain domain) {
        super(name, domain);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------ 
    /** @see com.koalog.jcs.variable.MinMaxVariable */
    public void shaveMin(BacktrackSolver solver, boolean deep) 
        throws ConstraintInconsistencyException {
        throw new RuntimeException("not implemented");
    }

    /** @see com.koalog.jcs.variable.MinMaxVariable */
    public void shaveMax(BacktrackSolver solver, boolean deep) 
        throws ConstraintInconsistencyException {
        throw new RuntimeException("not implemented");
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
                             SortedSet min,
                             SortedSet max) 
        throws VariableInconsistencyException {
        adjustMin(cp, cs, c, min);
        adjustMax(cp, cs, c, max);
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
                          SortedSet max) 
        throws VariableInconsistencyException {
        if (!max.containsAll(getMax())) {
            cp.memorize(this);
            getMax().retainAll(max);
            if (getDomain().isEmpty()) {
                fail(c);
            }
            updateEventMask(EVENT_MAX);
            cs.update(cp, this);
        }
    }

    /**
     * Adjusts the max of the domain of the variable.
     * @param cp the choice point stack
     * @param cs the constraint scheduler
     * @param c the constraint responsible for this variable adjustment
     * @param exc the collection to exclude
     * @throws VariableInconsistencyException if the domain becomes empty
     */
    public void adjustMaxByExcluding(ChoicePointStack cp, 
                                     ConstraintScheduler cs,
                                     Constraint c,
                                     Collection exc) 
        throws VariableInconsistencyException {
        final SortedSet diff = new TreeSet(exc);
        diff.retainAll(getMax());
        if (!diff.isEmpty()) {
            cp.memorize(this);
            getMax().removeAll(diff);
            if (getDomain().isEmpty()) {
                fail(c);
            }
            updateEventMask(EVENT_MAX);
            cs.update(cp, this);
        }
    }

    /**
     * Adjusts the max of the domain of the variable.
     * @param cp the choice point stack
     * @param cs the constraint scheduler
     * @param c the constraint responsible for this variable adjustment
     * @param val the value to exclude
     * @throws VariableInconsistencyException if the domain becomes empty
     */
    public void adjustMaxByExcluding(ChoicePointStack cp, 
                                     ConstraintScheduler cs,
                                     Constraint c,
                                     Comparable val) 
        throws VariableInconsistencyException {
        if (getMax().contains(val)) {
            if (getMin().contains(val)) {
                fail(c);
            }
            cp.memorize(this);
            getMax().remove(val);
            updateEventMask(EVENT_MAX);
            cs.update(cp, this);
        }
    }

    /**
     * Adjusts the max of the domain of the variable to its min.
     * @param cp the choice point stack
     * @param cs the constraint scheduler
     * @param c the constraint responsible for this variable adjustment     
     */
    public void adjustMaxToMin(ChoicePointStack cp, 
                               ConstraintScheduler cs,
                               Constraint c) {
        if (isNotInstantiated()) {
            cp.memorize(this);
            setMax(new TreeSet(getMin()));
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
                          SortedSet min) 
        throws VariableInconsistencyException {
        if (!getMin().containsAll(min)) {
            cp.memorize(this);
            getMin().addAll(min);
            if (getDomain().isEmpty()) {
                fail(c);
            }
            updateEventMask(EVENT_MIN);
            cs.update(cp, this);
        }
    }

    /**
     * Adjusts the min of the domain of the variable.
     * @param cp the choice point stack
     * @param cs the constraint scheduler
     * @param c the constraint responsible for this variable adjustment     
     * @param inc the collection to include
     * @throws VariableInconsistencyException if the domain becomes empty
     */
    public void adjustMinByIncluding(ChoicePointStack cp, 
                                     ConstraintScheduler cs,
                                     Constraint c,
                                     Collection inc) 
        throws VariableInconsistencyException {
        final SortedSet diff = new TreeSet(inc);
        diff.removeAll(getMin());
        if (!diff.isEmpty()) {
            cp.memorize(this);
            getMin().addAll(diff);
            if (getDomain().isEmpty()) {
                fail(c);
            }
            updateEventMask(EVENT_MIN);
            cs.update(cp, this);
        }
    }

    /**
     * Adjusts the min of the domain of the variable.
     * @param cp the choice point stack
     * @param cs the constraint scheduler
     * @param c the constraint responsible for this variable adjustment     
     * @param val the value to include
     * @throws VariableInconsistencyException if the domain becomes empty
     */
    public void adjustMinByIncluding(ChoicePointStack cp, 
                                     ConstraintScheduler cs,
                                     Constraint c, 
                                     Comparable val) 
        throws VariableInconsistencyException {
        if (!getMin().contains(val)) {
            if (!getMax().contains(val)) {
                fail(c);
            }
            cp.memorize(this);
            getMin().add(val);
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
        if (isNotInstantiated()) {
            cp.memorize(this);
            setMin(new TreeSet(getMax()));
            updateEventMask(EVENT_MIN);
            cs.update(cp, this);
        }
    }

    /**
     * Chooses a new max for the variable.
     * @param cp the choice point stack
     * @param cs the constraint scheduler
     * @param val the value to remove
     */
    public void chooseMaxByRemoving(ChoicePointStack cp, 
                                    ConstraintScheduler cs, 
                                    Comparable val) {
        final SetDomain dom = (SetDomain) getDomain().copy();
        dom.getMin().add(val);
        cp.memorize(this, dom);
        getMax().remove(val);
        updateEventMask(EVENT_MAX);
        cs.update(cp, this);
    }

    /**
     * Chooses a new min for the variable.
     * @param cp the choice point stack
     * @param cs the constraint scheduler
     * @param val the value to add
     */
    public void chooseMinByAdding(ChoicePointStack cp, 
                                  ConstraintScheduler cs, 
                                  Comparable val) {
        final SetDomain dom = (SetDomain) getDomain().copy();
        dom.getMax().remove(val);
        cp.memorize(this, dom);
        getMin().add(val);
        updateEventMask(EVENT_MIN);
        cs.update(cp, this);
    }

    /** 
     * Gets the min of the variable.
     * @return a set
     */
    public final SortedSet getMin() {
        return ((SetDomain) getDomain()).getMin();
    }
  
    /** 
     * Gets the max of the variable.
     * @return a set
     */
    public final SortedSet getMax() {
        return ((SetDomain) getDomain()).getMax();
    }

    /** 
     * Sets the min of the variable.
     * @param min a set
     */
    protected final void setMin(SortedSet min) {
        ((SetDomain) getDomain()).setMin(min);
    }

    /** 
     * Sets the max of the variable.
     * @param max a set
     */
    protected final void setMax(SortedSet max) {
        ((SetDomain) getDomain()).setMax(max);
    }
}
