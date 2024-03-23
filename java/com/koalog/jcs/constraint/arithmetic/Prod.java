package com.koalog.jcs.constraint.arithmetic;

import com.koalog.util.Arithmetic;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.TernaryConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.domain.IntegerDomain;

/**
 * This constraint enforces x=y*z.
 *
 * <P>Algorithmic informations 
 * (see 
 * {@link com.koalog.jcs.constraint.Constraint Constraint}
 * for more details on complexity and idempotence):
 * <TABLE border="1">
 * <TR>
 *    <TD><B>Idempotent</B></TD>
 *    <TD align="right">when variables have interval domains</TD>
 * </TR>
 * <TR>
 *    <TD><B>Complexity</B></TD>
 *    <TD align="right">1</TD>
 * </TR>
 * </TABLE>
 *
 * @author Yan Georget
 * @since 2.3
 */
public class Prod extends TernaryConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** An integer variable. */
    protected IntegerVariable x;
    /** An integer variable. */
    protected IntegerVariable y;
    /** An integer variable. */
    protected IntegerVariable z;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param x an integer variable
     * @param y an integer variable
     * @param z an integer variable
     */
    public Prod(IntegerVariable x, IntegerVariable y, IntegerVariable z) {
        super(x, y, z);
        this.name = "x=y*z";
        this.x = x;
        this.y = y;
        this.z = z;
        idempotent = x.isMinMax() && y.isMinMax() && z.isMinMax();
        complexity = 1;
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        x.addMinMaxConstraint(this);
        y.addMinMaxConstraint(this);
        z.addMinMaxConstraint(this);
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        final IntegerDomain yDom = (IntegerDomain) y.getDomain();
        final IntegerDomain zDom = (IntegerDomain) z.getDomain();
        if ((zDom.isSingleton() && zDom.getMin() == 0) 
            || (yDom.isSingleton() && yDom.getMin() == 0)) {
            x.adjustValue(cp, cs, this, 0);
            entailed(cp);
            return;
        }
        final IntegerDomain xDom = (IntegerDomain) x.getDomain();
        final int xmin = xDom.getMin();
        if (xDom.isSingleton() && xmin == 0) {
            if (!zDom.contains(0)) {
                y.adjustValue(cp, cs, this, 0);
                entailed(cp);
            } else if (!yDom.contains(0)) {
                z.adjustValue(cp, cs, this, 0);
                entailed(cp);
            }
            return;
        }
        final int xmax = xDom.getMax();
        filter(cp, cs, y, xmin, xmax, z);
        if (yDom.isSingleton() && yDom.getMin() == 0) {
            x.adjustValue(cp, cs, this, 0);
            entailed(cp);
            return;
        }
        filter(cp, cs, z, xmin, xmax, y);
        if (zDom.isSingleton() && zDom.getMin() == 0) {
            x.adjustValue(cp, cs, this, 0);
            entailed(cp);
            return;
        }
        final int ymax = yDom.getMax();
        final int zmax = zDom.getMax();
        final int ymin = yDom.getMin();
        final int zmin = zDom.getMin();
        final int ymin_zmin = ymin*zmin;
        final int ymax_zmin = ymax*zmin;
        final int ymin_zmax = ymin*zmax;
        final int ymax_zmax = ymax*zmax;
        x.adjustMinMax(cp, 
                       cs, 
                       this, 
                       Arithmetic.min(ymin_zmin, 
                                      ymin_zmax,
                                      ymax_zmin, 
                                      ymax_zmax),
                       Arithmetic.max(ymin_zmin, 
                                      ymin_zmax,
                                      ymax_zmin, 
                                      ymax_zmax));
    }

    private void filter(ChoicePointStack cp,
                        ConstraintScheduler cs,
                        IntegerVariable y, 
                        int xmin,
                        int xmax,
                        IntegerVariable z) 
        throws ConstraintInconsistencyException {
        final IntegerDomain zDom = (IntegerDomain) z.getDomain();
        final int zmin = zDom.getMin();
        final int zmax = zDom.getMax();
        if (!zDom.contains(0)) { // z different from 0
            y.adjustMinMax(cp, 
                           cs, 
                           this, 
                           Arithmetic.min(Arithmetic.ceilDivide(xmin, zmin), 
                                          Arithmetic.ceilDivide(xmin, zmax),
                                          Arithmetic.ceilDivide(xmax, zmin), 
                                          Arithmetic.ceilDivide(xmax, zmax)),
                           Arithmetic.max(Arithmetic.floorDivide(xmin, zmin), 
                                          Arithmetic.floorDivide(xmin, zmax),
                                          Arithmetic.floorDivide(xmax, zmin), 
                                          Arithmetic.floorDivide(xmax, zmax)));
        } else if (zmin == 0) {
            y.adjustMinMax(cp, 
                           cs, 
                           this, 
                           Math.min(xmin,
                                    Arithmetic.ceilDivide(xmin, zmax)),
                           Math.max(xmax, 
                                    Arithmetic.floorDivide(xmax, zmax)));
        } else if (zmax == 0) {
            y.adjustMinMax(cp, 
                           cs, 
                           this, 
                           Math.min(-xmax,
                                    Arithmetic.ceilDivide(xmax, zmin)),
                           Math.max(-xmin, 
                                    Arithmetic.floorDivide(xmin, zmin)));
        } else { 
            y.adjustMinMax(cp, 
                           cs, 
                           this,
                           Arithmetic.min(xmin, 
                                          Arithmetic.ceilDivide(xmin, zmax),
                                          -xmax,
                                          Arithmetic.floorDivide(xmax, zmin)),
                           Arithmetic.max(xmax, 
                                          Arithmetic.ceilDivide(xmax, zmax),
                                          -xmin,
                                          Arithmetic.floorDivide(xmin, zmin)));
        }
    }
}
/*
 * $Log$
 * Revision 1.11  2005/07/18 15:30:17  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.10  2005/06/13 08:56:37  yan
 * fixed bug
 *
 * Revision 1.9  2005/02/02 14:09:40  yan
 * fixed style
 *
 * Revision 1.8  2004/11/26 13:56:33  yan
 * optimized
 *
 * Revision 1.7  2004/11/26 09:35:47  yan
 * using adjustValue
 *
 * Revision 1.6  2004/11/09 17:07:15  yan
 * throwing static exceptions
 *
 * Revision 1.5  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.4  2004/09/15 16:03:17  yan
 * fixed style
 *
 * Revision 1.3  2004/09/08 13:11:46  yan
 * fixed bug: not idempotent
 *
 * Revision 1.2  2004/09/07 19:16:01  yan
 * fixed style
 *
 * Revision 1.1  2004/09/07 16:37:14  yan
 * initial revision
 *
 */

