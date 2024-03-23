package com.koalog.jcs.domain;

import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import com.koalog.jcs.choicepoint.StorableValue;

/**
 * A domain for set variables.
 *
 * <P>The elements of the set must implement <CODE>Comparable</CODE>.</P>
 *
 * <P>A set domain is a lattice defined by an minimal and a maximal value.
 * Each element of the lattice contains the minimal value 
 * and is contained in the maximal value.</P>
 *
 * <P>Exemple: The set domain <CODE>{1} .. {1, 2, 3}</CODE> is the lattice
 * containing the values <CODE>{1}, {1,2}, {1,3}, {1, 2, 3}</CODE>.</P>
 *
 * @see Comparable
 * @author Yan Georget
 */
public class SetDomain implements Domain {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The set of elements that are for sure in the set. */
    private SortedSet min;
    /** The set of elements that might be in the set. */
    private SortedSet max;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Main constructor.
     * @param min a collection
     * @param max a collection
     */
    public SetDomain(Collection min, Collection max) {
        this.min = new TreeSet(min);
        this.max = new TreeSet(max);
    }

    /**
     * Constructs an instantiated set domain where 
     * the value is the set given as a parameter. 
     * @param s the value
     */
    public SetDomain(Collection s) {
        this(s, s);
    }

    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------
    /**
     * Returns the min of a domain.
     * @return a set
     */
    public final SortedSet getMin() {
        return min;
    } 

    /**
     * Sets the min of the domain.
     * @param min a set
     */
    public final void setMin(SortedSet min) {
        this.min = min;
    } 

    /**
     * Returns the max of a domain.
     * @return a set
     */
    public final SortedSet getMax() {
        return max;
    }

    /**
     * Sets the max of the domain.
     * @param max a set
     */
    public final void setMax(SortedSet max) {
        this.max = max;
    } 

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.choicepoint.StorableValue */
    public final StorableValue copy() {
        return new SetDomain(min, max);
    }

    /**
     * Returns an element from the maximal set which is not is the minimal set.
     * @return an object that implements <CODE>Comparable</CODE>
     */
    public final Comparable getPossibleElement() {
        for (Iterator i = max.iterator(); i.hasNext();) {
            final Comparable o = (Comparable) i.next();
            if (!min.contains(o)) {
                return o;
            }
        }
        // should not happen
        return null;
    }

    /**
     * Returns true if the element is possible 
     * (included in the max but not in the min)
     * for the domain.
     * @param o an element
     * @return a boolean
     */
    public final boolean isPossibleElement(Comparable o) {
        return max.contains(o) && !min.contains(o);
    }

    /**
     * Returns a copy of the domain with a modified minimal value.
     * @param inc the collection to add to the minimal value
     * @return a set domain
     */
    public final SetDomain copyByIncreasingMin(Collection inc) {
        final SortedSet newMin = new TreeSet(min);
        newMin.addAll(inc);
        return new SetDomain(newMin, max);
    }

    /**
     * Returns a copy of the domain with a modified maximal value.
     * @param dec the collection to substract to the maximal value
     * @return a set domain
     */
    public final SetDomain copyByDecreasingMax(Collection dec) {
        final SortedSet newMax = new TreeSet(max);
        newMax.removeAll(dec);
        return new SetDomain(min, newMax);
    }

    /** @see com.koalog.jcs.domain.Domain */
    public final boolean isSingleton() {
        return min.size() == max.size();
    }

    /** @see com.koalog.jcs.domain.Domain */
    public final boolean isNotSingleton() {
        return min.size() < max.size();
    }

    /** @see com.koalog.jcs.domain.Domain */
    public final boolean isEmpty() {
        // an empty domain is encoded by the fact that 
        // the min is not contained in the max
        return !max.containsAll(min);
    }

    /** @see com.koalog.jcs.domain.Domain */
    public final boolean equals(Domain domain) {
        if (domain == null) {
            return false;
        }
        if (domain instanceof SetDomain) {
            return min.equals(((SetDomain) domain).getMin())
                && max.equals(((SetDomain) domain).getMax());
        } 
        throw new DomainComparisonNotSupportedException();
    }
    
    /**
     * Converts the SetDomain to a String.
     * @return a String
     */
    public String toString(){
        if (max.equals(min)) {
            return min.toString();
        } 
        if (max.containsAll(min)) {
            StringBuffer buf = new StringBuffer(min.toString());
            buf.append("..");
            buf.append(max);
            return buf.toString();
        } 
        return EMPTY;
    }
}
/*
 * $Log$
 * Revision 1.22  2004/11/29 09:44:24  yan
 * made some methods final
 *
 * Revision 1.21  2004/11/25 17:18:26  yan
 * added isNotSingleton
 *
 * Revision 1.20  2004/11/10 09:31:11  yan
 * made some local variables final
 *
 * Revision 1.19  2004/10/01 15:23:32  yan
 * using a constant for empty domains
 *
 * Revision 1.18  2004/06/14 15:55:52  yan
 * using storable references and values
 *
 * Revision 1.17  2004/05/06 09:54:06  yan
 * fixed toString method
 *
 * Revision 1.16  2003/10/02 19:32:04  yan
 * does not extends BaseDomain anymore
 *
 * Revision 1.15  2003/08/15 10:09:39  yan
 * fixed javadoc
 *
 * Revision 1.14  2003/08/04 17:27:44  yan
 * some methods are now final
 *
 * Revision 1.13  2003/07/31 21:13:57  yan
 * fixed style
 *
 * Revision 1.12  2003/07/31 21:12:18  yan
 * fixed style
 *
 * Revision 1.11  2003/07/31 19:38:38  yan
 * added back isPossibleElement
 *
 * Revision 1.10  2003/07/31 16:41:30  yan
 * SetDomain's sets are now sorted
 *
 * Revision 1.9  2003/07/21 12:20:51  yan
 * added isPossibleElement method
 *
 * Revision 1.8  2003/07/19 18:27:56  yan
 * small optim
 *
 * Revision 1.7  2003/06/19 16:50:29  yan
 * changed some signatures
 *
 * Revision 1.6  2003/06/19 09:49:47  yan
 * fixed style
 *
 * Revision 1.5  2003/06/18 15:19:15  yan
 * added useful signatures
 *
 * Revision 1.4  2003/06/18 11:10:56  yan
 * fixed constructors
 *
 * Revision 1.3  2003/06/18 11:03:04  yan
 * fixed style
 *
 * Revision 1.2  2003/06/17 17:10:07  yan
 * fixed internal iterator constructor
 *
 * Revision 1.1  2003/06/17 17:07:26  yan
 * initial revision
 *
 */
