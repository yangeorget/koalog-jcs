package com.koalog.jcs.choicepoint;

import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import org.apache.log4j.Category;
import com.koalog.jcs.variable.Variable;
import com.koalog.jcs.constraint.ConstraintScheduler;

/**
 * This class is the reification of a choice point.
 * 
 * <P>A <CODE>BaseChoicePoint</CODE> 
 * records the values of the storable references
 * in a <CODE>Map</CODE>.</P>
 *
 * @see StorableValue
 * @see StorableReference
 * @author Yan Georget
 */
public class BaseChoicePoint implements ChoicePoint {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(BaseChoicePoint.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** A map associating a storable reference to a value.*/
    Map map;
    /** The set of variables on which choices have been made, 
        usually a singleton. */
    Set choices;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------  
    /** 
     * Sole constructor. 
     */
    public BaseChoicePoint() {
        map = new HashMap(DEFAULT_CAPACITY);
        choices = new HashSet(1);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------  
    /** @see com.koalog.jcs.choicepoint.ChoicePoint **/
    public void storeChoiceVariable(Variable v) {
        choices.add(v);
    }

    /** @see com.koalog.jcs.choicepoint.ChoicePoint **/
    public void clear() {
        map.clear();
        choices.clear();
    }
    
    /** @see com.koalog.jcs.choicepoint.ChoicePoint **/
    public void memorize(StorableReference ref) {
        if (!map.containsKey(ref)) {
            map.put(ref, ref.getStorableValue().copy());
        }
    }

    /** @see com.koalog.jcs.choicepoint.ChoicePoint **/
    public final void put(StorableReference ref, StorableValue val) {
        map.put(ref, val);
    }

    /** @see com.koalog.jcs.choicepoint.ChoicePoint **/
    public final StorableValue get(StorableReference ref) {
        return (StorableValue) map.get(ref);
    }

    /** @see com.koalog.jcs.choicepoint.ChoicePoint **/
    public void restore(ConstraintScheduler cs, ChoicePointStack cp) {
        for (Iterator i = map.keySet().iterator(); i.hasNext();) {
            final StorableReference ref = (StorableReference) i.next();
            ref.setStorableValue((StorableValue) map.get(ref));
        }
        // now updates the scheduler with the choice variables
        for (Iterator i = choices.iterator(); i.hasNext();) {
            final Variable var = (Variable) i.next();
            var.setFullEventMask();
            cs.update(cp, var);
        }
    }

    /** @see com.koalog.jcs.choicepoint.ChoicePoint **/
    public void merge(ChoicePoint cp) {
        final Set references = getStorableReferences();
        for (Iterator i = cp.getStorableReferences().iterator(); 
             i.hasNext();) {
            final StorableReference ref = (StorableReference) i.next();
            if (!references.contains(ref)) {
                // we won't store the value otherwise since it is younger
                map.put(ref, (StorableValue) cp.get(ref));
            }
            // if it was a choice then store it as a choice 
            if (cp.getChoiceVariables().contains(ref)) {
                choices.add(ref);
            }
        }
    }

    /** @see com.koalog.jcs.choicepoint.ChoicePoint **/
    public final Set getStorableReferences() {
        return map.keySet();
    }

    /** @see com.koalog.jcs.choicepoint.ChoicePoint **/
    public final Set getChoiceVariables() {
        return choices;
    }

    /**
     * Converts a choice point to a string.
     * @return a string
     */
    public String toString() {
        StringBuffer b = new StringBuffer();
        Iterator i = map.keySet().iterator();
        while (i.hasNext()) {
            StorableReference ref = (StorableReference) i.next();
            b.append(ref);
            b.append("=");
            b.append(get(ref));
            b.append(" ");
        }
        return b.toString().trim();
    }
}
/*
 * $Log$
 * Revision 1.23  2004/11/10 09:31:11  yan
 * made some local variables final
 *
 * Revision 1.22  2004/09/16 12:28:57  yan
 * added cp stack as an argument to the restore method
 *
 * Revision 1.21  2004/07/12 12:07:51  yan
 * added methods for merging choice points
 *
 * Revision 1.20  2004/06/24 12:07:59  yan
 * made some methods final
 *
 * Revision 1.19  2004/06/24 12:03:17  yan
 * various optimizations
 *
 * Revision 1.18  2004/06/14 15:55:51  yan
 * using storable references and values
 *
 * Revision 1.17  2004/04/13 16:35:29  yan
 * fixed style
 *
 * Revision 1.16  2004/04/13 15:28:48  yan
 * optimized updates of constraint scheduler in choice point stack
 *
 * Revision 1.15  2004/04/09 14:41:57  yan
 * removal of events
 *
 * Revision 1.14  2004/03/14 16:44:07  yan
 * removed unused methods
 *
 * Revision 1.13  2003/07/12 17:56:57  yan
 * added commented debug statements
 *
 * Revision 1.12  2003/03/27 11:07:27  yan
 * using restoreDomain
 *
 * Revision 1.11  2003/03/09 18:51:19  yan
 * fixed style
 *
 * Revision 1.10  2003/03/09 16:57:16  yan
 * optimized choice point (no restoreDomain method anymore)
 *
 * Revision 1.9  2003/02/11 10:23:30  yan
 * fixed style
 *
 * Revision 1.8  2003/02/03 12:03:04  yan
 * using choice point kind of pool
 *
 * Revision 1.7  2003/02/03 09:10:49  yan
 * removed unecessary methods
 *
 * Revision 1.6  2002/06/16 15:56:05  mphilip
 * Modified jdoc.
 *
 * Revision 1.5  2002/06/16 13:45:33  yan
 * added javadoc
 *
 * Revision 1.4  2002/05/22 09:27:24  yan
 * added javadoc
 *
 * Revision 1.3  2002/05/02 16:16:12  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
