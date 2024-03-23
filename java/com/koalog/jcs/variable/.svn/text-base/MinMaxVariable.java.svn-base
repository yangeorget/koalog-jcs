package com.koalog.jcs.variable;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Category;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.Constraint;
import com.koalog.jcs.domain.Domain;
import com.koalog.jcs.solver.BacktrackSolver;

/**
 * This class represents variables 
 * that support events corresponding to the modification 
 * of their minimal or maximal values. 
 *
 * <P>Dependencies:
 * <UL>
 * <LI>use <CODE>addGroundConstraint</CODE> in <CODE>updateConstraints</CODE> 
 * to make a constraint depend on 
 * the groundness of the variable,
 * <LI>use <CODE>addMinConstraint</CODE> in <CODE>updateConstraints</CODE>
 * to make a constraint depend on
 * any modification of the minimal value of the domain of the variable,
 * <LI>use <CODE>addMaxConstraint</CODE> in <CODE>updateConstraints</CODE>
 * to make a constraint depend on
 * any modification of the maximal value of the domain of the variable,
 * <LI>use <CODE>addMinMaxConstraint</CODE> in <CODE>updateConstraints</CODE>
 * to make a constraint depend on
 * any modification of the bounds of the domain of the variable,
 * <LI>use <CODE>addDomConstraint</CODE> in <CODE>updateConstraints</CODE>
 * to make a constraint depend on 
 * any modification of the domain of the variable.
 * </UL>
 *
 * @author Yan Georget
 */
public abstract class MinMaxVariable extends BaseVariable {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(MinMaxVariable.class);
 
    //------------------------------------------------------------------------
    // CONSTANTS
    //------------------------------------------------------------------------ 
    /** An event for the modification of the min. */
    public static final int EVENT_MIN = 2;
    /** An event for the modification of the max. */
    public static final int EVENT_MAX = 4;
    /** An event for the modification of the min and the max. */
    public static final int EVENT_MINMAX = EVENT_MIN | EVENT_MAX;

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** A list of constraints 
        that depend only on the min of the variable. */
    protected List minConstraints;
    /** A list of constraints 
        that depend only on the max of the variable. */
    protected List maxConstraints;
    /** A list of constraints 
        that depend on the min and the max of the variable. */
    protected List minmaxConstraints;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------  
    /**
     * Constructs a variable with a given name and domain.
     * @param name the name of the variable
     * @param domain the domain of the variable
     */
    protected MinMaxVariable(String name, Domain domain) {
        super(name, domain);
        minConstraints = new ArrayList(DEFAULT_DEPENDENCIES_SIZE);
        maxConstraints = new ArrayList(DEFAULT_DEPENDENCIES_SIZE);
        minmaxConstraints = new ArrayList(DEFAULT_DEPENDENCIES_SIZE);
    }

    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------  
    /** 
     * Returns the list of constraints 
     * depending on the modification of the min or the max 
     * of the domain of the variable.
     * @return a list
     * @since 3.0
     */
    public final List getMinMaxConstraints() {
        return minmaxConstraints;
    }

    /** 
     * Returns the list of constraints 
     * depending on the modification of the min 
     * of the domain of the variable.
     * @return a list
     * @since 3.0
     */
    public final List getMinConstraints() {
        return minConstraints;
    }

    /** 
     * Returns the list of constraints 
     * depending on the modification of the max 
     * of the domain of the variable.
     * @return a list
     * @since 3.0
     */
    public final List getMaxConstraints() {
        return maxConstraints;
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------  
    /** @see com.koalog.jcs.variable.Variable */
    public void setFullEventMask() {
        eventMask = EVENT_MINMAX;
    }

    /** 
     * Adds a constraint to the list of constraints 
     * depending on the min and the max of the variable.
     * @param c a constraint
     */
    public final void addMinMaxConstraint(Constraint c) {
        minmaxConstraints.add(c);
    }

    /** 
     * Adds a constraint to the list of constraints 
     * depending on the min of the variable.
     * @param c a constraint
     */
    public final void addMinConstraint(Constraint c) {
        minConstraints.add(c);
    }

    /** 
     * Adds a constraint to the list of constraints 
     * depending on the max of the variable.
     * @param c a constraint
     */
    public final void addMaxConstraint(Constraint c) {
        maxConstraints.add(c);
    }

    /** 
     * Adds a constraint to the list of constraints 
     * depending on the domain of the variable.
     * @param c a constraint
     */
    public final void addDomConstraint(Constraint c) {
        domConstraints.add(c);
        minmaxConstraints.add(c);
    }

    /** @see com.koalog.jcs.variable.Variable */
    public int getConstraintNb() {
        if (constraintNb == -1) {
            Set s = new HashSet(minmaxConstraints);
            s.addAll(minConstraints);
            s.addAll(maxConstraints);
            s.addAll(groundConstraints);
            // since domConstraints are also in minmaxConstraints
            // there is no need to add them
            constraintNb = s.size();
        }
        return constraintNb;
    }

    //------------------------------------------------------------------------
    // ABSTRACT METHODS
    //------------------------------------------------------------------------  
    /**
     * Shaves the minimal value of the variable.
     * @param solver a solver for enforcing consistency
     * @param deep true for deep shaving false otherwise
     * @throws ConstraintInconsistencyException when there is no solution
     * @since 3.0
     */
    public abstract void shaveMin(BacktrackSolver solver, boolean deep) 
        throws ConstraintInconsistencyException;

    /**
     * Shaves the maximal value of the variable.
     * @param solver a solver for enforcing consistency
     * @param deep true for deep shaving false otherwise
     * @throws ConstraintInconsistencyException when there is no solution
     * @since 3.0
     */
    public abstract void shaveMax(BacktrackSolver solver, boolean deep) 
        throws ConstraintInconsistencyException;
}
