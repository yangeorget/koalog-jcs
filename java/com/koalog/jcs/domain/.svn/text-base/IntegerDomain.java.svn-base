package com.koalog.jcs.domain;

import java.util.SortedSet;
import java.util.Iterator;

/**
 * This abstract class contains methods and constants common to 
 * all integer domains.
 * 
 * <P>Integer domains support:
 * <UL>
 * <LI>set operations (size, intersection, difference, ...),</LI>
 * <LI>set relations (intersection, inclusion, ...),</LI> 
 * <LI>additional operations (iteration, ...).</LI>
 * </UL>
 * </P>
 *
 * <P>Note to programmers: 
 * for efficiency reasons, overflow is not handled by domain operations.</P>
 * 
 * @author Yan Georget
 */
public abstract class IntegerDomain implements Domain {
    //------------------------------------------------------------------------
    // CONSTANTS
    //------------------------------------------------------------------------
    /** 
     * The smallest min of a domain.
     */
    public static final int DEFAULT_MIN   = Integer.MIN_VALUE/64;
    /** 
     * The greatest max of a domain.
     */
    public static final int DEFAULT_MAX   = Integer.MAX_VALUE/64;
  
    //------------------------------------------------------------------------
    // ABSTRACT METHODS
    //------------------------------------------------------------------------
    /**
     * Returns the number of elements in the domain.
     * @return an int
     */
    public abstract int size();

    /**
     * Sets the value of a domain.
     * @param value a value
     */
    public abstract void setValue(int value);

    /** 
     * Sets the min of a domain.
     * @param min an int
     */
    public abstract void setMin(int  min);

    /** 
     * Sets the max of a domain.
     * @param max an int
     */
    public abstract void setMax(int  max);

    /**
     * Returns the min of a domain.
     * @return an int
     */
    public abstract int getMin(); 

    /**
     * Returns the max of a domain.
     * @return an int
     */
    public abstract int getMax();

    /** 
     * Sets the min and the max of a domain.
     * @param min an int
     * @param max an int
     * @since 2.3
     */
    public abstract void setMinMax(int min, int max);

    /** 
     * Sets the min to the max.
     * @since 3.0
     */
    public abstract void setMinToMax();

    /** 
     * Sets the max to the min.
     * @since 3.0
     */
    public abstract void setMaxToMin();

    /**
     * Returns an iterator over the domain.
     * @return an iterator
     */
    public abstract Iterator iterator();

    /**
     * Computes the intersection of this domain and the domain 
     * given as a parameter.
     * @param d a domain
     */
    public abstract void intersect(IntegerDomain d);

    /**
     * Return a boolean indicating whether the intersection of this domain and 
     * the domain given as a parameter is empty or not.
     * @param d a domain
     * @return a boolean
     */
    public abstract boolean intersects(IntegerDomain d);

    /**
     * Returns a boolean indicating whether the domain contains the parameter.
     * @param value an int
     * @return a boolean
     */
    public abstract boolean contains(int value);

    /**
     * Returns a boolean indicating whether the domain contains the parameter.
     * @param value an integer
     * @return a boolean
     */
    public abstract boolean contains(Integer value);

    /**
     * Returns a copy of the domain with a modified minimal value.
     * @param min the new minimal value
     * @return an integer domain
     */
    public abstract IntegerDomain copyWithMin(int min);

    /**
     * Returns a copy of the domain with a modified maximal value.
     * @param max the new maximal value
     * @return an integer domain
     */
    public abstract IntegerDomain copyWithMax(int max);

    /**
     * Converts the domain into an array of integer.
     * @return an array of int
     */
    public abstract int[] toArray();

    /**
     * Converts the domain into a set of Integer.
     * @return a sorted set
     */
    public abstract SortedSet toSet();

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** 
     * Sets the min and the max of a domain.
     * @param dom an integer domain
     * @since 3.0
     */
    public final void setMinMax(IntegerDomain dom) {
        setMinMax(dom.getMin(), dom.getMax());
    }
}
/*
 * $Log$
 * Revision 1.16  2004/11/23 13:04:54  yan
 * fixed API
 *
 * Revision 1.15  2004/09/16 13:49:58  yan
 * added a setMinMax method on IntegerDomain
 *
 * Revision 1.14  2004/07/13 23:14:36  yan
 * fixed style
 *
 * Revision 1.13  2004/07/01 09:43:22  yan
 * small refactoring
 *
 * Revision 1.12  2004/06/17 13:55:09  yan
 * VERSION
 *
 * Revision 1.11  2004/04/13 15:30:47  yan
 * remove methods are not available in MinMaxDomain anymore
 *
 * Revision 1.10  2004/04/09 14:44:35  yan
 * removal of events
 *
 * Revision 1.9  2003/10/02 19:32:04  yan
 * does not extends BaseDomain anymore
 *
 * Revision 1.8  2003/10/01 18:31:09  yan
 * made some methods final
 *
 * Revision 1.7  2003/03/07 13:54:00  yan
 * fixed style
 *
 * Revision 1.6  2003/02/05 13:59:21  yan
 * various algorithmic optimizations
 *
 * Revision 1.5  2002/06/16 17:41:27  mphilip
 * Modified jdoc.
 *
 * Revision 1.4  2002/06/16 13:56:07  yan
 * added javadoc
 *
 * Revision 1.3  2002/05/02 16:15:22  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
