package com.koalog.jcs.solver;

import com.koalog.jcs.variable.Variable;
import com.koalog.jcs.domain.SetDomain;

/**
 * This class represents the variable heuristic that 
 * chooses the set variable with the smallest set domain.
 *
 * <P>Note that this corresponds to the <EM>first-fail</EM> principle:
 * reducing the domain of variable which is constrained a lot 
 * may lead to an early failure.</P>
 *
 * @see com.koalog.jcs.domain.SetDomain
 * @see com.koalog.jcs.variable.SetVariable
 * @author Yan Georget
 */
public class SmallestSetDomainVariableHeuristic implements VariableHeuristic {
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------  
    /**
     * Chooses the variable with the smallest domain.
     * @param variables an array of variables
     * @return a variable
     */
    public Variable chooseVariable(Variable[] variables) {
        Variable variable = null;
        int value = Integer.MAX_VALUE;
        for (int i=0; i<variables.length; i++) {
            SetDomain dom = (SetDomain) variables[i].getDomain();
            final int val = dom.getMax().size() - dom.getMin().size();
            if (val > 0) {
                if (val == 1) {
                    return variables[i];
                } else if (val < value) {
                    variable = variables[i];
                    value = val;
                }
            }
        } 
        return variable;
    }  
}
/*
 * $Log$
 * Revision 1.4  2004/11/25 17:18:49  yan
 * optimized
 *
 * Revision 1.3  2004/06/10 11:01:21  yan
 * added javadoc
 *
 * Revision 1.2  2003/07/19 18:28:33  yan
 * small optim
 *
 * Revision 1.1  2003/07/19 13:18:41  yan
 * initial revision
 *
 */
