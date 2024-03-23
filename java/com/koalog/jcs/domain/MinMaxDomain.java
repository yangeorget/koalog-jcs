package com.koalog.jcs.domain;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import com.koalog.jcs.choicepoint.StorableValue;

/**
 * This class represents intervals.
 * 
 * <P>An interval is internally represented by its minimal and maximal values.
 * This representation is efficient but does not have the precision of the sets 
 * used by the <CODE>SparseDomain</CODE> objects.</P>
 *
 * @author Yan Georget
 * @see SparseDomain
 */
public class MinMaxDomain extends IntegerDomain {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The min of the domain.*/
    private int min;
    /** The max of the domain.*/
    private int max;
  
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Constructs a MinMaxDomain which is an interval.
     * @param min the min of the interval
     * @param max the max of the interval
     */
    public MinMaxDomain(int min, int max) {
        this.min = min;
        this.max = max;
    }

    /**
     * Constructs a MinMaxDomain which is a singleton
     * @param value the value of the singleton
     */
    public MinMaxDomain(int value) {
        min = max = value;
    }

    /**
     * Constructs a MinMaxDomain from an IntegerDomain.
     * @param domain an IntegerDomain
     */
    public MinMaxDomain(IntegerDomain domain) {
        this(domain.getMin(), domain.getMax());
    }
  
    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------  
    /** @see com.koalog.jcs.domain.IntegerDomain */
    public final int getMin() {
        return min;
    }
  
    /** @see com.koalog.jcs.domain.IntegerDomain */
    public final void setMin(int min) {
        this.min = min;
    }

    /** @see com.koalog.jcs.domain.IntegerDomain */  
    public final void setMax(int max) {
        this.max = max;
    }

    /** @see com.koalog.jcs.domain.IntegerDomain */
    public final int getMax() {
        return max;
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.choicepoint.StorableValue */
    public final StorableValue copy() {
        return new MinMaxDomain(min, max);
    }

    /** @see com.koalog.jcs.domain.IntegerDomain */
    public final boolean contains(int value) {
        return min<=value && value<=max;
    }

    /** @see com.koalog.jcs.domain.IntegerDomain */
    public final boolean contains(Integer value) {
        return contains(value.intValue());
    }

    /** @see com.koalog.jcs.domain.IntegerDomain */
    public final void intersect(IntegerDomain d) {
        if (d instanceof MinMaxDomain) {
            intersect((MinMaxDomain) d);
        } else { // sparse domain
            intersect((SparseDomain) d);
        }
    }

    /**
     * Computes the intersection of this domain and the min-max domain 
     * given as a parameter.
     * @param d a min-max domain
     */
    public final void intersect(MinMaxDomain d) {
        if (d.min > min) {
            min = d.min;
        }
        if (d.max < max) {
            max = d.max;
        }
    }

    /**
     * Computes the intersection of this domain and the sparse domain 
     * given as a parameter.
     * @param d a sparse domain
     */
    public final void intersect(SparseDomain d) {
        final SortedSet vals = d.getValues().subSet(new Integer(min), 
                                                    new Integer(max+1));
        min = ((Integer) vals.first()).intValue();
        max = ((Integer) vals.last()).intValue(); 
    }

    /** @see com.koalog.jcs.domain.IntegerDomain */
    public final boolean intersects(IntegerDomain d) {
        if (d instanceof MinMaxDomain) {
            return intersects((MinMaxDomain) d);
        } else { // sparse domain
            return intersects((SparseDomain) d);
        }
    }

    /**
     * Return a boolean indicating whether the intersection of this domain and 
     * the min-max domain given as a parameter is empty or not.
     * @param d a min-max domain
     * @return a boolean
     */
    public final boolean intersects(MinMaxDomain d) {
        return min<=d.max && max>=d.min;
    }

    /**
     * Return a boolean indicating whether the intersection of this domain and 
     * the sparse domain given as a parameter is empty or not.
     * @param d a sparse domain
     * @return a boolean
     */
     public final boolean intersects(SparseDomain d) {
        return d.intersects(this);
    }

    /** @see com.koalog.jcs.domain.Domain */
    public final boolean isSingleton() {
        return min == max;
    }

    /** @see com.koalog.jcs.domain.Domain */
    public final boolean isNotSingleton() {
        return min < max;
    }

    /** @see com.koalog.jcs.domain.IntegerDomain */
    public Iterator iterator() {
        return new Iterator() {
                int index = min;
                
                public boolean hasNext() {
                    return index <= max;
                }
                
                public Object next() {
                    return new Integer(index++);
                }
                
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
    }

    /** @see com.koalog.jcs.domain.IntegerDomain */
    public final IntegerDomain copyWithMin(int min) {
        return new MinMaxDomain(min, max);
    }

    /** @see com.koalog.jcs.domain.IntegerDomain */
    public final IntegerDomain copyWithMax(int max) {
        return new MinMaxDomain(min, max);
    }

    /** @see com.koalog.jcs.domain.IntegerDomain */
    public final void setValue(int value) {
        min = max = value;
    }

    /** @see com.koalog.jcs.domain.IntegerDomain */
    public final void setMinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }

    /** @see com.koalog.jcs.domain.IntegerDomain */
    public final void setMinToMax() {
        min = max;
    }

    /** @see com.koalog.jcs.domain.IntegerDomain */
    public final void setMaxToMin() {
        max = min;
    }

    /** @see com.koalog.jcs.domain.Domain */
    public final boolean isEmpty() {
        // an empty domain is encoded by the fact that 
        // the min is strictly greater than the max
        return min > max;
    }

    /** @see com.koalog.jcs.domain.Domain */
    public final boolean equals(Domain domain) {
        if (domain == null) {
            return false;
        }
        if (domain instanceof MinMaxDomain) {
            return equals((MinMaxDomain) domain);
        } 
        if (domain instanceof SparseDomain) {
           return equals((SparseDomain) domain);
        } 
        throw new DomainComparisonNotSupportedException();
    }
    
    /** 
     * Tests whether the domain is equals (has the same possible values) 
     * as the min-max domain given as a parameter.
     * @param domain a min-max domain
     * @return a boolean
     */
    public final boolean equals(MinMaxDomain domain) {
        return min == domain.min && max == domain.max;
    }

    /** 
     * Tests whether the domain is equals (has the same possible values) 
     * as the sparse domain given as a parameter.
     * @param domain a sparse domain
     * @return a boolean
     */
    public final boolean equals(SparseDomain domain) {
        return domain.equals(this);
    }

    /**
     * Converts the MinMaxDomain to a String.
     * @return a String
     */
    public String toString(){
        if (min == max) {
            return new Integer(min).toString();
        } 
        if (min < max) {
            StringBuffer buf = new StringBuffer();
            buf.append(min);
            buf.append("..");
            buf.append(max);
            return buf.toString();
        } 
        return EMPTY;
    }

    /** @see com.koalog.jcs.domain.IntegerDomain */
    public final int size() {
        return max-min+1;
    }

    /** @see com.koalog.jcs.domain.IntegerDomain */
    public int[] toArray() {
        int[] vals = new int[size()];
        for (int i=min; i<=max; i++) {
            vals[i-min] = i;
        }
        return vals;
    }

    /** @see com.koalog.jcs.domain.IntegerDomain */
    public SortedSet toSet() {
        final SortedSet s = new TreeSet();
        for (int i=min; i<=max; i++) {
            s.add(new Integer(i));
        }
        return s;
    }

    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    /** 
     * Converts an array of arrays of integers into 
     * an array of arrays of domains.
     * @param a the array of array of integers
     * @return an array of array of domains
     */
    public static MinMaxDomain[][] toDomains(int[][] a) { 
        final MinMaxDomain[][] b = new MinMaxDomain[a.length][];
        for (int i=0; i<a.length; i++) {
            b[i] = toDomains(a[i]);
        }
        return b;
    }

    /** 
     * Converts an array of integers into an array of domains.
     * @param a the array of integers
     * @return an array of domains
     */
    private static MinMaxDomain[] toDomains(int[] a) {
        final MinMaxDomain[] b = new MinMaxDomain[a.length];
        for (int i=0; i<a.length; i++) {
            b[i] = new MinMaxDomain(a[i]);
        }
        return b;
    }
}
/*
 * $Log$
 * Revision 1.22  2004/11/29 09:44:43  yan
 * made some methods final
 *
 * Revision 1.21  2004/11/25 17:18:26  yan
 * added isNotSingleton
 *
 * Revision 1.20  2004/11/23 13:21:01  yan
 * clean API
 *
 * Revision 1.19  2004/11/10 09:31:11  yan
 * made some local variables final
 *
 * Revision 1.18  2004/10/01 15:23:32  yan
 * using a constant for empty domains
 *
 * Revision 1.17  2004/09/16 13:49:58  yan
 * added a setMinMax method on IntegerDomain
 *
 * Revision 1.16  2004/07/01 09:43:22  yan
 * small refactoring
 *
 * Revision 1.15  2004/06/24 14:38:18  yan
 * small optims
 *
 * Revision 1.14  2004/06/21 18:20:05  yan
 * various fixes
 *
 * Revision 1.13  2004/06/14 15:55:52  yan
 * using storable references and values
 *
 * Revision 1.12  2004/05/06 09:54:05  yan
 * fixed toString method
 *
 * Revision 1.11  2004/05/05 11:44:13  yan
 * fixed style
 *
 * Revision 1.10  2004/04/13 15:30:47  yan
 * remove methods are not available in MinMaxDomain anymore
 *
 * Revision 1.9  2003/10/01 18:31:09  yan
 * made some methods final
 *
 * Revision 1.8  2003/04/21 10:02:55  yan
 * increased coverage
 *
 * Revision 1.7  2003/03/27 11:08:26  yan
 * allowing values removal
 *
 * Revision 1.6  2003/03/07 13:54:00  yan
 * fixed style
 *
 * Revision 1.5  2003/02/05 13:59:21  yan
 * various algorithmic optimizations
 *
 * Revision 1.4  2002/05/22 09:27:25  yan
 * added javadoc
 *
 * Revision 1.3  2002/05/02 16:15:22  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
