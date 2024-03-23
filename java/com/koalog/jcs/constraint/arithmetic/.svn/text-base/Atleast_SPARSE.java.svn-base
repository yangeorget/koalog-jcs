package com.koalog.jcs.constraint.arithmetic;

import java.util.List;
import java.util.Iterator;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.constraint.ConstraintScheduler;

/**
 * This constraint enforces that at least p variables 
 * among x_0 and ... x_n-1 are equals to c.
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
 *    <TD align="right">variables.length</TD>
 * </TR>
 * </TABLE>
 *
 * @since 2.0
 * @author Yan Georget
 */
public class Atleast_SPARSE extends Atleast {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param p a positive integer
     * @param variables an array of integer variables with sparse domains
     * @param c an integer
     */    
    public Atleast_SPARSE(int p, IntegerVariable[] variables, int c) {
        super(p, variables, c);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        for (int i=0; i<variables.length; i++) {
            ((IntegerVariable) variables[i]).addDomConstraint(this);
        }
    }
    
    /** @see com.koalog.jcs.constraint.arithmetic.AbstractCount */
    void enforceDifferent(ChoicePointStack cp,
                          ConstraintScheduler cs,
                          List vars,
                          int j) throws ConstraintInconsistencyException {
        for (int i=j; --i>=0;) {
            ((IntegerVariable) variables[i]).adjustDifferent(cp, cs, this, c);
        }
        for (Iterator l = vars.iterator(); l.hasNext();) {
            ((IntegerVariable) l.next()).adjustDifferent(cp, cs, this, c);
        }
        entailed(cp);
    }
}
/*
 * $Log$
 * Revision 1.8  2005/07/18 15:33:55  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.7  2004/11/26 16:54:50  yan
 * optimized
 *
 * Revision 1.6  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.5  2004/06/27 13:00:07  yan
 * fixed complexity
 *
 * Revision 1.4  2004/06/15 13:26:45  yan
 * various optimizations and refactoring
 *
 * Revision 1.3  2004/06/10 14:38:08  yan
 * various refactoring
 *
 * Revision 1.2  2004/06/03 16:50:21  yan
 * added @since tag
 *
 * Revision 1.1  2004/05/05 14:01:57  yan
 * initial revision
 *
 */




