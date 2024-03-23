package com.koalog.jcs.constraint;

import java.util.List;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.variable.Variable;

/**
 * This interface describes a constraint scheduler.
 *
 * <P>A constraint scheduler is mainly responsible for providing 
 * a problem with a constraint to filter.
 *
 * @see Constraint
 * @see Problem
 * @author Yan Georget
 */
public interface ConstraintScheduler {
    /** 
     * Clears the scheduler. 
     */
    void clear();

    /** 
     * Pops a constraint.
     * @param cp a choice point stack
     * @return a constraint
     */
    Constraint pop(ChoicePointStack cp);

    /** 
     * Gets the last constraint popped.
     * @return a constraint or null     
     * @since 3.0
     */
    Constraint getConstraint();

    /**
     * Adds a constraint list to the scheduler.
     * @param cp a choice point stack
     * @param l a constraint list
     */
    void add(ChoicePointStack cp, List l);

    /**
     * Adds a constraint to the scheduler.
     * @param cp a choice point stack
     * @param c a constraint
     */
    void add(ChoicePointStack cp, Constraint c);

    /**
     * Updates the scheduler.
     * @param cp a choice point stack
     * @param var the variable responsible for the update
     */
    void update(ChoicePointStack cp, Variable var);
}
/*
 * $Log$
 * Revision 1.14  2004/09/16 12:24:59  yan
 * cleaned API for adding constraints
 *
 * Revision 1.13  2004/06/24 14:21:05  yan
 * set of scheduled constraints encoded in the constraints
 *
 * Revision 1.12  2004/04/09 14:42:15  yan
 * removal of events
 *
 * Revision 1.11  2004/04/01 19:15:30  yan
 * problem is not a constraint anymore
 *
 * Revision 1.10  2003/10/02 19:33:20  yan
 * changed scheduler update API
 *
 * Revision 1.9  2003/08/03 16:22:35  yan
 * added getProblem method
 *
 * Revision 1.8  2003/07/21 12:29:00  yan
 * fixed style
 *
 * Revision 1.7  2003/07/19 13:20:46  yan
 * removed add method
 *
 * Revision 1.6  2003/06/18 19:21:34  yan
 * added clear method
 *
 * Revision 1.5  2003/03/27 10:52:30  yan
 * added events related methods
 *
 * Revision 1.4  2003/03/20 18:07:06  yan
 * added entailment check
 *
 * Revision 1.3  2003/03/07 11:18:35  yan
 * fixed style
 *
 * Revision 1.2  2002/10/09 13:52:38  yan
 * various cleaning
 *
 * Revision 1.1  2002/10/09 11:05:16  yan
 * initial revision
 *
 */
