package com.koalog.jcs.constraint.arithmetic;

import org.apache.log4j.Category;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.domain.IntegerDomain;

/**
 * This constraint enforces x&ne;y.
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
 *    <TD align="right">1/TD>
 * </TR>
 * </TABLE>
 *
 * @author Yan Georget
 */
public class Neq_SPARSE extends Neq {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(Neq_SPARSE.class);

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param x an integer variable
     * @param y an integer variable
     */
    public Neq_SPARSE(IntegerVariable x, IntegerVariable y) {
        super(x,y);
        complexity = 1;
        idempotent = true;
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        final IntegerDomain yDom = (IntegerDomain) y.getDomain();
        if (yDom.isSingleton()) {
            x.adjustDifferent(cp, cs, this, yDom.getMin());
            entailed(cp);
        } else {
            final IntegerDomain xDom = (IntegerDomain) x.getDomain();
            if (xDom.isSingleton()) {
                y.adjustDifferent(cp, cs, this, xDom.getMin());
                entailed(cp);
            }
        }
    }
}
/*
 * $Log$
 * Revision 1.11  2005/07/18 15:30:17  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.10  2004/06/27 13:00:07  yan
 * fixed complexity
 *
 * Revision 1.9  2004/05/05 11:46:49  yan
 * fixed style
 *
 * Revision 1.8  2004/04/09 14:42:50  yan
 * removal of events
 *
 * Revision 1.7  2003/10/02 23:50:10  yan
 * introduced complexity computation
 *
 * Revision 1.6  2003/06/17 13:36:20  yan
 * added javadoc about algorithmic infos
 *
 * Revision 1.5  2003/03/27 11:01:48  yan
 * added events related method (updateConstraints)
 *
 * Revision 1.4  2003/03/20 18:07:06  yan
 * added entailment check
 *
 * Revision 1.3  2003/02/11 10:30:42  yan
 * fixed style
 *
 * Revision 1.2  2003/02/02 21:12:52  yan
 * modifiedVariables is now an instance variable
 *
 * Revision 1.1  2003/01/13 11:09:03  yan
 * initial revision
 *
 */
