package com.koalog.jcs.examples;

import java.util.StringTokenizer;
import org.apache.log4j.Category;
import com.koalog.jcs.domain.IntegerDomain;
import com.koalog.jcs.scheduler.Task;
import com.koalog.jcs.scheduler.Disjunctive;
import com.koalog.jcs.solver.BaseVariableHeuristic;
import com.koalog.jcs.solver.IncreasingOrderDomainHeuristic;
import com.koalog.jcs.solver.KeepOrderVariableHeuristic;
import com.koalog.jcs.solver.SplitLowDomainHeuristic;
import com.koalog.jcs.solver.SplitSolver;
import com.koalog.jcs.variable.BaseVariable;
import com.koalog.jcs.variable.Variable;

/**
 * This is a solver for the JSSP.
 *
 * @author Yan Georget
 */    
public class JobShopSolver extends SplitSolver {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(JobShopSolver.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The critical machine. */
    int criticalMachine;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param problem a JSSP
     */
    public JobShopSolver(JobShopProblem problem) {
        super(problem);
        criticalMachine = -1;
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Returns a critical machine.
     * @return a machine number
     */
    private int getCriticalMachine() {
        JobShopProblem p = (JobShopProblem) problem;
        int machine = -1;
        int slack = Integer.MAX_VALUE;
        int localSlack = Integer.MAX_VALUE;
        for (int i=0; i<p.m; i++) {
            if (BaseVariable.notAllInstantiated(p.order[i])) {
                int l = Task.getLocalSlack(p.task[i]);
                if (l < localSlack) {
                    localSlack = l;
                    machine = i;
                } else if (l == localSlack) {
                    int s = Task.getSlack(p.task[i]);
                    if (s < slack) {
                        slack = s;
                        machine = i;
                    }
                }
            }
        }
// better than:
//         int disorder = Integer.MAX_VALUE;
//         int localDisorder = Integer.MAX_VALUE;
//         for (int i=0; i<p.m; i++) {
//             if (BaseVariable.notAllInstantiated(p.order[i])) {
//                 int l = Disjunctive.getLocalDisorder(p.order[i]);
//                 if (l < localDisorder) {
//                     localDisorder = l;
//                     machine = i;
//                 } else if (l == localDisorder) {
//                     int s = Disjunctive.getDisorder(p.order[i]);
//                     if (s < disorder) {
//                         disorder = s;
//                         machine = i;
//                     }
//                 }
//             }
//         }
        return machine;
    }

    /** @see com.koalog.jcs.solver.BacktrackSolver */
    public boolean choice() {
        JobShopProblem p = (JobShopProblem) problem;
        // we keep the critical machine until its tasks are ordered
        if (criticalMachine == -1 
            || BaseVariable.allInstantiated(p.order[criticalMachine])) {
            criticalMachine = getCriticalMachine();
        }
        if (criticalMachine != -1) {
            setVariableHeuristic(new JobShopVariableHeuristic(p));
            setDomainHeuristic(new SplitLowDomainHeuristic());
            // we split the domain of the selected task
            return super.choice(p.task[criticalMachine]);
        } else {
            setVariableHeuristic(new KeepOrderVariableHeuristic());
            setDomainHeuristic(new IncreasingOrderDomainHeuristic());
            // then we instantiate the dates and the makespan
            return super.choice(p.getVariables());
        }
    }

    //------------------------------------------------------------------------
    // INNER CLASSES
    //------------------------------------------------------------------------
    /**
     * A variable heuristic for the JobShop problem.
     * @author Yan Georget
     */
    static class JobShopVariableHeuristic extends BaseVariableHeuristic {
        /**
         * Sole constructor.
         * @param problem a jobshop problem
         */
        JobShopVariableHeuristic(JobShopProblem problem) {
            super(problem);
        }

        /** @see com.koalog.jcs.solver.BaseVariableHeuristic */
        public int eval(Variable var) {
            JobShopProblem p = (JobShopProblem) problem;
            StringTokenizer tks = new StringTokenizer(var.getName(), "_");
            int machine = Integer.parseInt(tks.nextToken());
            int j = Integer.parseInt(tks.nextToken());
            int disorder = Disjunctive.getDisorder(p.order[machine], j);
            if (disorder == 0) {
                // the task is fully ordered
                return Integer.MIN_VALUE;
            } else {
                return (disorder)*p.ub  
                    + ((IntegerDomain) var.getDomain()).size();
            }
        }
    }
}

