package com.koalog.jcs.solver;

import com.koalog.jcs.variable.Variable;
import com.koalog.jcs.domain.SetDomain;

/**
 * This class represents the variable heuristic that 
 * chooses the set variable with the greatest set domain.
 *
 * <P>Note that this corresponds to the <EM>first-fail</EM> principle: 
 * reducing a lot a large domain may lead to an early failure.</P>
 *
 * @see com.koalog.jcs.domain.SetDomain
 * @see com.koalog.jcs.variable.SetVariable
 * @author Yan Georget
 */
public class GreatestSetDomainVariableHeuristic extends BaseVariableHeuristic {
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------  
    /** @see com.koalog.jcs.solver.BaseVariableHeuristic */
    public int eval(Variable var) {
        final SetDomain dom = (SetDomain) var.getDomain();
        return dom.getMax().size() - dom.getMin().size();
    }
}
/*
 * $Log$
 * Revision 1.2  2004/06/10 11:01:21  yan
 * added javadoc
 *
 * Revision 1.1  2003/07/19 18:29:17  yan
 * initial revision
 *
 */
