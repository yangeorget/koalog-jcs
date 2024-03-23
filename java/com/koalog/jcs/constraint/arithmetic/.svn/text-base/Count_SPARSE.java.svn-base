package com.koalog.jcs.constraint.arithmetic;

import java.util.List;
import java.util.Iterator;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.constraint.ConstraintScheduler;

/**
 * Counts the number of occurences of a value.
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
public class Count_SPARSE extends Count {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param p an integer variable (non instantiated)
     * @param x an array of integer variables with sparse domains
     * @param c an integer
     */    
    public Count_SPARSE(IntegerVariable p, IntegerVariable[] x, int c) {
        super(p, x, c);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        p.addMinMaxConstraint(this);
        for (int i=0; i<variables.length-1; i++) {
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
 * Revision 1.13  2005/07/22 12:07:53  yan
 * forbidding instantiated counter
 *
 * Revision 1.12  2005/07/18 15:32:55  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.11  2004/11/26 16:54:50  yan
 * optimized
 *
 * Revision 1.10  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.9  2004/06/27 13:00:07  yan
 * fixed complexity
 *
 * Revision 1.8  2004/06/15 13:26:45  yan
 * various optimizations and refactoring
 *
 * Revision 1.7  2004/06/10 14:38:08  yan
 * various refactoring
 *
 * Revision 1.6  2004/05/05 11:46:48  yan
 * fixed style
 *
 * Revision 1.5  2004/04/13 15:32:43  yan
 * adjustDifferent can only be used for variables with a sparse domain
 *
 * Revision 1.4  2003/10/02 23:49:00  yan
 * introduced complexity computation
 *
 * Revision 1.3  2003/06/17 13:36:20  yan
 * added javadoc about algorithmic infos
 *
 * Revision 1.2  2003/06/15 19:47:37  yan
 * fixed style
 *
 * Revision 1.1  2003/06/15 18:02:45  yan
 * initial revision
 *
 */




