package com.koalog.jcs.solver;

import com.koalog.jcs.variable.Variable;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.constraint.ConstraintScheduler;

/**
 * Domain heuristic that instantiates the corresponding integer variable 
 * with a value of its integer domain (which must be sparse).
 *
 * <P>Subclasses of <CODE>BaseDomainHeuristic</CODE> implement the abstract 
 * <CODE>getValue</CODE> method 
 * which returns the value to which the variable will be instantiated.
 *
 * <P>For variables with non sparse domains, directly implement interface 
 * <CODE>DomainHeuristic</CODE> as in the following example:
 * <PRE>
 * public class IncreasingOrderDomainHeuristic implements DomainHeuristic {
 *    public void chooseDomain(ChoicePointStack cp, 
 *                             ConstraintScheduler cs,
 *                             Variable variable) {
 *       IntegerVariable var = (IntegerVariable) variable;
 *       var.chooseMax(cp, cs, var.getMin());
 *    }
 * }
 * </PRE>
 *
 * @see IntegerVariable
 * @see com.koalog.jcs.domain.IntegerDomain
 * @see com.koalog.jcs.domain.SparseDomain
 * @author Yan Georget
 */
public abstract class BaseDomainHeuristic implements DomainHeuristic {
    //------------------------------------------------------------------------
    // ABSTRACT METHODS
    //------------------------------------------------------------------------  
    /**
     * Returns a value.
     * @param variable an integer variable
     * @return an int
     */
    public abstract int getValue(IntegerVariable variable);

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------  
    /**
     * Chooses a value.
     * @param cp a stack of choice points
     * @param cs a constraint scheduler
     * @param variable a variable
     */
    public void chooseDomain(ChoicePointStack cp, 
                             ConstraintScheduler cs,
                             Variable variable) {
        final IntegerVariable var = (IntegerVariable) variable;
        var.chooseValue(cp, cs, getValue(var));
    }
}
/*
 * $Log$
 * Revision 1.8  2004/06/17 11:21:45  yan
 * added jdoc
 *
 * Revision 1.7  2004/04/09 14:43:41  yan
 * removal of events
 *
 * Revision 1.6  2003/06/18 15:20:00  yan
 * removed cast
 *
 * Revision 1.5  2002/06/16 16:05:20  mphilip
 * Modified jdoc.
 *
 * Revision 1.4  2002/06/16 13:59:14  yan
 * added javadoc
 *
 * Revision 1.3  2002/05/02 16:13:18  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:44  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
