package com.koalog.jcs.constraint.arithmetic;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.BinaryConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.domain.SparseDomain;
import com.koalog.jcs.domain.IntegerDomain;

/**
 * This constraint enforces x=y.
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
 *    <TD align="right">1</TD>
 * </TR>
 * </TABLE>
 *
 * @since 3.1
 * @author Yan Georget
 */
public class Eq_SPARSE extends BinaryConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    private IntegerVariable x;
    private IntegerVariable y;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param x an integer variable
     * @param y an integer variable
     */
    public Eq_SPARSE(IntegerVariable x, IntegerVariable y) {
        super(x,y);
        this.x = x;
        this.y = y;
        name = "x=y";
        idempotent = true;
        complexity = Math.max(((IntegerDomain) x.getDomain()).size(),
                              ((IntegerDomain) y.getDomain()).size());
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        x.addDomConstraint(this);
        y.addDomConstraint(this);
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        x.adjustDomain(cp, 
                       cs, 
                       this, 
                       ((SparseDomain) y.getDomain()).getValues());
        y.adjustDomain(cp, 
                       cs, 
                       this, 
                       ((SparseDomain) x.getDomain()).getValues());
    }
}
