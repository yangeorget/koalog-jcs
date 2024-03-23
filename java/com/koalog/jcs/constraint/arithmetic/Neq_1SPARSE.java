package com.koalog.jcs.constraint.arithmetic;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.constraint.UnaryConstraint;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;

/**
 * This constraint enforces x<>c.
 *
 * <P>Algorithmic informations 
 * (see 
 * {@link com.koalog.jcs.constraint.Constraint Constraint}
 * for more details on complexity and idempotence):
 * <TABLE border="1">
 * <TR>
 *    <TD><B>Idempotent</B></TD>
 *    <TD align="right">yes</TD>
 * </TR>
 * <TR>
 *    <TD><B>Complexity</B></TD>
 *    <TD align="right">1</TD>
 * </TR>
 * </TABLE>
 *
 * @author Yan Georget
 */
public class Neq_1SPARSE extends UnaryConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    private int c;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param x an integer variable with a sparse domain
     * @param c a constant
     */
    public Neq_1SPARSE(IntegerVariable x, int c) {
        super(x);
        name = "x<>" + c;
        this.c = c;
        idempotent = true;
        complexity = 1;
    }
  
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Sets the variable to be different from the constant.
     * @param cp the choice point stack
     * @param cs a constraint scheduler
     * @throws ConstraintInconsistencyException when there is no solution
     */
    public void init(ChoicePointStack cp,
                     ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        ((IntegerVariable) variables[0]).adjustDifferent(cp, cs, this, c); 
        // no need to call entailed(cp)
    }

    /**
     * Does nothing.
     * @param cp the choice point stack
     * @param cs a constraint scheduler
     * @throws ConstraintInconsistencyException when there is no solution
     */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
    }
}
/*
 * $Log$
 * Revision 1.4  2005/07/22 14:53:24  yan
 * pretty variable name
 *
 * Revision 1.3  2005/07/18 17:36:50  yan
 * fixed style
 *
 * Revision 1.2  2005/07/18 15:30:17  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.1  2005/02/02 14:15:37  yan
 * added back
 *
 */
