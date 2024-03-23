package com.koalog.jcs.choicepoint;

import java.util.Set;
import com.koalog.jcs.variable.Variable;
import com.koalog.jcs.constraint.ConstraintScheduler;

/**
 * Interface for a choice point.
 * 
 * <P>A choice point is responsible for: 
 * <UL>
 * <LI>memorizing the changes 
 * that occured since a choice has been made on a variable,
 * <LI>restoring the state 
 * (values of storable references) preceding the choice.
 * </UL>
 *
 * <P>Note to programmers: 
 * it is unlikely that you will need to use choice points directly.
 *
 * @see StorableValue
 * @see StorableReference
 * @see com.koalog.jcs.variable.Variable
 * @author Yan Georget
 */
public interface ChoicePoint {
    //------------------------------------------------------------------------
    // CONSTANTS
    //------------------------------------------------------------------------ 
    /** The default capacity of a choice point. */
    int DEFAULT_CAPACITY = 256;

    //------------------------------------------------------------------------
    // ABSTRACT METHODS
    //------------------------------------------------------------------------ 
    /**
     * Returns the set of the storable references stored in this choice point.
     * @return a set
     * @since 2.2
     */
    Set getStorableReferences();

    /**
     * Stores a variable on which a choice has been made.
     * @param var a variable
     * @since 2.1
     */
    void storeChoiceVariable(Variable var);

    /**
     * Returns the set of the variables on which choices have been made.
     * @return a set
     * @since 2.2
     */
    Set getChoiceVariables();

    /** 
     * Memorizes (if necessary) the value of a storable reference.
     * @param ref a storable reference
     */
    void memorize(StorableReference ref);

    /** 
     * Stores a value for a storable reference.
     * @param ref a storable reference
     * @param val a value
     */
    void put(StorableReference ref, StorableValue val);

    /** 
     * Gets the stored value of a storable reference.
     * @param ref a storable reference
     * @return its stored value
     */
    StorableValue get(StorableReference ref);

    /**
     * Restores the choice point.
     * @param cs the constraint scheduler
     * @param cp the choice point stack
     */
    void restore(ConstraintScheduler cs, ChoicePointStack cp);

    /**
     * Merge the choice point with a choice point which is younger.
     * @param cp a young choice point
     */
    void merge(ChoicePoint cp);

   /**
    * Clears the choice point.
    */
    void clear();
}
/*
 * $Log$
 * Revision 1.17  2004/09/16 12:28:57  yan
 * added cp stack as an argument to the restore method
 *
 * Revision 1.16  2004/07/12 12:07:56  yan
 * added methods for merging choice points
 *
 * Revision 1.15  2004/06/24 12:09:50  yan
 * fixed style
 *
 * Revision 1.14  2004/06/24 12:05:34  yan
 * various fixes
 *
 * Revision 1.13  2004/06/14 15:55:52  yan
 * using storable references and values
 *
 * Revision 1.12  2004/05/05 11:49:29  yan
 * fixed style and jdoc
 *
 * Revision 1.11  2004/04/13 15:28:48  yan
 * optimized updates of constraint scheduler in choice point stack
 *
 * Revision 1.10  2004/04/09 14:41:57  yan
 * removal of events
 *
 * Revision 1.9  2004/03/14 16:44:07  yan
 * removed unused methods
 *
 * Revision 1.8  2003/02/03 12:03:05  yan
 * using choice point kind of pool
 *
 * Revision 1.7  2003/02/03 09:10:49  yan
 * removed unecessary methods
 *
 * Revision 1.6  2002/06/16 16:32:50  mphilip
 * Modified jdoc.
 *
 * Revision 1.5  2002/06/16 13:45:33  yan
 * added javadoc
 *
 * Revision 1.4  2002/05/03 14:26:48  yan
 * added javadoc
 *
 * Revision 1.3  2002/05/02 16:16:12  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
