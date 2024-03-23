package com.koalog.jcs.constraint.arithmetic;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.domain.IntegerDomain;
import com.koalog.jcs.domain.SparseDomain;
import com.koalog.jcs.variable.IntegerVariable;

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
 *    <TD align="right">true</TD>
 * </TR>
 * <TR>
 *    <TD><B>Complexity</B></TD>
 *    <TD align="right">dom(x)*dom(i)</TD>
 * </TR>
 * </TABLE>
 *
 * @author Yan Georget
 */
public class Element_3SPARSE extends Element_3 {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param x an integer variable with a sparse domain
     * @param i an integer variable with a sparse domain
     * @param l an array of integer variables with sparse domains
     */
    public Element_3SPARSE(IntegerVariable x, 
                           IntegerVariable i, 
                           IntegerVariable[] l) {
        super(x, i, l);
        complexity = ((IntegerDomain) x.getDomain()).size() 
            * ((IntegerDomain) i.getDomain()).size();
        idempotent = true;
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        x.addDomConstraint(this);
        i.addDomConstraint(this);
        for (int i=0; i<l.length; i++) {
            l[i].addDomConstraint(this);
        }
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
            final SparseDomain dom = 
                (SparseDomain) l[j.intValue()].getDomain();
            if (xDom.intersects(dom)) { 
                values.addAll(dom.getValues());
            } else {
                i.adjustDifferent(cp, cs, this, j, k); 
            }
        }
        x.adjustDomain(cp, cs, this, values);
        if (i.isInstantiated()) {
            l[i.getMin()].adjustDomain(cp, 
                                       cs,
                                       this,
                                       xDom.toSet());
            if (x.isInstantiated()) {
                entailed(cp);
            }
        }
    }
}
/*
 * $Log$
 * Revision 1.5  2005/07/18 15:32:55  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.4  2004/11/25 09:38:20  yan
 * fixed style
 *
 * Revision 1.3  2004/11/23 16:02:51  yan
 * optimized and cleaned a bit
 *
 * Revision 1.2  2004/11/23 13:00:37  yan
 * not using getValues anymore
 *
 * Revision 1.1  2004/11/22 10:28:33  yan
 * initial revision
 *
 * Revision 1.20  2004/11/10 10:19:27  yan
 * various optims
 *
 * Revision 1.19  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.18  2004/06/29 19:10:45  yan
 * using new adjustDifferent method
 *
 * Revision 1.17  2004/06/21 18:06:38  yan
 * various small fixes
 *
 * Revision 1.16  2004/06/15 19:20:38  yan
 * added back init method
 *
 * Revision 1.15  2004/05/05 11:46:48  yan
 * fixed style
 *
 * Revision 1.14  2004/04/09 14:42:50  yan
 * removal of events
 *
 * Revision 1.13  2004/03/14 16:42:59  yan
 * various simplifications
 *
 * Revision 1.12  2003/10/02 23:49:00  yan
 * introduced complexity computation
 *
 * Revision 1.11  2003/06/17 13:36:20  yan
 * added javadoc about algorithmic infos
 *
 * Revision 1.10  2003/04/01 13:58:01  yan
 * fixed complexity
 *
 * Revision 1.9  2003/03/27 11:01:48  yan
 * added events related method (updateConstraints)
 *
 * Revision 1.8  2003/03/20 19:17:03  yan
 * added entailment statement
 *
 * Revision 1.7  2003/03/07 10:58:18  yan
 * fixed complexity
 *
 * Revision 1.6  2003/02/11 10:30:42  yan
 * fixed style
 *
 * Revision 1.5  2003/02/06 16:48:18  yan
 * small optimization and indentation
 *
 * Revision 1.4  2003/02/05 13:58:40  yan
 * using adjustDomainDifferent
 *
 * Revision 1.3  2003/02/05 09:35:33  yan
 * using adjustMinMax
 *
 * Revision 1.2  2003/02/02 21:14:55  yan
 * modifiedVariables is now an instance variable
 *
 * Revision 1.1  2002/12/11 12:19:55  yan
 * initial revision
 *
 */
