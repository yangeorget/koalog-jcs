package com.koalog.jcs.examples;

import com.koalog.jcs.variable.SetVariable;
import com.koalog.jcs.solver.BacktrackSolver;
import com.koalog.jcs.domain.SetDomain;

/**
 * A solver for the golfers problem.
 *
 * @author Yan Georget
 */
public class GolfersSolver extends BacktrackSolver {
    SetVariable[][] group;
    
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /** 
     * Sole constructor
     * @param p a golfers problem
     */
    public GolfersSolver(GolfersProblem p) {
        super(p);
        this.group = p.group;
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.solver.BacktrackSolver */
    public boolean choice() {
        // allocates the golfers by numbers to the foursomes
        for (int i=1; i<=GolfersProblem.GOLFERS_NB; i++) {
            Integer j = new Integer(i);
            for (int d=0; d<group.length; d++) {
                for (int g=0; g<GolfersProblem.GROUP_NB; g++) {
                    if (((SetDomain) group[d][g].getDomain())
                        .isPossibleElement(j)) {
                        choicePoints.push();
                        group[d][g].chooseMinByAdding(choicePoints, 
                                                      constraintScheduler, 
                                                      j);
                        return true;
                    } 
                }
            }
        }
        return false;
    }
}
