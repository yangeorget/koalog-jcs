package com.koalog.jcs.solver;

import com.koalog.util.Arithmetic;
import com.koalog.jcs.variable.Variable;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.domain.IntegerDomain;

/**
 * Domain heuristic that chooses the first half of an integer domain.
 *
 * <P>Note that, in general, this heuristic does not 
 * instantiate the corresponding variable.</P>
 *
 * @see com.koalog.jcs.domain.IntegerDomain
 * @author Yan Georget
 */
public class SplitLowDomainHeuristic implements DomainHeuristic {
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------  
    /**
     * Chooses the first half.
     * @param cp a stack of choice points
     * @param cs a constraint scheduler
     * @param variable a variable
     */
    public void chooseDomain(ChoicePointStack cp, 
                             ConstraintScheduler cs,
                             Variable variable) {
        final IntegerVariable var = (IntegerVariable) variable;
        final IntegerDomain dom = (IntegerDomain) var.getDomain();
        var.chooseMax(cp, 
                      cs, 
                      Arithmetic.floorDivide_SG1(dom.getMin() + dom.getMax(), 
                                                 2));
    }
}
/*
 * $Log$
 * Revision 1.9  2004/04/09 14:43:41  yan
 * removal of events
 *
 * Revision 1.8  2003/06/18 15:20:00  yan
 * removed cast
 *
 * Revision 1.7  2003/03/07 13:52:52  yan
 * fixed style
 *
 * Revision 1.6  2002/12/10 17:33:56  yan
 * added getMiddle method
 *
 * Revision 1.5  2002/06/16 19:31:39  mphilip
 * Modified jdoc.
 *
 * Revision 1.4  2002/06/16 13:59:15  yan
 * added javadoc
 *
 * Revision 1.3  2002/05/02 16:13:18  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:44  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
