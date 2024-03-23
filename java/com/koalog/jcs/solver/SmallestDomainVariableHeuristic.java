package com.koalog.jcs.solver;

import com.koalog.jcs.variable.Variable;
import com.koalog.jcs.domain.IntegerDomain;

/**
 * This class represents the variable heuristic that 
 * chooses the integer variable with the smallest integer domain.
 *
 * <P>Note that this corresponds to the <EM>first-fail</EM> principle:
 * reducing the domain of variable which is constrained a lot 
 * may lead to an early failure.</P>
 *
 * @see com.koalog.jcs.domain.IntegerDomain
 * @see com.koalog.jcs.variable.IntegerVariable
 * @author Yan Georget
 */
public class SmallestDomainVariableHeuristic implements VariableHeuristic {
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
            int val = ((IntegerDomain) variables[i].getDomain()).size();
            if (val != 1) {
                if (val == 2) {
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
 * Revision 1.8  2004/06/10 11:01:21  yan
 * added javadoc
 *
 * Revision 1.7  2003/03/09 18:45:56  yan
 * weird optimization (declaration inside loop)
 *
 * Revision 1.6  2003/03/09 16:58:02  yan
 * optimized (returns when size equals 2)
 *
 * Revision 1.5  2003/03/07 13:52:52  yan
 * fixed style
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
