package com.koalog.jcs.constraint.set;

import java.util.Collection;
import java.util.Iterator;
import org.apache.log4j.Category;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.variable.SetVariable;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.arithmetic.Exactly;

/**
 * A partition constraint.
 *
 * @since 2.1
 * @author Yan Georget
 */
public class Partition extends BaseProblem {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(Partition.class);

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param vars an array of set variables
     * @param set a set as a collection
     */
    public Partition(SetVariable[] vars, Collection set) {
        super("partition");
        /*
          for (int j=0; j<vars.length-1; j++) {
             for (int k=j+1; k<vars.length; k++) {
                internalAdd(new EmptyIntersection(vars[j], vars[k]));
             }
          }
        */
        Iterator l = set.iterator();
        while (l.hasNext()) {
            Integer s = (Integer) l.next();
            BooleanVariable[] b = new BooleanVariable[vars.length];
            for (int j=0; j<vars.length; j++) {
                b[j] = new BooleanVariable();
                add(new Contains(b[j], vars[j], s));
            }
            add(new Exactly(1, b, 1));
        }
        setVariables(vars);
    }
}
/*
 * $Log$
 * Revision 1.5  2005/07/21 07:22:50  yan
 * fixed complexity in jdoc
 *
 * Revision 1.4  2004/09/17 10:05:56  yan
 * changed internalAdd into add
 *
 * Revision 1.3  2004/06/25 15:01:00  yan
 * using internalAdd
 *
 * Revision 1.2  2004/06/03 16:49:43  yan
 * added @since tag
 *
 * Revision 1.1  2004/05/17 13:52:49  yan
 * initial revision
 *
 */
