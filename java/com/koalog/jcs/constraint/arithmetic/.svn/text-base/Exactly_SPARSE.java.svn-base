package com.koalog.jcs.constraint.arithmetic;

import java.util.List;
import java.util.Iterator;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.constraint.ConstraintScheduler;

/**
 * This constraint enforces that exactly p variables 
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
 * @author Yan Georget
 */
public class Exactly_SPARSE extends Exactly {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param p a positive integer
     * @param variables an array of integer variables with sparse domains
     * @param c an integer
     */    
    public Exactly_SPARSE(int p, IntegerVariable[] variables, int c) {
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
 * Revision 1.16  2005/07/18 15:32:55  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.15  2004/11/26 16:54:50  yan
 * optimized
 *
 * Revision 1.14  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.13  2004/06/27 13:00:07  yan
 * fixed complexity
 *
 * Revision 1.12  2004/06/15 13:26:45  yan
 * various optimizations and refactoring
 *
 * Revision 1.11  2004/06/10 14:38:08  yan
 * various refactoring
 *
 * Revision 1.10  2004/05/05 11:46:48  yan
 * fixed style
 *
 * Revision 1.9  2004/04/13 15:32:43  yan
 * adjustDifferent can only be used for variables with a sparse domain
 *
 * Revision 1.8  2003/10/02 23:50:09  yan
 * introduced complexity computation
 *
 * Revision 1.7  2003/06/17 13:36:20  yan
 * added javadoc about algorithmic infos
 *
 * Revision 1.6  2003/04/01 13:58:01  yan
 * fixed complexity
 *
 * Revision 1.5  2003/03/27 11:01:48  yan
 * added events related method (updateConstraints)
 *
 * Revision 1.4  2003/03/20 19:17:03  yan
 * added entailment statement
 *
 * Revision 1.3  2003/03/20 15:12:30  yan
 * small optim
 *
 * Revision 1.2  2003/03/07 11:19:02  yan
 * fixed style
 *
 * Revision 1.1  2003/02/25 11:20:39  yan
 * initial revision
 *
 */




