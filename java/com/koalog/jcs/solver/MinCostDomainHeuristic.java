package com.koalog.jcs.solver;

import java.util.Map;
import java.util.Iterator;
import com.koalog.jcs.domain.SparseDomain;
import com.koalog.jcs.variable.IntegerVariable;

/**
 * A sparse domain heuristic choosing the value of minimal cost. 
 *
 * @see com.koalog.jcs.domain.SparseDomain
 * @author Yan Georget
 */
public class MinCostDomainHeuristic extends BaseDomainHeuristic {
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
    public MinCostDomainHeuristic(Map costs) {
        super();
        this.costs = costs;
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------  
    /** @see com.koalog.jcs.solver.BaseVariableHeuristic */
    public int getValue(IntegerVariable variable) {
        int[] values = (int[]) costs.get(variable);
        int index = -1;
        int min = Integer.MAX_VALUE;
        for (Iterator it = ((SparseDomain) variable.getDomain()).iterator(); 
             it.hasNext();) {
            int i = ((Integer)it.next()).intValue();
            if (values[i] < min) {
                min = values[i];
                index = i;
            }
        }
        return index;
    }
}
/*
 * $Log$
 * Revision 1.8  2005/09/23 07:50:23  yan
 * fixed jdoc
 *
 * Revision 1.7  2004/10/01 15:20:41  yan
 * improved style (replaced while by for)
 *
 * Revision 1.6  2003/03/07 13:52:52  yan
 * fixed style
 *
 * Revision 1.5  2002/06/16 18:41:42  mphilip
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
