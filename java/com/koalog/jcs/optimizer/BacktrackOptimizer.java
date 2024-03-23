package com.koalog.jcs.optimizer;

import org.apache.log4j.Category;
import com.koalog.jcs.TimeOutException;
import com.koalog.jcs.InconsistencyException;
import com.koalog.jcs.solver.BacktrackSolver;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.domain.IntegerDomain;
import com.koalog.jcs.choicepoint.PrunableChoicePointStack;

/**
 * This abstract class describes an optimizer that uses a backtrack solver.
 *
 * @see BacktrackSolver
 * @author Yan Georget
 */
public abstract class BacktrackOptimizer extends BaseOptimizer {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
       Category.getInstance(BacktrackOptimizer.class);
    private static final boolean DEBUG = cat.isDebugEnabled();

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
    public BacktrackOptimizer(IntegerVariable variable,
                              BacktrackSolver solver,
                              int mode) {
        super(variable, solver, mode);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.optimizer.BaseOptimizer */
    public void findBetterSolutions(long timeOut) 
        throws InconsistencyException, TimeOutException {
        BacktrackSolver btSolver = (BacktrackSolver) solver;
        btSolver.initialFilter();
        for (;;) {
            btSolver.search(timeOut);
            // storing the value of the variable
            optimum = variable.getMin(); 
            localOptimumFound();
            if (DEBUG) {
                cat.debug(btSolver);
            }
            IntegerDomain domain = optimizingDomain();
            if (mode == CONTINUE) {
                ((PrunableChoicePointStack) btSolver.getChoicePoints())
                    .prune(variable, domain);
                btSolver.backtrack();
            } else { // RESTART
                resetStaticDomains();
                domain.intersect((IntegerDomain) variable.getDomain());
                if (DEBUG) {
                    cat.debug("new domain " +  domain);
                }
                if (domain.isEmpty()) {
                    throw new InconsistencyException();
                }
                variable.setDomain(domain);
                btSolver.initialFilter();
            }
        }
    }
 
    /**
     * This method, used only in mode RESTART, 
     * resets the domains of the variables to their static values 
     * (note that the case of the variable to be optimized is a little bit 
     * more tricky since its static domain changes during the optimization). 
     * @throws InconsistencyException in the general case, 
     * eg if one overrides the method and applies domain adjustments 
     * (note that it is not the case of the default implementation)
     */
    protected void resetStaticDomains() throws InconsistencyException {
        solver.reset();
        if (DEBUG) {
            cat.debug("after reset " + solver);
        }
    }

    //------------------------------------------------------------------------
    // ABSTRACT METHODS
    //------------------------------------------------------------------------
    /**
     * Generates a new domain for the variable to be optimized.
     * @return an integer domain
     */
    public abstract IntegerDomain optimizingDomain();
}
/*
 * $Log$
 * Revision 1.29  2005/08/31 14:14:55  yan
 * reintroduced resetStaticDomains method
 *
 * Revision 1.28  2005/07/19 07:40:38  yan
 * real anytime constraint solving
 *
 * Revision 1.27  2005/07/18 17:48:05  yan
 * fixed jdoc
 *
 * Revision 1.26  2005/07/18 17:26:08  yan
 * introduced TimeOutException + various simplifications
 *
 * Revision 1.25  2005/07/18 15:22:54  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.24  2005/05/24 09:36:54  yan
 * fixed bug in use of resetCounters
 *
 * Revision 1.23  2005/02/02 14:09:40  yan
 * fixed style
 *
 * Revision 1.22  2004/11/09 17:05:16  yan
 * throwing static exceptions
 *
 * Revision 1.21  2004/09/17 14:45:00  yan
 * various cleaning
 *
 * Revision 1.20  2004/09/17 14:18:51  yan
 * various cleaning
 *
 * Revision 1.19  2004/07/12 17:58:24  yan
 * removed logging statement
 *
 * Revision 1.18  2004/06/14 15:54:22  yan
 * using PrunableChoiceStack
 *
 * Revision 1.17  2004/05/05 11:43:42  yan
 * fixed style
 *
 * Revision 1.16  2004/04/09 14:44:14  yan
 * removal of events
 *
 * Revision 1.15  2003/09/21 18:28:47  yan
 * fixed style
 *
 * Revision 1.14  2003/09/21 18:25:25  yan
 * added resetStaticDomains method
 *
 * Revision 1.13  2003/09/21 16:07:12  yan
 * added debug statement
 *
 * Revision 1.12  2003/03/20 15:15:00  yan
 * added anytime optimizer
 *
 * Revision 1.11  2003/03/07 13:53:29  yan
 * fixed style
 *
 * Revision 1.10  2003/01/31 13:52:38  yan
 * removed reset method comment
 *
 * Revision 1.9  2003/01/31 13:46:54  yan
 * added reset method
 *
 * Revision 1.8  2003/01/31 12:19:02  yan
 * changed exception handling
 *
 * Revision 1.7  2003/01/31 12:06:00  yan
 * added intermediate methods
 *
 * Revision 1.6  2002/10/25 09:05:20  yan
 * cleaned category
 *
 * Revision 1.5  2002/10/04 13:17:00  yan
 * added logs
 *
 * Revision 1.4  2002/06/16 13:57:24  yan
 * added javadoc
 *
 * Revision 1.3  2002/05/02 16:14:13  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:44  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
