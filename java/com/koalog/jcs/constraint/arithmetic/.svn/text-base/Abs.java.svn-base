package com.koalog.jcs.constraint.arithmetic;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.constraint.BinaryConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.domain.IntegerDomain;
import com.koalog.jcs.variable.IntegerVariable;

/**
 * This constraint enforces x=|y|.
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
 */
public class Abs extends BinaryConstraint {
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
    public Abs(IntegerVariable x, IntegerVariable y) {
        super(x, y);
        this.x = x;
        this.y = y;
        name = "x=|y|";
        idempotent = x.isMinMax() && y.isMinMax();
        complexity = 1;
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
        final IntegerDomain yDom = (IntegerDomain) y.getDomain();
        final int yMin = yDom.getMin();
        final int yMax = yDom.getMax();
        if (yMin > 0) {
            x.adjustMinMax(cp, cs, this, yMin, yMax);
        } else if (yMax < 0) {
            x.adjustMinMax(cp, cs, this, -yMax, -yMin);
        } else {
            x.adjustMinMax(cp, cs, this, 0, Math.max(-yMin, yMax));
        }
        final int xMax = x.getMax();
        y.adjustMinMax(cp, cs, this, -xMax, xMax);
    }
}
/*
 * $Log$
 * Revision 1.16  2005/07/18 15:32:55  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.15  2004/11/26 13:15:07  yan
 * optimized
 *
 * Revision 1.14  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.13  2004/06/27 13:00:07  yan
 * fixed complexity
 *
 * Revision 1.12  2004/04/09 14:42:50  yan
 * removal of events
 *
 * Revision 1.11  2004/03/14 16:42:59  yan
 * various simplifications
 *
 * Revision 1.10  2003/06/17 13:34:08  yan
 * added javadoc about algorithmic infos
 *
 * Revision 1.9  2003/04/01 13:58:01  yan
 * fixed complexity
 *
 * Revision 1.8  2003/03/27 11:01:47  yan
 * added events related method (updateConstraints)
 *
 * Revision 1.7  2003/02/11 10:26:41  yan
 * fixed style
 *
 * Revision 1.6  2003/02/05 09:35:33  yan
 * using adjustMinMax
 *
 * Revision 1.5  2003/02/02 21:14:55  yan
 * modifiedVariables is now an instance variable
 *
 * Revision 1.4  2002/10/04 16:25:10  yan
 * various optimizations
 *
 * Revision 1.3  2002/05/02 16:21:22  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
