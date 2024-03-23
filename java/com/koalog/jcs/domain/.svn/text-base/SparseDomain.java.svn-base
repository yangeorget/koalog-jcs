package com.koalog.jcs.domain;

import java.util.TreeSet;
import java.util.SortedSet;
import java.util.Iterator;
import java.util.Collection;
import com.koalog.jcs.choicepoint.StorableValue;

/**
 * This class represents sparse domains.
 *
 * <P>A sparse domain is internally represented by a set of possible values.
 * This representation is precise but does not have the efficiency of the 
 * intervals used by the <CODE>MinMaxDomain</CODE> objects.</P>
 *
 * @author Yan Georget
 * @see MinMaxDomain
 */
public class SparseDomain extends IntegerDomain {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The set of values in the sparse domain.*/
    private SortedSet values;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Main constructor.
     * @param vals an array of int
     */
    public SparseDomain(int[] vals) {
        values = new TreeSet();
        for (int i=vals.length; --i>=0;) {
            add(vals[i]);
        }
    }

    /**
     * Another constructor.
     * @param vals a collection
     */
    public SparseDomain(Collection vals) {
        values = new TreeSet(vals);
    }

    /**
     * Constructs a sparse domain corresponding to a given interval.
     * @param min the min of the interval
     * @param max the max of the interval
     */
    public SparseDomain(int min, int max) {
        values = new TreeSet();
        for (int i=min; i<=max; i++) {
            add(i);
        }
    }

    /**
     * Constructs a sparse domain corresponding to a given value.
     * @param value a value
     * @since 2.4
     */
    public SparseDomain(int value) {
        values = new TreeSet();
        add(value);
    }

    /**
     * Copy constructor.
     * @param domain a sparse domain
     */
    public SparseDomain(SparseDomain domain) {
        this(domain.getValues());
    }

    /**
     * Another constructor.
     * @param domain a min-max domain
     */
     public SparseDomain(MinMaxDomain domain) {
        this(domain.getMin(), domain.getMax());
    }

    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------
    /** 
     * Returns the set of values in the sparse domain.
     * @return a set
     */
    public final SortedSet getValues() {
        return values;
    }

    /**
     * Sets the values of the sparse domain.
     * @param values a sorted set
     */
    public final void setValues(SortedSet values) {
        this.values = values;
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.choicepoint.StorableValue */
    public final StorableValue copy() {
        return new SparseDomain(values);
        // faster than the following ... (which could be improved)
        /*
        try {
            SparseDomain dom = (SparseDomain) clone();
            dom.values = (SortedSet) ((TreeSet)values).clone();
            return dom;
        } catch (Exception e) {
            return null;
        }
        */
    }

    /** @see com.koalog.jcs.domain.IntegerDomain */
    public final boolean contains(int value) {
        return contains(new Integer(value));
    }

    /** @see com.koalog.jcs.domain.IntegerDomain */
    public final boolean contains(Integer value) {
        return values.contains(value);
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
        return !values.subSet(new Integer(d.getMin()), 
                              new Integer(d.getMax()+1)).isEmpty();
    }

    /**
     * Return a boolean indicating whether the intersection of this domain and 
     * the sparse domain given as a parameter is empty or not.
     * @param d a sparse domain
     * @return a boolean
     */
    public final boolean intersects(SparseDomain d) {
        // faster than:
        /*
          SortedSet tmp = new TreeSet(values);
          tmp.retainAll(d.values);
          return !tmp.isEmpty();
        */
        if (size() >= d.size()) {
            for (Iterator i = d.iterator(); i.hasNext();) {
                if (values.contains(i.next())) {
                    return true;
                }
            }
            return false;
        } else {
            return d.intersects(this);
        }
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
        // forced to create a new TreeSet
        values = new TreeSet(values.subSet(new Integer(d.getMin()), 
                                           new Integer(d.getMax()+1)));
    }

    /**
     * Computes the intersection of this domain and the sparse domain 
     * given as a parameter.
     * @param d a sparse domain
     */
    public final void intersect(SparseDomain d) {
        values.retainAll(d.values);
    }

    /** @see com.koalog.jcs.domain.Domain */
    public final boolean isSingleton() {
        return size() == 1;
    }

    /** @see com.koalog.jcs.domain.Domain */
    public final boolean isNotSingleton() {
        return size() > 1;
    }

    /** @see com.koalog.jcs.domain.IntegerDomain */
    public final int getMin() {
        return ((Integer) values.first()).intValue();
    }
  
    /** @see com.koalog.jcs.domain.IntegerDomain */
    public final int getMax() {
        return ((Integer) values.last()).intValue();
    }
  
  
    /** @see com.koalog.jcs.domain.IntegerDomain */
    public final void setValue(int value) {
        values.clear();
        values.add(new Integer(value));
    }

    /** @see com.koalog.jcs.domain.IntegerDomain */
    public final void setMin(int min) {
        // forced to create a new TreeSet
        values = new TreeSet(values.tailSet(new Integer(min)));
    }
  
    /** @see com.koalog.jcs.domain.IntegerDomain */
    public final void setMax(int max) {
        // forced to create a new TreeSet
        values = new TreeSet(values.headSet(new Integer(max+1)));
    }

    /** @see com.koalog.jcs.domain.IntegerDomain */
    public final void setMinMax(int min, int max) {
        // forced to create a new TreeSet
        values = new TreeSet(values.subSet(new Integer(min), 
                                           new Integer(max+1)));
    }

    /** @see com.koalog.jcs.domain.IntegerDomain */
    public final void setMinToMax() {
        final Object last = values.last();
        values.clear();
        values.add(last);
    }

    /** @see com.koalog.jcs.domain.IntegerDomain */
    public final void setMaxToMin() {
        final Object first = values.first();
        values.clear();
        values.add(first);
    }

    /** @see com.koalog.jcs.domain.IntegerDomain */
    public final IntegerDomain copyWithMax(int max) {
        return new SparseDomain(values.headSet(new Integer(max+1)));
    }

    /** @see com.koalog.jcs.domain.IntegerDomain */
    public final IntegerDomain copyWithMin(int min) {
        return new SparseDomain(values.tailSet(new Integer(min)));
    }

    /** @see com.koalog.jcs.domain.Domain */
    public final boolean isEmpty() {
        return values.isEmpty();
    }

    /** @see com.koalog.jcs.domain.Domain */
    public final boolean equals(Domain domain) {
        if (domain == null) {
            return false;
        }
        if (domain instanceof SparseDomain) {
            return equals((SparseDomain) domain);
        } 
        if (domain instanceof MinMaxDomain) {
            return equals((MinMaxDomain) domain);
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
        return size() == domain.size()
            && getMin() == domain.getMin()
            && getMax() == domain.getMax();
    }
    
    /** 
     * Tests whether the domain is equals (has the same possible values) 
     * as the sparse domain given as a parameter.
     * @param domain a sparse domain
     * @return a boolean
     */
    public final boolean equals(SparseDomain domain) {
        return values.equals(domain.values);
    }

    /**
     * Converts the SparseDomain to a String.
     * @return a String
     */
    public String toString(){
        if (isEmpty()) {
            return EMPTY;
        } 
        StringBuffer b = new StringBuffer();
        for (Iterator i = values.iterator(); i.hasNext();) {
            b.append(i.next());
            b.append(" ");
        }
        return b.toString();
    }

    /** @see com.koalog.jcs.domain.IntegerDomain */
    public final int size() {
        return values.size();
    }

    /**
     * Adds a value to the domain.
     * @param value an integer
     */
    public final void add(int value) {
        values.add(new Integer(value));
    }

    /** 
     * Removes a value from the domain.
     * @param value a value
     */
    public final void remove(int value) {
        values.remove(new Integer(value));
    }

    /** 
     * Removes the values from a domain.
     * @param d a domain
     */
    public final void remove(IntegerDomain d) {
        if (d instanceof MinMaxDomain) {
            remove((MinMaxDomain) d);
        } else { // sparse domain
            remove((SparseDomain) d);
        }
    }

    /**
     * Removes from the domain all the values of the min-max domain 
     * given as a parameter.
     * @param d a min-max domain
     */
    public final void remove(MinMaxDomain d) {
        for (int i=d.getMin(); i<=d.getMax(); i++) {
            remove(i);
        }
    }

    /**
     * Removes from the domain all the values of the sparse domain 
     * given as a parameter.
     * @param d a sparse domain
     */
    public final void remove(SparseDomain d) {
        values.removeAll(d.getValues());
    }

    /** @see com.koalog.jcs.domain.IntegerDomain */
    public final Iterator iterator() {
        return values.iterator();
    }

    /** @see com.koalog.jcs.domain.IntegerDomain */
    public int[] toArray() {
        int[] vals = new int[size()];
        int index = 0;
        for (Iterator i = iterator(); i.hasNext();) {
            vals[index++] = ((Integer) i.next()).intValue();
        }
        return vals;
    }

    /** @see com.koalog.jcs.domain.IntegerDomain */
    public SortedSet toSet() {
        return new TreeSet(values);
    }
}
/*
 * $Log$
 * Revision 1.23  2004/11/25 17:18:26  yan
 * added isNotSingleton
 *
 * Revision 1.22  2004/11/23 13:21:35  yan
 * clean API
 *
 * Revision 1.21  2004/11/22 12:20:22  yan
 * factorized some code in constructors
 *
 * Revision 1.20  2004/11/18 17:47:13  yan
 * added a constructor
 *
 * Revision 1.19  2004/10/01 15:23:32  yan
 * using a constant for empty domains
 *
 * Revision 1.18  2004/09/16 13:49:58  yan
 * added a setMinMax method on IntegerDomain
 *
 * Revision 1.17  2004/07/01 09:43:22  yan
 * small refactoring
 *
 * Revision 1.16  2004/06/29 19:10:12  yan
 * added new contains method
 *
 * Revision 1.15  2004/06/27 14:08:44  yan
 * small fixes
 *
 * Revision 1.14  2004/06/14 15:55:52  yan
 * using storable references and values
 *
 * Revision 1.13  2004/04/13 15:30:47  yan
 * remove methods are not available in MinMaxDomain anymore
 *
 * Revision 1.12  2004/04/09 14:44:35  yan
 * removal of events
 *
 * Revision 1.11  2004/03/14 16:44:07  yan
 * removed unused methods
 *
 * Revision 1.10  2003/10/01 18:31:09  yan
 * made some methods final
 *
 * Revision 1.9  2003/03/07 13:54:00  yan
 * fixed style
 *
 * Revision 1.8  2003/02/05 13:59:21  yan
 * various algorithmic optimizations
 *
 * Revision 1.7  2003/01/13 11:11:31  yan
 * added methods for addition, multiplication
 *
 * Revision 1.6  2002/09/16 08:04:05  yan
 * *** empty log message ***
 *
 * Revision 1.5  2002/06/16 19:27:38  mphilip
 * Modified jdoc.
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
