package com.koalog.jcs.constraint;

import org.apache.log4j.Category;

import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.variable.Variable;

/**
 * This class is a lazy constraint scheduler based on constraint complexities.
 *
 * @see Constraint
 * @author Yan Georget
 */
public class LazyComplexityConstraintScheduler 
    extends ComplexityConstraintScheduler {

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(LazyComplexityConstraintScheduler.class);

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.ConstraintScheduler */
    public void clear() {
        if (constraint != null) {
            // clearing the events on the constraint's variables
            final Variable[] vars = constraint.getVariables();
            for (int i = vars.length; --i>=0;) {
                vars[i].clearEventMask();
            }
        }
        super.clear();
    }


    /** @see com.koalog.jcs.constraint.ConstraintScheduler */
    public void update(ChoicePointStack cp, Variable v) {
        if (constraint == null) {
            super.update(cp, v);
        }
    }

    /** @see com.koalog.jcs.constraint.ConstraintScheduler */
    public Constraint pop(ChoicePointStack cp) {
        if (constraint != null) {
            final Variable[] vars = constraint.getVariables();
            for (int i = vars.length; --i>=0;) {
                final Variable var = vars[i];
                if (var.getEventMask() != 0) {
                    super.update(cp, var);
                }
            }
        }
        return super.pop(cp);
    }
}
/*
 * $Log$
 */

