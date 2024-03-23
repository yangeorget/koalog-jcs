package com.koalog.jcs.variable;

import java.util.List;

import com.koalog.jcs.VariableInconsistencyException;
import com.koalog.jcs.NamedObject;
import com.koalog.jcs.choicepoint.StorableReference;
import com.koalog.jcs.constraint.Constraint;
import com.koalog.jcs.domain.Domain;

/**
 * The <CODE>Variable</CODE> interface is the root of the variable hierarchy. 
 * All variables implement the methods of this interface.
 *
 * <P>A variable has mainly a <CODE>Domain</CODE>.</P>
 *
 * <P>Note for programmers: 
 * designing a new type of variable, 
 * usually reduces to designing a new type of domain.</P>
 *
 * @see Domain
 * @author Yan Georget
 */
public interface Variable extends StorableReference, NamedObject {
    //------------------------------------------------------------------------
    // CONSTANTS
    //------------------------------------------------------------------------
    /** A default name for variables. */
    String DEFAULT_NAME = "_";

    /** A default size for dependencies. */
    int DEFAULT_DEPENDENCIES_SIZE = 32;

    /** An event for domain modifications. */
    int EVENT_DOM = 1;
 
    //------------------------------------------------------------------------
    // ABSTRACT METHODS
    //------------------------------------------------------------------------
    /** 
     * Clears the event mask. 
     * @since 3.0
     */
    void clearEventMask();

    /**
     * Sets a full event mask.
     * @since 3.0
     */
    void setFullEventMask();

    /** 
     * Updates the event mask 
     * by adding the events of the mask given as a parameter.
     * @param eventMask a mask of events
     * @since 3.0
     */
    void updateEventMask(int eventMask);
    
    /**
     * Returns the event mask
     * @return a mask of events as an int
     * @since 3.0
     */
    int getEventMask(); 
        
    /** 
     * Fails.
     * @param c the constraint to which the variable belongs
     * @throws VariableInconsistencyException to indicate the failure
     * @since 3.0
     */
    void fail(Constraint c) throws VariableInconsistencyException;

    /**
     * Gets the variable domain.
     * @return value of domain
     */
    Domain getDomain();

    /**
     * Sets the variable domain.
     * @param domain a domain
     */
    void setDomain(Domain domain);

    /**
     * Tests whether the variable is instantiated.
     *
     * <P>Returns true iff the domain is a singleton.
     *
     * <P>When the domain is not empty, then either 
     * <CODE>isInstantiated</CODE> or <CODE>isNotInstantiated</CODE> 
     * is true.
     *
     * @return a boolean
     * @see #isNotInstantiated
     * @see com.koalog.jcs.domain.Domain
     */
    boolean isInstantiated();

    /**
     * Tests whether the variable is not instantiated.
     *
     * <P>Returns true iff the domain is not a singleton.
     *
     * <P>When the domain is not empty, then either 
     * <CODE>isInstantiated</CODE> or <CODE>isNotInstantiated</CODE> 
     * is true.
     *
     * @return a boolean
     * @since 2.4
     * @see #isNotInstantiated
     * @see com.koalog.jcs.domain.Domain
     */
    boolean isNotInstantiated();

    /**
     * Returns the number of constraints 
     * depending on the variable.
     * @return an integer
     */
    int getConstraintNb();

    /** 
     * Adds a constraint to the list of constraints 
     * depending on the domain of the variable.
     * @param c a constraint
     */
    void addDomConstraint(Constraint c);

    /** 
     * Returns the list of constraints 
     * depending on the domain of the variable.
     * @return a list
     * @since 3.0
     */
    List getDomConstraints();

    /** 
     * Adds a constraint to the list of constraints 
     * to be waken up when the variable becomes ground.
     * @param c a constraint
     */
    void addGroundConstraint(Constraint c);

    /** 
     * Returns to the list of constraints 
     * to be waken up when the variable becomes ground.
     * @return a list
     * @since 3.0
     */
    List getGroundConstraints();
}
/*
 * $Log$
 * Revision 1.21  2004/11/29 09:45:14  yan
 * added jdoc
 *
 * Revision 1.20  2004/10/12 14:56:04  yan
 * named object are now comparable
 *
 * Revision 1.19  2004/09/16 12:26:50  yan
 * factorized some code in updateScheduler
 *
 * Revision 1.18  2004/09/15 16:20:32  yan
 * using a constant for the default name
 *
 * Revision 1.17  2004/09/15 16:02:31  yan
 * using new counter mechanism
 *
 * Revision 1.16  2004/09/15 14:19:08  yan
 * using new names (with counters)
 *
 * Revision 1.15  2004/06/14 15:55:52  yan
 * using storable references and values
 *
 * Revision 1.14  2004/04/09 14:43:11  yan
 * removal of events
 *
 * Revision 1.13  2004/04/01 19:21:42  yan
 * problem is not a constraint anymore
 *
 * Revision 1.12  2003/10/02 19:31:20  yan
 * changed updateScheduler method signature
 *
 * Revision 1.11  2003/08/03 16:23:50  yan
 * changed updateScheduler method's signature
 *
 * Revision 1.10  2003/03/27 10:42:59  yan
 * introduced events
 *
 * Revision 1.9  2003/03/09 18:51:19  yan
 * fixed style
 *
 * Revision 1.8  2003/03/09 16:57:16  yan
 * optimized choice point (no restoreDomain method anymore)
 *
 * Revision 1.7  2003/03/07 13:51:36  yan
 * fixed style
 *
 * Revision 1.6  2002/06/16 19:42:42  mphilip
 * Modified jdoc.
 *
 * Revision 1.5  2002/06/16 14:00:28  yan
 * added javadoc
 *
 * Revision 1.4  2002/05/22 09:27:25  yan
 * added javadoc
 *
 * Revision 1.3  2002/05/02 16:11:03  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:44  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
