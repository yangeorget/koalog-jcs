package com.koalog.jcs.solver;

import java.util.Iterator;
import com.koalog.jcs.domain.SparseDomain;
import com.koalog.jcs.variable.IntegerVariable;

/**
 * A sparse domain heuristic choosing the value at random.
 * 
 * @see com.koalog.jcs.domain.SparseDomain
 * @author Yan Georget
 */
public class RandomOrderDomainHeuristic extends BaseDomainHeuristic {
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------  
    /** @see com.koalog.jcs.solver.BaseVariableHeuristic */
    public int getValue(IntegerVariable variable) {
        SparseDomain dom = (SparseDomain) variable.getDomain();
        Iterator it = dom.iterator(); 
        Integer value = null;
        for (int i = (int) (Math.random() * dom.size()); i>=0; i--) {
            value = (Integer) it.next();
        }
        return value.intValue();
    }
}
/*
 * $Log$
 * Revision 1.1  2005/09/23 07:51:58  yan
 * initial revision
 *
 */
