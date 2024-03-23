package com.koalog.jcs.constraint.arithmetic;

import org.apache.log4j.Category;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.UnaryConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.domain.IntegerDomain;

/**
 * This constraint forces x to take its values in a given set. 
 *
 * <P>Note: this constraint is of no interest if the domain of x is sparse.
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
 * @author Yan Georget
 */
public class InDomain extends UnaryConstraint {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(InDomain.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    private IntegerVariable x;
    private int valueMin;
    private int valueMax;
    int[] min;
    int[] max;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param x an integer variable
     * @param values a set of integers as a sorted array 
     */
    public InDomain(IntegerVariable x, int[] values) {
        super(x);
        this.x = x;
        // [] instead of {} for display compatibility with InRange
        name = "x in ["; 
        for (int j=0; j<values.length; j++) {
            name += values[j];
            if (j < values.length-1) {
                name += ", ";
            }
        }
        name += "]";
        idempotent = true;
        complexity = 1;
        valueMin = values[0];
        valueMax = values[values.length-1];
        min = new int[valueMax-valueMin+1];
        max = new int[valueMax-valueMin+1];
        for (int i=0, j=0; j<values.length-1; j++) {
            while (valueMin+i < values[j+1]) {
                max[i++] = values[j];
            }
        }
        max[valueMax-valueMin] = valueMax;
        for (int i=valueMax-valueMin, j=values.length-1; j>0; j--) {
            while (valueMin+i > values[j-1]) {
                min[i--] = values[j];
            }
        }
        min[0] = valueMin;
    }
  
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        x.addMinMaxConstraint(this);
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        final IntegerDomain xDom = (IntegerDomain) x.getDomain();
        x.adjustMinMax(cp, 
                       cs, 
                       this,  
                       min[xDom.getMin()-valueMin], 
                       max[xDom.getMax()-valueMin]);
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void init(ChoicePointStack cp,
                     ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        x.adjustMinMax(cp, cs, this,  valueMin, valueMax);
    }
}
/*
 * $Log$
 * Revision 1.19  2005/07/25 10:17:09  yan
 * pretty printing of name
 *
 * Revision 1.18  2005/07/19 11:45:49  yan
 * now constant complexity
 *
 * Revision 1.17  2005/07/18 15:30:17  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.16  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.15  2004/09/08 13:16:06  yan
 * fixed style
 *
 * Revision 1.14  2004/09/08 13:12:37  yan
 * values has now to be sorted
 *
 * Revision 1.13  2004/08/25 14:15:18  yan
 * fixed style
 *
 * Revision 1.12  2004/05/05 11:46:49  yan
 * fixed style
 *
 * Revision 1.11  2004/04/09 14:42:50  yan
 * removal of events
 *
 * Revision 1.10  2004/03/14 16:42:59  yan
 * various simplifications
 *
 * Revision 1.9  2003/10/02 23:50:10  yan
 * introduced complexity computation
 *
 * Revision 1.8  2003/06/17 13:36:20  yan
 * added javadoc about algorithmic infos
 *
 * Revision 1.7  2003/04/01 13:58:01  yan
 * fixed complexity
 *
 * Revision 1.6  2003/03/27 11:01:48  yan
 * added events related method (updateConstraints)
 *
 * Revision 1.5  2003/02/11 10:30:42  yan
 * fixed style
 *
 * Revision 1.4  2003/02/05 09:35:33  yan
 * using adjustMinMax
 *
 * Revision 1.3  2003/02/02 21:12:52  yan
 * modifiedVariables is now an instance variable
 *
 * Revision 1.2  2002/12/11 17:39:50  yan
 * fixed side effect
 *
 * Revision 1.1  2002/12/11 12:19:55  yan
 * initial revision
 *
 */
