package com.koalog.jcs.domain;

import com.koalog.jcs.choicepoint.StorableValue;

/**
 * The <CODE>Domain</CODE> interface is the root of the domain hierarchy. 
 * All domains implement the methods of this interface.
 *
 * <P>A domain stores the possible values for a variable.
 * During the search for a solution by a solver, each domain is refined
 * until the corresponding variable is instantiated.</P>
 *
 * @author Yan Georget
 * @see com.koalog.jcs.variable.Variable
 * @see com.koalog.jcs.solver.Solver
 */
public interface Domain extends StorableValue {
    //------------------------------------------------------------------------
    // CONSTANTS
    //------------------------------------------------------------------------  
    /** A string for displaying an empty domain. */
    String EMPTY = "empty";

    //------------------------------------------------------------------------
    // ABSTRACT METHODS
    //------------------------------------------------------------------------  
    /** 
     * Tests whether the domain is equals (has the same possible values) 
     * as the domain given as a parameter.
     * @param domain a domain
     * @return a boolean
     */
    boolean equals(Domain domain);

    /**
     * Tests whether the domain is empty (has no possible value).
      *
     * <P>Either <CODE>isNotSingleton</CODE>, 
     * <CODE>isSingleton</CODE> or
     * <CODE>isEmpty</CODE> is true.
     *
     * @return a boolean
     * @see #isSingleton
     * @see #isNotSingleton
     */
    boolean isEmpty();

    /** 
     * Tests whether the domain is a singleton (has a single possible value).
     *
     * <P>Either <CODE>isNotSingleton</CODE>, 
     * <CODE>isSingleton</CODE> or
     * <CODE>isEmpty</CODE> is true.
     *
     * @return a boolean
     * @see #isEmpty
     * @see #isNotSingleton
     */
    boolean isSingleton();

    /** 
     * Tests whether the domain is not a singleton 
     * (has more than a single possible value).
     *
     * <P>Either <CODE>isNotSingleton</CODE>, 
     * <CODE>isSingleton</CODE> or
     * <CODE>isEmpty</CODE> is true.
     *
     * @return a boolean
     * @since 2.4
     * @see #isEmpty
     * @see #isSingleton
     */
    boolean isNotSingleton();
}
/*
 * $Log$
 * Revision 1.15  2005/02/02 14:09:40  yan
 * fixed style
 *
 * Revision 1.14  2004/11/29 09:31:04  yan
 * added jdoc
 *
 * Revision 1.13  2004/11/25 17:18:26  yan
 * added isNotSingleton
 *
 * Revision 1.12  2004/10/01 17:49:40  yan
 * fixed style
 *
 * Revision 1.11  2004/10/01 15:23:32  yan
 * using a constant for empty domains
 *
 * Revision 1.10  2004/06/14 15:55:52  yan
 * using storable references and values
 *
 * Revision 1.9  2004/05/05 11:44:13  yan
 * fixed style
 *
 * Revision 1.8  2003/03/07 13:54:00  yan
 * fixed style
 *
 * Revision 1.7  2002/06/16 17:19:58  mphilip
 * Modified jdoc.
 *
 * Revision 1.6  2002/06/16 13:56:07  yan
 * added javadoc
 *
 * Revision 1.5  2002/05/22 09:27:25  yan
 * added javadoc
 *
 * Revision 1.4  2002/05/03 14:26:49  yan
 * added javadoc
 *
 * Revision 1.3  2002/05/02 16:15:22  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
