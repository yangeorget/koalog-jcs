package com.koalog.jcs.constraint;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.choicepoint.ChoicePointStack;

/** 
 * The <CODE>Constraint</CODE> interface is the root of the constraint
 * hierarchy. 
 * All constraints implement the methods of this interface. 
 *
 * <P>A constraint is a relation over variables.</P>
 *
 * <P>From an algorithmic point of view, 
 * it is mainly the reification of a filtering algorithm 
 * (called by the <CODE>filter</CODE> method).
 * </P>
 *
 * @see com.koalog.jcs.variable.Variable
 * @author Yan Georget
 */
public interface Constraint extends AbstractRelation { 
    //------------------------------------------------------------------------
    // ABSTRACT METHODS
    //------------------------------------------------------------------------
    /** 
     * Fails.
     * @throws ConstraintInconsistencyException to indicate the failure
     * @since 3.0
     */
    void fail() throws ConstraintInconsistencyException;

    /**
     * Udpates the constraint dependencies.
     *
     * <P>This methods has to be implemented for each constraint.
     * See the documentation corresponding to the type of variables 
     * involved in the constraint for more information on specific dependencies.
     */
    void updateDependencies();

    /** 
     * Indicates whether the constraint is idempotent. 
     * 
     * <P>If it is idempotent then 
     * the filter method doesn't need to be applied 
     * more than once in a row.</P> 
     * @return a boolean
     */
    boolean isIdempotent();

    /** 
     * Returns the complexity of the underlying filtering algorithm.
     *
     * @return an int
     */
    int getComplexity();

    /** 
     * Returns true iff the constraint complexity depends 
     * neither on the variables number nor on the domain size.
     * @return a boolean
     * @since 2.2
     */
    boolean hasConstantComplexity();

    /**
     * Filters the domains of the constraint variables.
     * @param cp the choice point stack
     * @param cs the constraint scheduler
     * @throws ConstraintInconsistencyException when there is no solution
     */
    void filter(ChoicePointStack cp, ConstraintScheduler cs) 
        throws ConstraintInconsistencyException;

    /**
     * Performs the initial filtering 
     * of the domains of the constraint variables.
     *
     * <P>This method is called exactly once: 
     * <UL>
     * <LI>when solving a problem with a <CODE>BacktrackSolver</CODE>,
     * <LI>when optimizing a problem with a <CODE>BacktrackOptimizer</CODE> 
     * in mode <CODE>Optimizer.CONTINUE</CODE> 
     * (it is called several times in mode <CODE>Optimizer.RESTART</CODE>).
     * </UL>
     *
     * @param cp the choice point stack
     * @param cs the constraint scheduler
     * @throws ConstraintInconsistencyException when there is no solution
     * @see com.koalog.jcs.solver.BacktrackSolver
     * @see com.koalog.jcs.optimizer.BacktrackOptimizer
     */
    void init(ChoicePointStack cp, ConstraintScheduler cs) 
        throws ConstraintInconsistencyException;

    /**
     * Returns the date when the constraint has been entailed.
     * @return the date when the constraint has been entailed or -1
     */
    int getDateEntailed();
    
    /**
     * Marks the constraint as entailed.
     * @param cp a choice point stack
     */ 
    void entailed(ChoicePointStack cp);

    /**
     * Returns the date when the constraint is scheduled to be filtered.
     * @return the date when the constraint is scheduled to be filtered 
     * or -1
     */
    int getDateScheduled();

    /**
     * Sets the date when the constraint is scheduled to be filtered.
     * @param dateScheduled 
     * the date when the constraint is scheduled to be filtered or -1
     */
    void setDateScheduled(int dateScheduled);
}
/*
 * $Log$
 * Revision 1.33  2005/07/18 15:32:55  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.32  2004/10/01 17:49:40  yan
 * fixed style
 *
 * Revision 1.31  2004/10/01 17:41:40  yan
 * various small refactoring
 *
 * Revision 1.30  2004/09/30 15:42:52  yan
 * using AbstractRelation
 *
 * Revision 1.29  2004/09/17 12:53:10  yan
 * renamed updateConstraints
 *
 * Revision 1.28  2004/09/15 16:02:31  yan
 * using new counter mechanism
 *
 * Revision 1.27  2004/06/27 13:04:47  yan
 * added javadoc
 *
 * Revision 1.26  2004/06/24 14:19:31  yan
 * set of scheduled constraints encoded in the constraints
 *
 * Revision 1.25  2004/06/21 16:04:10  yan
 * made entailed method public
 *
 * Revision 1.24  2004/06/17 13:55:09  yan
 * VERSION
 *
 * Revision 1.23  2004/06/15 19:20:38  yan
 * added back init method
 *
 * Revision 1.22  2004/05/05 11:44:47  yan
 * fixed style
 *
 * Revision 1.21  2004/04/09 14:42:15  yan
 * removal of events
 *
 * Revision 1.20  2004/04/01 19:15:30  yan
 * problem is not a constraint anymore
 *
 * Revision 1.19  2004/03/14 16:41:43  yan
 * removed init method
 *
 * Revision 1.18  2003/10/02 23:57:29  yan
 * fixed javadoc
 *
 * Revision 1.17  2003/10/02 23:52:42  yan
 * introduced DOMAIN_SIZE (mean value)
 *
 * Revision 1.16  2003/10/02 19:59:00  yan
 * fixed comment
 *
 * Revision 1.15  2003/06/05 12:19:02  yan
 * fixed javadoc
 *
 * Revision 1.14  2003/03/27 10:52:31  yan
 * added events related methods
 *
 * Revision 1.13  2003/03/20 18:05:57  yan
 * added entailment check
 *
 * Revision 1.12  2003/03/09 17:00:32  yan
 * typos
 *
 * Revision 1.11  2003/02/11 10:24:34  yan
 * fixed style
 *
 * Revision 1.10  2003/01/02 09:48:36  yan
 * added setComplexity
 *
 * Revision 1.9  2002/10/09 11:04:09  yan
 * a constraint is now a comparable
 *
 * Revision 1.8  2002/10/08 17:20:25  yan
 * added complexity property
 *
 * Revision 1.7  2002/06/16 16:49:15  mphilip
 * Modified jdoc.
 *
 * Revision 1.6  2002/06/16 13:47:16  yan
 * added javadoc
 *
 * Revision 1.5  2002/05/22 09:27:25  yan
 * added javadoc
 *
 * Revision 1.4  2002/05/03 14:26:49  yan
 * added javadoc
 *
 * Revision 1.3  2002/05/02 16:24:38  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
