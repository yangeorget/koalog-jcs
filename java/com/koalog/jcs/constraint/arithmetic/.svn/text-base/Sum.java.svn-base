package com.koalog.jcs.constraint.arithmetic;

import java.util.Collection;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.BaseConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.domain.IntegerDomain;

/**
 * This constraint enforces s=SIGMA_i x_i.
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
 *    <TD align="right">variables.length</TD>
 * </TR>
 * </TABLE>
 *
 * @author Yan Georget
 */
public class Sum extends BaseConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    private IntegerVariable s;
    private int[] mins;
    private int[] maxs;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param s an integer variable
     * @param x an array of integer variables
     */    
    public Sum(IntegerVariable s, IntegerVariable[] x) {
        super();
        this.name = "s=SIGMA_i x_i";
        this.s = s;
        this.variables = new IntegerVariable[x.length + 1];
        this.variables[0] = s;
        System.arraycopy(x, 0, variables, 1, x.length);
        complexity = variables.length; 
        idempotent = s.isMinMax() && !IntegerVariable.oneSparse(x);
        mins = new int[variables.length];
        maxs = new int[variables.length];
    }

    /**
     * Auxilliary constructor.
     * @param s an integer variable
     * @param x a collection of integer variables
     */
    public Sum(IntegerVariable s, Collection x) {
        this(s, (IntegerVariable[]) x.toArray(new IntegerVariable[] {}));
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        for (int i=0; i<variables.length; i++) {
            ((IntegerVariable) variables[i]).addMinMaxConstraint(this);
        }
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        final IntegerDomain sDom = (IntegerDomain) s.getDomain();
        int min = mins[0] = sDom.getMin();
        int max = maxs[0] = sDom.getMax();
        for (int j=variables.length; --j>0;) {
            final IntegerDomain dom = (IntegerDomain) variables[j].getDomain();
            max -= mins[j] = dom.getMin();
            min -= maxs[j] = dom.getMax();
        }
        s.adjustMinMax(cp, 
                       cs,
                       this,
                       maxs[0]-max, 
                       mins[0]-min);                   
        for (int j=variables.length; --j>0;) { 
            ((IntegerVariable) variables[j]).adjustMinMax(cp, 
                                                          cs,
                                                          this,
                                                          min+maxs[j],
                                                          max+mins[j]);
        }
    }
}
/*
 * $Log$
 * Revision 1.19  2005/07/18 15:28:21  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.18  2004/11/10 10:19:27  yan
 * various optims
 *
 * Revision 1.17  2004/11/02 13:54:27  yan
 * optimized for loops
 *
 * Revision 1.16  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.15  2004/05/15 15:40:29  yan
 * various small optims
 *
 * Revision 1.14  2004/05/05 11:46:49  yan
 * fixed style
 *
 * Revision 1.13  2004/04/09 14:42:50  yan
 * removal of events
 *
 * Revision 1.12  2003/10/02 23:50:10  yan
 * introduced complexity computation
 *
 * Revision 1.11  2003/06/17 13:36:20  yan
 * added javadoc about algorithmic infos
 *
 * Revision 1.10  2003/04/01 13:58:01  yan
 * fixed complexity
 *
 * Revision 1.9  2003/03/27 11:01:48  yan
 * added events related method (updateConstraints)
 *
 * Revision 1.8  2003/02/11 10:30:42  yan
 * fixed style
 *
 * Revision 1.7  2003/02/02 21:12:51  yan
 * modifiedVariables is now an instance variable
 *
 * Revision 1.6  2002/10/08 17:18:31  yan
 * added complexity property
 *
 * Revision 1.5  2002/10/05 16:26:15  yan
 * made linear
 *
 * Revision 1.4  2002/10/04 16:25:10  yan
 * various optimizations
 *
 * Revision 1.3  2002/05/02 16:21:23  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
