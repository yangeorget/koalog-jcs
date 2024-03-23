package com.koalog.jcs.constraint.arithmetic;

import java.util.Iterator;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.domain.IntegerDomain;
import com.koalog.jcs.domain.SparseDomain;
import com.koalog.jcs.constraint.BaseConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;

/**
 * This constraint enforces that x is the ith element of array l.
 *
 * <P>Note: the index must have a sparse domain.
 *
 * <P>Algorithmic informations 
 * (see 
 * {@link com.koalog.jcs.constraint.Constraint Constraint}
 * for more details on complexity and idempotence):
 * <TABLE border="1">
 * <TR>
 *    <TD><B>Idempotent</B></TD>
 *    <TD align="right">when variables have interval domains</TD>
 * </TR>
 * <TR>
 *    <TD><B>Complexity</B></TD>
 *    <TD align="right">dom(i)</TD>
 * </TR>
 * </TABLE>
 *
 * @author Yan Georget
 */
public class Element_3 extends BaseConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The list. */
    protected IntegerVariable[] l;
    /** The element. */
    protected IntegerVariable x;
    /** The index. */
    protected IntegerVariable i;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param x an integer variable
     * @param i an integer variable with a sparse domain
     * @param l an array of integer variables
     */
    public Element_3(IntegerVariable x, 
                     IntegerVariable i, 
                     IntegerVariable[] l) {
        super();
        this.variables = new IntegerVariable[l.length + 2];
        this.variables[0] = x;
        this.variables[1] = i;
        System.arraycopy(l, 0, variables, 2, l.length); 
        complexity = ((IntegerDomain) i.getDomain()).size();
        this.l = l;
        this.x = x;
        this.i = i;
        this.name = "x=vl_i";
        idempotent = x.isMinMax() && !IntegerVariable.oneSparse(l);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        x.addMinMaxConstraint(this);
        i.addDomConstraint(this);
        for (int i=0; i<l.length; i++) {
            l[i].addMinMaxConstraint(this);
        }
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void init(ChoicePointStack cp, 
                     ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        i.adjustMinMax(cp, cs, this, 0, l.length-1); 
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        int xmin = IntegerDomain.DEFAULT_MAX;
        int xmax = IntegerDomain.DEFAULT_MIN;
        final IntegerDomain xDom = (IntegerDomain) x.getDomain();
        for (Iterator k = ((SparseDomain) i.getDomain()).iterator();
             k.hasNext();) {
            final Integer j = (Integer) k.next();
            final IntegerDomain ljDom = 
                (IntegerDomain) l[j.intValue()].getDomain();
            if (xDom.intersects(ljDom)) {
                final int ljMin = ljDom.getMin();
                final int ljMax = ljDom.getMax();
                if (ljMin < xmin) {
                    xmin = ljMin;
                }
                if (ljMax > xmax) {
                    xmax = ljMax;
                }
            } else {
                i.adjustDifferent(cp, cs, this, j, k);
            }
        }
        x.adjustMinMax(cp, cs, this, xmin, xmax);
        if (i.isInstantiated()) {
            l[i.getMin()].adjustMinMax(cp, 
                                       cs,
                                       this,
                                       xDom.getMin(), 
                                       xDom.getMax());
            if (x.isInstantiated()) {
                entailed(cp);
            }
        }
    }
}
/*
 * $Log$
 * Revision 1.23  2005/07/18 15:32:55  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.22  2004/11/23 16:02:51  yan
 * optimized and cleaned a bit
 *
 * Revision 1.21  2004/11/18 17:46:08  yan
 * small optimization
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
