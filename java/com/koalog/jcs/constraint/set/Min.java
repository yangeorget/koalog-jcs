package com.koalog.jcs.constraint.set;

import java.util.Iterator;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.BinaryConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.variable.SetVariable;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;

/**
 * This constraint enforces: x = min(y).
 * 
 * <P>Here, the elements of set <CODE>y</CODE> are <CODE>Integer</CODE>s 
 * (and not only <CODE>Comparable</CODE>s). 
 *
 * <P>Algorithmic informations 
 * (see 
 * {@link com.koalog.jcs.constraint.Constraint Constraint}
 * for more details on complexity and idempotence):
 * <TABLE border="1">
 * <TR>
 *    <TD><B>Idempotent</B></TD>
 *    <TD align="right">when x has an interval domain</TD>
 * </TR>
 * <TR>
 *    <TD><B>Complexity</B></TD>
 *    <TD align="right">card(y)</TD>
 * </TR>
 * </TABLE>
 *
 * @author Yan Georget
 */
public class Min extends BinaryConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The minimal element. */
    protected IntegerVariable x;
    /** The set. */
    protected SetVariable y;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param x an integer variable
     * @param y a set variable
     */
    public Min(IntegerVariable x, SetVariable y) {
        super(x, y);
        this.x = x;
        this.y = y;
        name = "x=min(y)";
        idempotent = x.isMinMax();
        complexity = y.getMax().size();
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        x.addMinConstraint(this);
        y.addMinMaxConstraint(this);
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        // the min is greater than the smallest possible element of the set
        boolean notFound = true;
        int minMin = x.getMin();
        int minMax = x.getMax();
        for (Iterator i = y.getMax().iterator(); i.hasNext();) {
            int newMin = ((Integer) i.next()).intValue();
            if (minMin <= newMin && newMin<=minMax) {
                x.adjustMin(cp, cs, this, newMin);
                notFound = false;
                break;
            }
        }
        if (notFound) {
            fail();
        }
        // the min is smaller than the smallest element of the set
        if (y.getMin().size() != 0) {
            x.adjustMax(cp, 
                        cs, 
                        this, 
                        ((Integer) y.getMin().first()).intValue());
        }
        // the possible elements of the set must be greater than the minimal min
        y.adjustMax(cp, 
                    cs, 
                    this, 
                    y.getMax().tailSet(new Integer(x.getMin())));
        // we don't want an empty set since it must contain at least the min
        if (y.getMax().size() == 0) {
            fail();
        }
        // the set must contain at least the min when instantiated
        if (x.isInstantiated()) {
            y.adjustMinByIncluding(cp, 
                                   cs, 
                                   this, 
                                   new Integer(x.getMin()));
        }
        
    }
}
