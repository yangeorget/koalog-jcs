package com.koalog.jcs;

import com.koalog.jcs.constraint.Constraint;

/**
 * This exception is thrown when the solver detects an inconsistency during
 * the filtering of a constraint.
 *  
 * <P>Programmers may want to throw an 
 * <CODE>ConstraintInconsistencyException</CODE>
 * in the <CODE>filter</CODE> method of a custom constraint.
 *
 * @see com.koalog.jcs.InconsistencyException
 * @see com.koalog.jcs.constraint.Constraint 
 *
 * @author Yan Georget
 * @since 2.6
 */ 
public class ConstraintInconsistencyException extends InconsistencyException {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    Constraint constraint;

    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------
    /**
     * Gets the constraint responsible for the exception.
     * @return a constraint
     */
    public final Constraint getConstraint() {
        return constraint;
    }

    /**
     * Sets the constraint responsible for the exception.
     * @param constraint a constraint
     * @since 3.0
     */
    public final void setConstraint(Constraint constraint) {
        this.constraint = constraint;
    }
}
/*
 * $Log$
 * Revision 1.3  2005/07/22 14:53:59  yan
 * storing unid instead of name
 *
 * Revision 1.2  2005/07/18 17:36:50  yan
 * fixed style
 *
 * Revision 1.1  2005/07/18 15:34:50  yan
 * initial revision
 *
 */
