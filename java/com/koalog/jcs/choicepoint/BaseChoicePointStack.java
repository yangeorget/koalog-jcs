package com.koalog.jcs.choicepoint;
  
import java.util.ArrayList;
import org.apache.log4j.Category;
import com.koalog.jcs.domain.Domain;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.variable.Variable;

/** 
 * This class implements a choice point stack.
 *
 * @author Yan Georget
 */
public class BaseChoicePointStack implements ChoicePointStack {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(BaseChoicePointStack.class);
    
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------ 
    /** The choice points created during the resolution. */
    ArrayList stack;
    /** The current top of the stack. */
    int top;
    /** The current date. */
    private int date;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------ 
    /**
     * Sole constructor.
     */
    public BaseChoicePointStack() {
        stack = new ArrayList(DEFAULT_CAPACITY);
        stack.add(new BaseChoicePoint());
        // top is equal to 0 at the beginning
        // date is equal to 0 at the beginning
    }

    //------------------------------------------------------------------------
    // ACCESSORS 
    //------------------------------------------------------------------------ 
    /** @see com.koalog.jcs.choicepoint.ChoicePointStack */
    public final int getDate() {
        return date;
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------ 
    /** @see com.koalog.jcs.choicepoint.ChoicePointStack **/
    public void memorize(Variable v, Domain d) {
        final ChoicePoint last = (ChoicePoint) stack.get(top);
        last.storeChoiceVariable(v);
        last.put(v, d); // d is a fresh domain
        ((ChoicePoint) stack.get(top-1)).memorize(v);
        //cat.info(toString());
    }


    /** @see com.koalog.jcs.choicepoint.ChoicePointStack **/
    public void memorize(StorableReference ref) {
        ((ChoicePoint) stack.get(top)).memorize(ref);
        //cat.info(toString());
    }

    /** 
     * Converts a choice point stack to a string.
     * @return a string
     */
    public String toString() {
        StringBuffer b = new StringBuffer();
        for (int i=0; i<=top; i++) {
            b.append("\nCP:");
            b.append((BaseChoicePoint) stack.get(i));
        }
        return b.toString();
    }

    /** @see com.koalog.jcs.choicepoint.ChoicePointStack **/
    public final boolean isEmpty() {
        return top == 0; // there is a dummy choice point
    }
 
   /** @see com.koalog.jcs.choicepoint.ChoicePointStack **/
    public final int maxDepth() {
        return stack.size()-1;
    }
 
    /** @see com.koalog.jcs.choicepoint.ChoicePointStack **/
    public void reset(ConstraintScheduler cs) {
        while (top >= 0) {
            pop(cs); // we don't care about the modified variables
        }
        push();
    }

    /** @see com.koalog.jcs.choicepoint.ChoicePointStack **/
    public void pop(ConstraintScheduler cs) {
        date++; // the date is only increased here and never decreased
        ((ChoicePoint) stack.get(top--)).restore(cs, this);
    }

    /** @see com.koalog.jcs.choicepoint.ChoicePointStack **/
    public void push() {
        if (stack.size() == ++top) {
            stack.add(new BaseChoicePoint());
        } else { // reusing choice point
            ((ChoicePoint) stack.get(top)).clear();
        }
    }
}
/*
 * $Log$
 * Revision 1.22  2004/11/10 09:31:11  yan
 * made some local variables final
 *
 * Revision 1.21  2004/09/16 12:29:20  yan
 * added cp stack as an argument to the restore method
 *
 * Revision 1.20  2004/06/29 16:08:43  yan
 * avoiding use of stacks
 *
 * Revision 1.19  2004/06/24 12:04:51  yan
 * various fixes
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
 * Revision 1.14  2003/09/21 18:11:56  yan
 * removed clear method
 *
 * Revision 1.13  2003/08/04 17:28:33  yan
 * fixed style
 *
 * Revision 1.12  2003/08/03 16:23:01  yan
 * optimized prune method
 *
 * Revision 1.11  2003/08/02 15:19:58  yan
 * added clear method
 *
 * Revision 1.10  2003/05/05 12:00:49  yan
 * fixed bug in reset
 *
 * Revision 1.9  2003/03/20 18:05:57  yan
 * added entailment check
 *
 * Revision 1.8  2003/02/11 10:23:30  yan
 * fixed style
 *
 * Revision 1.7  2003/02/03 12:03:04  yan
 * using choice point kind of pool
 *
 * Revision 1.6  2002/10/25 09:03:14  yan
 * cleaned category
 *
 * Revision 1.5  2002/06/16 15:57:41  mphilip
 * Modified jdoc.
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
