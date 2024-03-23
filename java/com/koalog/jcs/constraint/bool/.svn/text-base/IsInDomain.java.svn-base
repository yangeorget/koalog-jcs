package com.koalog.jcs.constraint.bool;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import org.apache.log4j.Category;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.constraint.BinaryConstraint;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.domain.SparseDomain;

/**
 * This constraint enforces b<=>x in s.
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
 *    <TD align="right">card(s)</TD>
 * </TR>
 * </TABLE>
 *
 * @author Yan Georget
 */
public class IsInDomain extends BinaryConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    Set s;
    BooleanVariable b;
    IntegerVariable x;
    
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param b a boolean variable
     * @param x an integer variable with a sparse domain
     * @param s a set of integers 
     */
    public IsInDomain(BooleanVariable b, IntegerVariable x, Set s) {
        super(b,x);
        name = "b<=>x in " + s;
        this.s = s;
        this.b = b;
        this.x = x;
        idempotent = true;
        complexity = s.size();
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        b.addGroundConstraint(this);
        x.addDomConstraint(this);
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        if (b.isFalse()) {
            for (Iterator i = s.iterator(); i.hasNext();) {
                x.adjustDifferent(cp, 
                                  cs, 
                                  this, 
                                  ((Integer) i.next()).intValue());
            }
            entailed(cp);
            return;
        } else if (b.isTrue()) {
            x.adjustDomain(cp, cs, this, new TreeSet(s));
            entailed(cp);
            return;
        } else {
            final Set xValues = ((SparseDomain) x.getDomain()).toSet();
            if (s.containsAll(xValues)) {
                b.adjustTrue(cp, cs, this);
                entailed(cp);
                return;
            }
            xValues.retainAll(s);
            if (xValues.isEmpty()) {
                b.adjustFalse(cp, cs, this);
                entailed(cp);
                return;
            } 
        }
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(IsInDomain.class);
}
/*
 * $Log$
 * Revision 1.1  2005/07/25 10:10:32  yan
 * renamed InSet into InRange
 *
 * Revision 1.1  2005/07/22 17:05:12  yan
 * initial revision
 *
 */
