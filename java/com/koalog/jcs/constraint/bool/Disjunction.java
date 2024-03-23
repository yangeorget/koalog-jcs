package com.koalog.jcs.constraint.bool;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.constraint.BaseConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;

/**
 * This constraint enforces x=y_0 or ... y_n-1.
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
 *    <TD align="right">n</TD>
 * </TR>
 * </TABLE>
 *
 * @author Yan Georget
 */
public class Disjunction extends BaseConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    private BooleanVariable[] y;
    private BooleanVariable x;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param x an boolean variable
     * @param y an array of boolean variables
     */    
    public Disjunction(BooleanVariable x, BooleanVariable[] y) {
        super();
        this.name = "x=or(y)";
        this.variables = new BooleanVariable[y.length + 1];
        this.variables[0] = x;
        System.arraycopy(y, 0, variables, 1, y.length); 
        this.x = x;
        this.y = y;
        complexity = y.length; 
        idempotent = true;
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        for (int i=0; i<variables.length; i++) {
            ((BooleanVariable) variables[i]).addGroundConstraint(this);
        }
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        if (x.isInstantiated()) {
            if (x.isFalse()) {
                for (int i=y.length; --i>=0;) {
                    y[i].adjustFalse(cp, cs, this);
                }
                // no need to call entailed 
                // since all the variables are instantiated
                return;
            } else { // x is true
                int f = -1;
                int i = -1;
                int n = 0;
                while(++i<y.length && n<2) {
                    if (!y[i].isFalse()) {
                        f = i;
                        n++;
                    }
                }
                switch (n) {
                case 0:
                    fail();
                case 1:
                    y[f].adjustTrue(cp, cs, this);
                    // no need to call entailed 
                    // since all the variables are instantiated
                    return;
                }
            }
        }
        if (BooleanVariable.oneTrue(y)) {
            x.adjustTrue(cp, cs, this);
            entailed(cp);
        } else if (BooleanVariable.allFalse(y)) {
            x.adjustFalse(cp, cs, this);
            // no need to call entailed 
            // since all the variables are instantiated
        } 
    }
}
/*
 * $Log$
 * Revision 1.2  2005/07/18 15:28:21  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.1  2005/05/16 14:43:15  yan
 * initial revision
 *
 */
