package com.koalog.jcs.constraint.arithmetic;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.domain.IntegerDomain;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.variable.Variable;

/**
 * This constraint enforces that at least p variables 
 * among x_0 and ... x_n-1 are equals to c.
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
public class Atleast extends AbstractCheck {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param p a positive integer
     * @param variables an array of integer variables
     * @param c an integer
     */    
    public Atleast(int p, IntegerVariable[] variables, int c) {
        super(p, variables, c);
        name = "atleast(" + p + ", x, " + c + ")";
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        vars.clear();
        nbEqual = p; // min number of
        nbDifferent = variables.length-p; // max number of
        for (int i=variables.length; --i>=0;) {
            final Variable var = variables[i];
            final IntegerDomain dom = (IntegerDomain) var.getDomain();
            if (dom.contains(c)) {
                if (dom.isSingleton()) { // then, equals to c
                    if (--nbEqual == 0) {
                        entailed(cp);
                        return;
                    }
                } else {
                    vars.add(var);
                }
            } else {
                if (--nbDifferent == 0) {
                    enforceEqual(cp, cs, vars, i);
                    return;
                } 
            }
        }
    }
}





