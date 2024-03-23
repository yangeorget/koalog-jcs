package com.koalog.jcs.choicepoint;
  
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.domain.Domain;
import com.koalog.jcs.variable.Variable;

/** 
 * This interface describes a choice point stack.
 *
 * <P>A choice point stack is used by a backtrack solver 
 * to record the changes occuring between successive choices.</P>
 *
 * <P>A <CODE>ChoicePointStack</CODE> is a wrapper for a stack
 * of choice points but contains methods specific to choice points as
 * <CODE>memorize(StorableReference) and memorize(Variable, Domain)</CODE>
 * which modify the elements of the internal stack.
 *
 * <P>Note to programmers: 
 * an interesting method of <CODE>ChoicePointStack</CODE> 
 * is the <CODE>push</CODE> method, that creates a fresh choice point 
 * on top of the stack. You will have to use this method if you decide to 
 * write your own backtrack solver.
 *
 * @see ChoicePoint
 * @see com.koalog.jcs.solver.BacktrackSolver
 * @see StorableReference
 * @see com.koalog.jcs.domain.Domain
 * @see com.koalog.jcs.variable.Variable
 * @author Yan Georget
 */
public interface ChoicePointStack {
     //------------------------------------------------------------------------
     // CONSTANTS
     //------------------------------------------------------------------------
     /** The default capacity of a choice point stack. */
     int DEFAULT_CAPACITY = 1024;

    //------------------------------------------------------------------------
    // ABSTRACT METHODS
    //------------------------------------------------------------------------ 
    /**
     * Memorizes a new domain for a variable
     * @param v a variable
     * @param d a domain
     */
    void memorize(Variable v, Domain d);

    /**
     * Memorizes a storable reference.
     * @param ref a storable reference
     * @since 2.1
     */
    void memorize(StorableReference ref);

    /** 
     * Tests whether the choice point stack is empty.
     * @return a boolean
     */
    boolean isEmpty();

    /**
     * Resets the static values of the storables references and 
     * discards all the choice points.
     * @param cs the constraint scheduler
     */ 
    void reset(ConstraintScheduler cs);

    /**
     * Pops and restores the current choice point.
     * @param cs the constraint scheduler
     */ 
    void pop(ConstraintScheduler cs);

    /** 
     * Pushes a new choice point on top of the stack.
     */
    void push();

    /**
     * Returns the maximal depth or size of the choice point stack.
     * @return an int
     */
    int maxDepth();

    /**
     * Returns the current date.
     * @return an integer
     */
    int getDate();
}

/*
 * $Log$
 * Revision 1.17  2004/06/29 16:08:48  yan
 * avoiding use of stacks
 *
 * Revision 1.16  2004/06/24 12:09:58  yan
 * fixed style
 *
 * Revision 1.15  2004/06/24 12:05:21  yan
 * various fixes
 *
 * Revision 1.14  2004/06/14 15:55:52  yan
 * using storable references and values
 *
 * Revision 1.13  2004/05/05 11:49:29  yan
 * fixed style and jdoc
 *
 * Revision 1.12  2004/04/09 14:41:57  yan
 * removal of events
 *
 * Revision 1.11  2003/09/21 18:11:56  yan
 * removed clear method
 *
 * Revision 1.10  2003/08/02 15:19:58  yan
 * added clear method
 *
 * Revision 1.9  2003/03/20 18:05:57  yan
 * added entailment check
 *
 * Revision 1.8  2003/02/11 10:23:30  yan
 * fixed style
 *
 * Revision 1.7  2003/02/03 12:03:05  yan
 * using choice point kind of pool
 *
 * Revision 1.6  2002/06/16 16:38:50  mphilip
 * Modified jdoc.
 *
 * Revision 1.5  2002/06/16 13:45:33  yan
 * added javadoc
 *
 * Revision 1.4  2002/05/22 09:27:25  yan
 * added javadoc
 *
 * Revision 1.3  2002/05/02 16:16:12  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
