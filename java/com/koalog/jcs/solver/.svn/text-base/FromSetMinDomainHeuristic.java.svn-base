package com.koalog.jcs.solver;

import org.apache.log4j.Category;
import com.koalog.jcs.variable.Variable;
import com.koalog.jcs.variable.SetVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.domain.SetDomain;
import com.koalog.jcs.constraint.ConstraintScheduler;

/**
 * Domain heuristic that splits the domain by removing elements from the max.
 *
 * @see com.koalog.jcs.domain.SetDomain
 * @author Yan Georget
 */
public class FromSetMinDomainHeuristic implements DomainHeuristic {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(FromSetMinDomainHeuristic.class);

     //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------  
    /**
     * Removes elements from the max.
     * @param cp a stack of choice points
     * @param cs a constraint scheduler
     * @param variable a variable
     */
    public void chooseDomain(ChoicePointStack cp, 
                             ConstraintScheduler cs,
                             Variable variable) {
        final SetVariable var = (SetVariable) variable;
        var.chooseMaxByRemoving(cp, 
                                cs, 
                                ((SetDomain) var.getDomain())
                                .getPossibleElement());
    }
}
/*
 * $Log$
 * Revision 1.7  2004/04/09 14:43:41  yan
 * removal of events
 *
 * Revision 1.6  2003/07/21 12:29:00  yan
 * fixed style
 *
 * Revision 1.5  2003/07/21 12:21:15  yan
 * using new choose methods
 *
 * Revision 1.4  2003/07/12 17:57:23  yan
 * added commented debug statements
 *
 * Revision 1.3  2003/06/23 17:14:18  yan
 * fixed style
 *
 * Revision 1.2  2003/06/19 16:50:29  yan
 * changed some signatures
 *
 * Revision 1.1  2003/06/19 15:01:22  yan
 * initial revision
 *
 */
