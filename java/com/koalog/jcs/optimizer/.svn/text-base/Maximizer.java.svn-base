package com.koalog.jcs.optimizer;

import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.domain.IntegerDomain;
import com.koalog.jcs.domain.MinMaxDomain;
import com.koalog.jcs.solver.BacktrackSolver;

/**
 * This class implements a maximizer.
 * @author Yan Georget
 */
public class Maximizer extends BacktrackOptimizer { 
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------  
    /**
     * Sole constructor.
     * @param variable the variable to optimize
     * @param solver the solver used to optimize
     * @param mode an integer indicating the type of exploration 
     * of the search tree
     */
    public Maximizer(IntegerVariable variable,
                     BacktrackSolver solver, 
                     int mode) {
        super(variable, solver, mode);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------  
    /** @see com.koalog.jcs.optimizer.BacktrackOptimizer */
    public IntegerDomain optimizingDomain() {
        return new MinMaxDomain(optimum+1,
                                IntegerDomain.DEFAULT_MAX);
    }
}
/*
 * $Log$
 * Revision 1.4  2003/03/07 13:53:29  yan
 * fixed style
 *
 * Revision 1.3  2002/05/02 16:14:13  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:44  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
