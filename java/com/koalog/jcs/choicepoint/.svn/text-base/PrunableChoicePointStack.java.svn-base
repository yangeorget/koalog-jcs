package com.koalog.jcs.choicepoint;
  
import org.apache.log4j.Category;
import com.koalog.jcs.domain.IntegerDomain;
import com.koalog.jcs.variable.IntegerVariable;

/** 
 * A choice point stack that allows pruning of the choice points 
 * when optimizing an integer variable.
 *
 * @see com.koalog.jcs.variable.IntegerVariable
 * @since 2.1
 * @author Yan Georget
 */
public class PrunableChoicePointStack extends BaseChoicePointStack {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(PrunableChoicePointStack.class);
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------ 
    /**
     * Prunes the choice points.
     * @param v the integer variable being optimized
     * @param d the optimizing domain 
     */
    public void prune(IntegerVariable v, IntegerDomain d) {
        ChoicePoint cp = (ChoicePoint) stack.get(top);
        IntegerDomain dom = (IntegerDomain) cp.get(v);
        while (top > 0 && (dom == null || !dom.intersects(d))) {
            cp = (ChoicePoint) stack.get(--top);
            cp.merge((ChoicePoint) stack.get(top+1));
            dom = (IntegerDomain) cp.get(v);
        }
        for (int i=top+1; --i>=0;) {
            cp = (ChoicePoint) stack.get(i);
            dom = (IntegerDomain) cp.get(v);
            if (dom != null) {
                dom.intersect(d); 
                cp.storeChoiceVariable(v);
            }
        }
        if (cat.isDebugEnabled()) {
            cat.debug("after pruning " + this);
        }
    }
}
/*
 * $Log$
 * Revision 1.4  2004/07/12 12:08:17  yan
 * optimized by merging cp
 *
 * Revision 1.3  2004/07/10 21:52:53  yan
 * factorized some code
 *
 * Revision 1.2  2004/07/09 18:20:52  yan
 * fixed bug: optimized var is treated as a choice
 *
 * Revision 1.1  2004/06/14 15:56:23  yan
 * initial revision
 *
 */
