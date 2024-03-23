package com.koalog.jcs.solver;

import java.util.Iterator;
import java.util.Map;
import com.koalog.jcs.variable.Variable;
import com.koalog.jcs.domain.IntegerDomain;

/**
 * This class represents the variable heuristic that
 * chooses the variable with the greatest regret.
 *
 * <P>The regret is the difference between the two best possible assignments 
 * (assignments of minimal costs).</P>
 *
 * @author Yan Georget
 */
public class MaxRegretVariableHeuristic extends BaseVariableHeuristic {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------  
    /** A map that maps a variable to an array of costs 
        (each element in the variable's domain is associated with a cost). */
    private Map costs;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------  
    /**
     * Sole constructor.
     * @param costs a map that maps a variable to an array of costs 
     * (each element in the variable's domain is associated with a cost)
     */
    public MaxRegretVariableHeuristic(Map costs) {
        this.costs = costs;
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------  
    /** @see com.koalog.jcs.solver.BaseVariableHeuristic */
    public int eval(Variable var) {
        int[] values = (int[]) costs.get(var);
        int v0 = Integer.MAX_VALUE;
        int v1 = Integer.MAX_VALUE;
        for (Iterator it = ((IntegerDomain) var.getDomain()).iterator(); 
             it.hasNext();) {
            int v = values[((Integer) it.next()).intValue()];
            if (v < v0) {
                v1 = v0;
                v0 = v;
            } else if (v < v1) {
                v1 = v;
            }
        }
        return v1 - v0;
    }
}
/*
 * $Log$
 * Revision 1.9  2004/10/01 15:20:32  yan
 * improved style (replaced while by for)
 *
 * Revision 1.8  2004/06/04 11:14:52  yan
 * optimized evaluation method
 *
 * Revision 1.7  2003/03/20 15:13:41  yan
 * removed a test which was non necessary
 *
 * Revision 1.6  2003/03/07 13:52:52  yan
 * fixed style
 *
 * Revision 1.5  2002/06/16 18:39:13  mphilip
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
