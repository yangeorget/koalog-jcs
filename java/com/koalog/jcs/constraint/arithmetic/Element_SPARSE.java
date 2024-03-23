package com.koalog.jcs.constraint.arithmetic;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.domain.SparseDomain;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;

/**
 * This constraint enforces that x is the ith element of array l.
 *
 * <P>Note: the variables must have sparse domains.
 *
 * <P>Algorithmic informations 
 * (see 
 * {@link com.koalog.jcs.constraint.Constraint Constraint}
 * for more details on complexity and idempotence):
 * <TABLE border="1">
 * <TR>
 *    <TD><B>Idempotent</B></TD>
 *    <TD align="right">yes</TD>
 * </TR>
 * <TR>
 *    <TD><B>Complexity</B></TD>
 *    <TD align="right">dom(i)</TD>
 * </TR>
 * </TABLE>
 *
 * @author Yan Georget
 */
public class Element_SPARSE extends Element {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param x an integer variable with a sparse domain
     * @param i an integer variable with a sparse domain
     * @param l an array of integers (not sorted, can have duplicates)
     */
    public Element_SPARSE(IntegerVariable x, IntegerVariable i, int[] l) {
        super(x, i, l);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        x.addDomConstraint(this);
        i.addDomConstraint(this);
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        final SortedSet values = new TreeSet(); // fresh set
        final SparseDomain xDom = (SparseDomain) x.getDomain();
        for (Iterator k = ((SparseDomain) i.getDomain()).iterator();
             k.hasNext();) {
            final Integer j = (Integer) k.next();
            final Integer lj = new Integer(l[j.intValue()]);
            if (xDom.contains(lj)) {
                values.add(lj);
            } else { // v is not a feasible value for x
                i.adjustDifferent(cp, cs, this, j, k);
            }
        }
        x.adjustDomain(cp, cs, this, values);
        if (x.isInstantiated()) {
            entailed(cp);
        }
    }
}
/*
 * $Log$
 * Revision 1.18  2005/07/18 15:32:55  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.17  2005/07/18 09:48:30  yan
 * fixed jdoc
 *
 * Revision 1.16  2004/11/23 16:02:51  yan
 * optimized and cleaned a bit
 *
 * Revision 1.15  2004/11/10 10:19:27  yan
 * various optims
 *
 * Revision 1.14  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.13  2004/07/13 23:14:55  yan
 * fixed style
 *
 * Revision 1.12  2004/07/01 09:44:05  yan
 * optimized adjustDomain
 *
 * Revision 1.11  2004/06/29 19:10:51  yan
 * using new adjustDifferent method
 *
 * Revision 1.10  2004/06/27 13:15:42  yan
 * optimized
 *
 * Revision 1.9  2004/06/25 18:55:31  yan
 * optimized
 *
 * Revision 1.8  2004/06/21 18:25:16  yan
 * fixed style
 *
 * Revision 1.7  2004/06/21 18:06:38  yan
 * various small fixes
 *
 * Revision 1.6  2004/06/15 19:20:38  yan
 * added back init method
 *
 * Revision 1.5  2004/05/05 11:46:48  yan
 * fixed style
 *
 * Revision 1.4  2004/04/09 14:42:50  yan
 * removal of events
 *
 * Revision 1.3  2004/03/14 16:42:59  yan
 * various simplifications
 *
 * Revision 1.2  2003/10/02 23:50:09  yan
 * introduced complexity computation
 *
 * Revision 1.1  2003/10/01 18:26:20  yan
 * initial revision
 *
 */
