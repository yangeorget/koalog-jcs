package com.koalog.jcs.solver;

import com.koalog.jcs.variable.Variable;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.choicepoint.ChoicePointStack;

/**
 * The <CODE>DomainHeuristic</CODE> interface is the root 
 * of the domain heuristic hierarchy. 
 * All domain heuristics implement the methods of this interface.
 *
 * <P>A domain heuristic is responsible for 
 * choosing a sub-domain for a given variable.</P>
 *
 * @see com.koalog.jcs.domain.Domain
 * @author Yan Georget
 */
public interface DomainHeuristic {
    //------------------------------------------------------------------------
    // ABSTRACT METHODS
    //------------------------------------------------------------------------  
    /**
     * Chooses a sub-domain of the domain of the variable given as parameter.
     * @param cp a stack of choice points
     * @param cs a constraint scheduler
     * @param variable a variable
     */
    void chooseDomain(ChoicePointStack cp, 
                      ConstraintScheduler cs,
                      Variable variable);
}
/*
 * $Log$
 * Revision 1.10  2004/06/17 11:21:45  yan
 * added jdoc
 *
 * Revision 1.9  2004/04/09 14:43:41  yan
 * removal of events
 *
 * Revision 1.8  2002/06/16 17:24:30  mphilip
 * Modified jdoc.
 *
 * Revision 1.7  2002/06/16 13:59:14  yan
 * added javadoc
 *
 * Revision 1.6  2002/05/22 09:35:17  yan
 * *** empty log message ***
 *
 * Revision 1.5  2002/05/22 09:27:25  yan
 * added javadoc
 *
 * Revision 1.4  2002/05/03 14:26:49  yan
 * added javadoc
 *
 * Revision 1.3  2002/05/02 16:13:18  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:44  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
