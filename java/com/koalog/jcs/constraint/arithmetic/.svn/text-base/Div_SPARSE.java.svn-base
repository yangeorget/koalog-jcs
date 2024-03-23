package com.koalog.jcs.constraint.arithmetic;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import com.koalog.util.Arithmetic;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.BinaryConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.domain.SparseDomain;
import com.koalog.jcs.domain.IntegerDomain;

/**
 * This constraint enforces y=x/c where / stands for the euclidian division.
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
 *    <TD align="right">max(dom(x)*c, dom(y))</TD>
 * </TR>
 * </TABLE>
 *
 * @since 3.1
 * @author Yan Georget
 */
public class Div_SPARSE extends BinaryConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** An integer variable. */
    IntegerVariable x;
    /** An integer variable. */
    IntegerVariable y;
    /** The constant as an int. */
    int c;
    
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param x an integer variable
     * @param c an integer
     * @param y an integer variable
     */
    public Div_SPARSE(IntegerVariable x, IntegerVariable y, int c) {
        super(x, y);
        this.name = "x=y/" + c;
        this.c = c;
        this.x = x;
        this.y = y;
        complexity = Math.max(c*((IntegerDomain) x.getDomain()).size(), 
                              ((IntegerDomain) y.getDomain()).size());
        idempotent = true;
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
        final SortedSet xvalues = new TreeSet();
        for (Iterator i = ((SparseDomain) y.getDomain()).iterator();
             i.hasNext();) {
            xvalues.add(new Integer(Arithmetic
                                    .floorDivide(((Integer) i.next())
                                                 .intValue(), 
                                                 c)));
        }
        x.adjustDomain(cp, cs, this, xvalues);
        final SortedSet yvalues = new TreeSet();
        for (Iterator i = ((SparseDomain) x.getDomain()).iterator();
             i.hasNext();) {
            final int val = ((Integer) i.next()).intValue();
            for (int shift=0; shift<c; shift++) {
                yvalues.add(new Integer(c*val + shift));
            }
        }
        y.adjustDomain(cp, cs, this, yvalues);
    }
}
