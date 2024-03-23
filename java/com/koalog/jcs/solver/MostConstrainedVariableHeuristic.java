package com.koalog.jcs.solver;

import com.koalog.jcs.variable.Variable;

/**
 * This class represents the variable heuristic that 
 * chooses the variable which is the most constrained 
 * (which appears in the greatest number of constraints). 
 * 
 * @author Yan Georget
 */
public class MostConstrainedVariableHeuristic extends BaseVariableHeuristic {
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------  
    /** @see com.koalog.jcs.solver.BaseVariableHeuristic */
    public int eval(Variable var) {
        return var.getConstraintNb();
    }
}
/*
 * $Log$
 * Revision 1.9  2004/07/21 15:43:24  yan
 * removed unnecessary code
 *
 * Revision 1.8  2004/04/01 19:21:00  yan
 * problem is not a constraint anymore
 *
 * Revision 1.7  2003/03/27 10:45:54  yan
 * changed the way the nb of constraints depending on a variable is computed
 *
 * Revision 1.6  2003/03/07 13:52:52  yan
 * fixed style
 *
 * Revision 1.5  2002/10/09 11:02:48  yan
 * using getConstrainingConstraints
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
