package com.koalog.jcs.solver;

import com.koalog.jcs.variable.Variable;
import com.koalog.jcs.domain.IntegerDomain;

/**
 * This class represents the variable heuristic that 
 * chooses the integer variable with the greatest integer domain.
 *
 * <P>Note that this corresponds to the <EM>first-fail</EM> principle: 
 * reducing a lot a large domain may lead to an early failure.</P>
 *
 * @see com.koalog.jcs.domain.IntegerDomain
 * @see com.koalog.jcs.variable.IntegerVariable
 * @author Yan Georget
 */
public class GreatestDomainVariableHeuristic extends BaseVariableHeuristic {
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------  
    /** @see com.koalog.jcs.solver.BaseVariableHeuristic */
    public int eval(Variable var) {
        return ((IntegerDomain) var.getDomain()).size();
    }
}
/*
 * $Log$
 * Revision 1.6  2004/06/10 11:01:20  yan
 * added javadoc
 *
 * Revision 1.5  2003/03/07 13:52:52  yan
 * fixed style
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
