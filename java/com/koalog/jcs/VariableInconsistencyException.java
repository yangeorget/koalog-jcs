package com.koalog.jcs;

import com.koalog.jcs.variable.Variable;

/**
 * This exception is thrown when the solver detects an inconsistency
 * when adjusting the domain of a variable (its domain becomes empty)
 * during the filtering of a constraint.
 *
 * @see com.koalog.jcs.InconsistencyException
 * @see com.koalog.jcs.ConstraintInconsistencyException
 * @see com.koalog.jcs.domain.Domain
 * @see com.koalog.jcs.constraint.Constraint
 * @see com.koalog.jcs.variable.Variable 
 *
 * @author Yan Georget
 * @since 2.6
 */ 
public class VariableInconsistencyException 
    extends ConstraintInconsistencyException {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    Variable variable;

    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------
    /**
     * Gets the variable responsible for the exception.
     * @return a variable
     */
    public final Variable getVariable() {
        return variable;
    }

    /**
     * Sets the variable responsible for the exception.
     * @param variable a variable
     * @since 3.0
     */
    public final void setVariable(Variable variable) {
        this.variable = variable;
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
