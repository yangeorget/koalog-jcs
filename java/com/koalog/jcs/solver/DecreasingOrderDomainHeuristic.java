package com.koalog.jcs.solver;

import com.koalog.jcs.variable.Variable;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.constraint.ConstraintScheduler;

/**
 * Domain heuristic that instantiates the domain with its max.
 *
 * @see com.koalog.jcs.domain.Domain
 * @author Yan Georget
 */
public class DecreasingOrderDomainHeuristic implements DomainHeuristic {
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------  
    /**
     * Chooses the max.
     * @param cp a stack of choice points
     * @param cs a constraint scheduler
     * @param variable a variable
     */
    public void chooseDomain(ChoicePointStack cp, 
                             ConstraintScheduler cs,
                             Variable variable) {
        final IntegerVariable var = (IntegerVariable) variable;
        var.chooseMin(cp, cs, var.getMax());
    }
}
/*
 * $Log$
 * Revision 1.7  2004/04/09 14:43:41  yan
 * removal of events
 *
 * Revision 1.6  2003/06/18 15:20:00  yan
 * removed cast
 *
 * Revision 1.5  2002/06/16 17:15:42  mphilip
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
