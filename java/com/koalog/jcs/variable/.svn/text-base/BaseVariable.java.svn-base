package com.koalog.jcs.variable;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Category;

import com.koalog.jcs.VariableInconsistencyException;
import com.koalog.jcs.BaseNamedObject;
import com.koalog.jcs.choicepoint.StorableValue;
import com.koalog.jcs.constraint.Constraint;
import com.koalog.jcs.domain.Domain;

/**
 * This class is the reification of the concept of variable.
 *
 * <P>Programmers will probably not need to use this class 
 * but one of its subclasses.
 * 
 * <P>Dependencies:
 * <UL>
 * <LI>use <CODE>addGroundConstraint</CODE> in <CODE>updateConstraints</CODE> 
 * to make a constraint depend on 
 * the groundness of the variable.,
 * <LI>use <CODE>addDomConstraint</CODE> in <CODE>updateConstraints</CODE>
 * to make a constraint depend on 
 * any modification of the domain of the variable.
 * </UL>
 * 
 * @see Domain
 * @see Constraint
 * @author Yan Georget
 */
public class BaseVariable extends BaseNamedObject implements Variable {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(BaseVariable.class);
    private static VariableInconsistencyException VIE = 
       new VariableInconsistencyException();
    private static int counter;

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** A list of constraints that need to be waken up when the 
        variable becomes ground. 
        Constraints contained in this list 
        won't be contained in any other list. */
    protected List groundConstraints;
    /** A list of constraints that need to be waken up when the 
        domain of the variable changes. */
    protected List domConstraints;
    /** A cache for the number of constraints depending on this variable. */
    int constraintNb;
    /** The event mask. */
    protected int eventMask;
    /** The domain. */
    private Domain domain;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Constructs a variable with the given name and domain.
     * @param name the name of the variable
     * @param domain the domain of the variable
     */
    protected BaseVariable(String name, Domain domain) {
        super(name);
        domConstraints = new ArrayList(DEFAULT_DEPENDENCIES_SIZE);
        groundConstraints = new ArrayList(DEFAULT_DEPENDENCIES_SIZE);
        constraintNb = -1;
        unid = ++counter;
        this.domain = domain;
    }

    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.variable.Variable */
    public final Domain getDomain() {
        return domain;
    }

    /** @see com.koalog.jcs.variable.Variable */
    public final void setDomain(Domain domain) {
       this.domain = domain;
    }

    /** @see com.koalog.jcs.variable.Variable */
    public final int getEventMask() {
        return eventMask;
    }

    /** @see com.koalog.jcs.variable.Variable */
    public final List getDomConstraints() {
        return domConstraints;
    }

    /** @see com.koalog.jcs.variable.Variable */
    public final List getGroundConstraints() {
        return groundConstraints;
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------  
    /** @see com.koalog.jcs.choicepoint.StorableReference */
    public final StorableValue getStorableValue() {
        return domain;
    }

    /** @see com.koalog.jcs.choicepoint.StorableReference */
    public final void setStorableValue(StorableValue storableValue) {
       domain = (Domain) storableValue;
    }

    /** @see com.koalog.jcs.variable.Variable */
    public final void updateEventMask(int mask) {
        eventMask |= mask;
    }

    /** @see com.koalog.jcs.variable.Variable */
    public final void clearEventMask() {
        eventMask = 0;
    }

    /** @see com.koalog.jcs.variable.Variable */
    public void setFullEventMask() {
        eventMask = EVENT_DOM;
    }
        
    /** @see com.koalog.jcs.variable.Variable */
    public final void fail(Constraint c) throws VariableInconsistencyException {
        VIE.setConstraint(c);
        VIE.setVariable(this);
        throw VIE;
    }

    /** @see com.koalog.jcs.variable.Variable */
    public void addDomConstraint(Constraint c) {
        domConstraints.add(c);
    }

    /** @see com.koalog.jcs.variable.Variable */
    public void addGroundConstraint(Constraint c) {
        groundConstraints.add(c);
    }

    /** 
     * Converts the variable to a string.
     * @return the generated String
     */
    public String toString() {
        StringBuffer buf = new StringBuffer(getFullName());
        buf.append("=");
        buf.append(domain);
        return buf.toString();
    }

    /** @see com.koalog.jcs.variable.Variable */
    public final boolean isInstantiated() {
        return domain.isSingleton();
    }

    /** @see com.koalog.jcs.variable.Variable */
    public final boolean isNotInstantiated() {
        return domain.isNotSingleton();
    }

    /** @see com.koalog.jcs.variable.Variable */
    public int getConstraintNb() { 
        if (constraintNb == -1) {
            Set s = new HashSet(domConstraints);
            s.addAll(groundConstraints);
            constraintNb = s.size();
        }
        return constraintNb;
    }

    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------  
    /**
     * Returns true iff all the variables are instantiated.
     * @param variables an array of variables
     * @return a boolean
     * @since 2.2
     */
     public static boolean allInstantiated(Variable[] variables) {
         for (int i=variables.length; --i>=0;) {
             if (variables[i].isNotInstantiated()) {
                 return false;
             }
         } 
         return true;
    }

    /**
     * Returns true iff not all the variables are instantiated.
     * @param variables an array of variables
     * @return a boolean
     * @since 2.2
     */
     public static boolean notAllInstantiated(Variable[] variables) {
         for (int i=variables.length; --i>=0;) {
             if (variables[i].isNotInstantiated()) {
                 return true;
             }
         } 
         return false;
    }

    /**
     * Returns the number of variables that are not instantiated.
     * @param variables an array of variables
     * @return an integer
     * @since 2.2
     */
     public static int nbNotInstantiated(Variable[] variables) {
         int nb = 0;
         for (int i=variables.length; --i>=0;) {
             if (variables[i].isNotInstantiated()) {
                 nb++;
             }
         } 
         return nb;
    }

}
/*
 * $Log$
 * Revision 1.34  2004/11/29 09:45:55  yan
 * added method isNotInstantiated
 *
 * Revision 1.33  2004/11/02 13:54:28  yan
 * optimized for loops
 *
 * Revision 1.32  2004/10/12 14:56:04  yan
 * named object are now comparable
 *
 * Revision 1.31  2004/09/16 12:26:50  yan
 * factorized some code in updateScheduler
 *
 * Revision 1.30  2004/09/15 16:20:41  yan
 * using a constant for the default name
 *
 * Revision 1.29  2004/09/15 16:02:31  yan
 * using new counter mechanism
 *
 * Revision 1.28  2004/09/15 14:19:18  yan
 * using new names (with counters)
 *
 * Revision 1.27  2004/07/13 16:19:47  yan
 * added a static method
 *
 * Revision 1.26  2004/07/12 17:57:46  yan
 * added 2 methods
 *
 * Revision 1.25  2004/06/24 10:11:19  yan
 * changed the order in which the constraint are added to the scheduler
 *
 * Revision 1.24  2004/06/21 16:01:21  yan
 * fixed javadoc
 *
 * Revision 1.23  2004/06/16 15:59:54  yan
 * fixed style
 *
 * Revision 1.22  2004/06/16 15:57:05  yan
 * BaseVariable now extends BaseStorableReference
 *
 * Revision 1.21  2004/06/14 15:55:52  yan
 * using storable references and values
 *
 * Revision 1.20  2004/05/06 09:54:06  yan
 * fixed toString method
 *
 * Revision 1.19  2004/04/09 14:43:11  yan
 * removal of events
 *
 * Revision 1.18  2004/04/01 19:21:42  yan
 * problem is not a constraint anymore
 *
 * Revision 1.17  2003/10/02 19:31:20  yan
 * changed updateScheduler method signature
 *
 * Revision 1.16  2003/10/01 18:30:39  yan
 * made some methods final
 *
 * Revision 1.15  2003/08/03 16:23:50  yan
 * changed updateScheduler method's signature
 *
 * Revision 1.14  2003/06/05 12:19:02  yan
 * fixed javadoc
 *
 * Revision 1.13  2003/05/05 12:03:29  yan
 * fixed bug in getConstraintNb
 *
 * Revision 1.12  2003/04/30 16:57:33  yan
 * fixed getConstraintNb computation
 *
 * Revision 1.11  2003/04/01 14:02:58  yan
 * various fixes (including minmax and style)
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
 * Revision 1.6  2002/06/16 16:23:39  mphilip
 * Modified jdoc.
 *
 * Revision 1.5  2002/06/16 14:00:27  yan
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
