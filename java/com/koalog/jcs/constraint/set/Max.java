package com.koalog.jcs.constraint.set;

import java.util.Iterator;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.BinaryConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.variable.SetVariable;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;

/**
 * This constraint enforces: x = max(y).
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
public class Max extends BinaryConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The maximal element. */
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
    public Max(IntegerVariable x, SetVariable y) {
        super(x, y);
        this.x = x;
        this.y = y;
        name = "x=max(y)";
        idempotent = x.isMinMax();
        complexity = y.getMax().size();
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        x.addMaxConstraint(this);
        y.addMinMaxConstraint(this);
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        // the max is smaller than the greatest possible element of the set
        int maxMin = x.getMin();
        int maxMax = x.getMax();
        int newBestMax = -1;
        // a bit complex because we iterate in the wrong sense
        for (Iterator i = y.getMax().iterator(); i.hasNext();) {
            int newMax = ((Integer) i.next()).intValue();
            if (maxMin <= newMax && newMax<=maxMax) {
                newBestMax = newMax;
            }
        }
        if (newBestMax == -1) {
            fail();
        } else {
            x.adjustMax(cp, cs, this, newBestMax);
        }
        // the max is greater than the greatest element of the set
        if (y.getMin().size() != 0) {
            x.adjustMin(cp, 
                        cs, 
                        this, 
                        ((Integer) y.getMin().last()).intValue());
        }
        // the possible elements of the set must be smaller than the maximal max
        y.adjustMax(cp, 
                    cs, 
                    this, 
                    y.getMax().headSet(new Integer(x.getMax()+1)));
        // we don't want an empty set since it must contain at least the max
        if (y.getMax().size() == 0) {
            fail();
        }
        // the set must contain at least the max when instantiated
        if (x.isInstantiated()) {
            y.adjustMinByIncluding(cp, 
                                   cs, 
                                   this, 
                                   new Integer(x.getMin()));
        }
        
    }
}
