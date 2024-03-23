package com.koalog.jcs.scheduler;

import java.util.Arrays;
import java.util.Comparator;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.constraint.BaseConstraint;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.variable.IntegerVariable;

/**
 * This class implements the cumulative constraint.
 * 
 * <P>A cumulative constraint allow tasks to overlap, 
 * but the height of the resource is never exceeded.</P>
 *
 * @since 1.3
 * @author Yan Georget
 */
public class Cumulative extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param tasks an array of tasks
     * @param height the height of the resource
     */
    public Cumulative(Task[] tasks, int height) {
        super("cumulative");
        // TODO: share code with Disjunctive
        add(new Cumulative_AUX1(tasks, height));
        // TODO: check this algorithm
        add(new Cumulative_AUX2(tasks, height));
        setVariables(tasks);
    }

    //------------------------------------------------------------------------
    // INNER CLASSES
    //------------------------------------------------------------------------
    /**
     * A first constraint for the cumulative problem: ensure that not too many
     * task are scheduled in a given interval.
     *
     * <P>This constraint is simply a modified version of the
     * AllDifferent constraint (ZHOU version), it is also based on the Hall
     * theorem.
     *
     * @see com.koalog.jcs.constraint.arithmetic.AllDifferent_ZHOU
     * @author Yan Georget
     */
    static class Cumulative_AUX1 extends BaseConstraint {
        private Comparator lb;
        private Comparator ub;
        /** The height of the resource. */
        private int height;
        private TaskDomain[] lbs;
        private TaskDomain[] ubs;

        /**
         * Sole constructor.
         */
        public Cumulative_AUX1(Task[] tasks, int height) {
            super(tasks);
            this.height = height;
            lbs = new TaskDomain[tasks.length];
            ubs = new TaskDomain[tasks.length];
            name = "cumulative_AUX1";            
            complexity = tasks.length*tasks.length;
            idempotent = true;
            // the following code could be static 
            // but it is not allowed in an inner class 
            lb = new Comparator() {
                    public int compare(Object o1, Object o2) {
                        return (((TaskDomain) o1).getReleaseMin() 
                                - ((TaskDomain) o2).getReleaseMin());
                    }
                };
            ub = new Comparator() {
                    public int compare(Object o1, Object o2) {
                        return (((TaskDomain) o1).getDueMax() 
                                - ((TaskDomain) o2).getDueMax());
                    }
                };
        }

        /** @see com.koalog.jcs.constraint.Constraint */
        public void updateDependencies() {
            for (int i=0; i<variables.length; i++) {
                ((IntegerVariable) variables[i]).addMinMaxConstraint(this);
            }
        }

        /** @see com.koalog.jcs.constraint.Constraint */
        public void filter(ChoicePointStack cp, ConstraintScheduler cs) 
            throws ConstraintInconsistencyException {
            for (int i=variables.length; --i>=0;) {
                lbs[i] = ubs[i] = new TaskDomain((Task) variables[i]);
            }
            // initialisations of the sorted arrays of domains 
            Arrays.sort(lbs, lb); 
            Arrays.sort(ubs, ub);
            // filtering of the domains
            contractLB(cp, cs);
            contractUB(cp, cs);
        }
        
        /**
         * Computes and applies the contractors based on 
         * lower bounds of the domains.
         * @param cp a choice point stack
         * @throws ConstraintInconsistencyException 
         * when it detects an inconsistency
         */
        private void contractLB(ChoicePointStack cp, 
                                ConstraintScheduler cs) 
            throws ConstraintInconsistencyException {
            int p; // amount of ressource consumed
            int q; 
            int size; // size of the current interval
            int release;
            for (int i=0; i<variables.length; i++) {
                // release is increasing
                release = lbs[i].getReleaseMin();
                if (i==0 || release != lbs[i-1].getReleaseMin()) {
                    p = 0;
                    q = -1;
                    for (int j=0; j<variables.length; j++) {
                        // the due date is increasing
                        if (release <= ubs[j].getReleaseMin()) {
                            size = (ubs[j].getDueMax() - release)*height;
                            p += ubs[j].getDuration();
                            if (p > size) {
                                fail();
                            }           
                            if (p == size) {
                                q = j;
                            }
                        }
                    }
                    if (q>=0) {
                        applyContractor(cp, 
                                        cs,
                                        release, 
                                        ubs[q].getDueMax()); 
                    }
                }
            } 
        }
        
        /**
         * Computes and applies the contractors based on 
         * upper bounds of the domains.
         * @param cp a choice point stack
         * @throws ConstraintInconsistencyException 
         * when it detects an inconsistency
         */
        private void contractUB(ChoicePointStack cp, 
                                ConstraintScheduler cs) 
            throws ConstraintInconsistencyException {
            int p; // number of domains included in the current interval
            int q; 
            int size; // size of the current interval
            int due;
            for (int i=0; i<variables.length; i++) {
                // due is increasing
                due = ubs[i].getDueMax();
                if (i==0 || due != ubs[i-1].getDueMax()) {
                    p = 0;
                    q = -1;
                    for (int j=variables.length-1; j>=0; j--) {
                        // the release date is decreasing
                        if (lbs[j].getDueMax() <= due) {
                            size = (due - lbs[j].getReleaseMin())*height;
                            p += lbs[j].getDuration();
                            if (p > size) {
                                fail();
                            }           
                            if (p == size) {
                                q = j;
                            }
                        }
                    }
                    if (q>=0) {
                        applyContractor(cp, 
                                        cs,
                                        lbs[q].getReleaseMin(), 
                                        due); 
                    }
                }
            } 
        }
        
        /**
         * Applies a contractor. 
         *
         * @param cp a choice point stack
         * @param release a release date
         * @param due a due date
         * @throws ConstraintInconsistencyException 
         * when it detects an inconsistency
         */
        protected void applyContractor(ChoicePointStack cp,
                                       ConstraintScheduler cs,
                                       int release, 
                                       int due) 
            throws ConstraintInconsistencyException {
            for (int l=variables.length; --l>=0;) {
                Task t = (Task) variables[l];
                if (t.getReleaseMin()<release && t.getReleaseMax()<due) {
                    t.adjustMax(cp, 
                                cs,
                                this,
                                release - t.getDuration());
                } else if (release<t.getDueMin() && due<t.getDueMax()) { 
                    t.adjustMin(cp, 
                                cs,
                                this,
                                due);
                } 
            }
        } 
    }

    /**
     * A second constraint for the cumulative problem: ensure that tasks do
     * not overlap other tasks' fixed parts.
     *
     * @author Yan Georget
     */
    static class Cumulative_AUX2 extends BaseConstraint {
        private Comparator lb;
        private Comparator ub;
        /** The height of the resource. */
        private int height;
        private TaskDomain[] lbs;
        private TaskDomain[] ubs;

        /**
         * Sole constructor.
         */
        public Cumulative_AUX2(Task[] tasks, int height) {
            super(tasks);
            this.height = height;
            lbs = new TaskDomain[tasks.length];
            ubs = new TaskDomain[tasks.length];
            name = "cumulative_AUX2";
            complexity = tasks.length*tasks.length;
            idempotent = false;
            // the following code could be static 
            // but it is not allowed in an inner class 
            lb = new Comparator() {
                    public int compare(Object o1, Object o2) {
                        return (((TaskDomain) o1).getReleaseMax() 
                                - ((TaskDomain) o2).getReleaseMax());
                    }
                };
            ub = new Comparator() {
                    public int compare(Object o1, Object o2) {
                        return (((TaskDomain) o1).getDueMin() 
                                - ((TaskDomain) o2).getDueMin());
                    }
                };
        }

        /** @see com.koalog.jcs.constraint.Constraint */
        public void updateDependencies() {
            for (int i=0; i<variables.length; i++) {
                ((IntegerVariable) variables[i]).addMinMaxConstraint(this);
            }
        }

        /** @see com.koalog.jcs.constraint.Constraint */
        public void filter(ChoicePointStack cp, ConstraintScheduler cs) 
            throws ConstraintInconsistencyException {
            for (int i=0; i<variables.length; i++) {
                lbs[i] = ubs[i] = new TaskDomain((Task) variables[i]);
            }
            // initialisations of the sorted arrays of domains 
            Arrays.sort(lbs, lb); 
            Arrays.sort(ubs, ub);
            // filtering of the domains
            contractLB(cp, cs);
            contractUB(cp, cs);
        }
        
        /**
         * Computes and applies the contractors based on 
         * lower bounds of the domains.
         * @param cp a choice point stack
         * @throws ConstraintInconsistencyException 
         * when it detects an inconsistency
         */
        private void contractLB(ChoicePointStack cp, 
                                ConstraintScheduler cs) 
            throws ConstraintInconsistencyException {
            int p;
            int q;
            int release;
            for (int i=0; i<variables.length; i++) {
                // release is increasing
                release = lbs[i].getReleaseMax();
                if (i==0 || release != lbs[i-1].getReleaseMax()) {
                    p = 0;
                    q = -1;
                    for (int j=variables.length -1; 
                         j>=0 && release < ubs[j].getDueMin(); 
                         j--) {
                        // the due date is decreasing
                        if (ubs[j].getReleaseMax() <= release) {
                            if (++p > height) {
                                fail();
                            }
                            if (p == height) {
                                q = j;
                                break;
                            }
                        }
                    }
                    if (q >= 0) {
                        applyContractor(cp, 
                                        cs,
                                        release, 
                                        ubs[q].getDueMin());
                    }
                }
            }
        }

        /**
         * Computes and applies the contractors based on 
         * upper bounds of the domains.
         * @param cp a choice point stack
         * @throws ConstraintInconsistencyException 
         * when it detects an inconsistency
         */
        private void contractUB(ChoicePointStack cp, 
                                ConstraintScheduler cs) 
            throws ConstraintInconsistencyException {
            int p;
            int q;
            int due;
            for (int i=variables.length-1; i>=0; i--) {
                // due is decreasing
                due = ubs[i].getDueMin();
                if (i==0 || due != ubs[i-1].getDueMin()) {
                    p = 0;
                    q = -1;
                    for (int j=0; 
                         j<variables.length && lbs[j].getReleaseMax() < due; 
                         j++) {
                        // the release date is increasing
                        if (due <= lbs[j].getDueMin()) { 
                            if (++p > height) {
                                fail();
                            }
                            if (p == height) {
                                q = j;
                                break;
                            }
                        }
                    }
                    if (q >= 0) {
                        applyContractor(cp, 
                                        cs, 
                                        lbs[q].getReleaseMax(), 
                                        due);      
                    }
                }
            } 
        }
        
        /**
         * Applies a contractor. 
         *
         * @param cp a choice point stack
         * @param release a release date
         * @param due a due date
         * @throws ConstraintInconsistencyException 
         * when it detects an inconsistency
         */
        protected void applyContractor(ChoicePointStack cp,
                                       ConstraintScheduler cs,
                                       int release, 
                                       int due) 
            throws ConstraintInconsistencyException {
            for (int l=0; l<variables.length; l++) {
                Task t = (Task) variables[l];
                if (t.getReleaseMax() <= release && t.getDueMin() < due) { 
                    // the task ends before the computed fixed part
                    t.adjustMax(cp, 
                                cs,
                                this,
                                release-t.getDuration());
                } else if (release < t.getReleaseMax() 
                           && due <= t.getDueMin()) {
                    // the task starts after the computed fixed part
                    t.adjustMin(cp, 
                                cs, 
                                this,
                                due);
                }
            }
        } 
    }
}
/*
 * $Log$
 * Revision 1.13  2005/07/18 17:36:50  yan
 * fixed style
 *
 * Revision 1.12  2005/07/18 15:22:54  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.11  2004/11/09 17:05:42  yan
 * throwing static exceptions
 *
 * Revision 1.10  2004/11/02 13:54:28  yan
 * optimized for loops
 *
 * Revision 1.9  2004/09/17 12:53:10  yan
 * renamed updateConstraints
 *
 * Revision 1.8  2004/09/17 10:06:55  yan
 * changed internalAdd into add
 *
 * Revision 1.7  2004/07/13 23:15:45  yan
 * fixed style
 *
 * Revision 1.6  2004/06/03 16:49:14  yan
 * added @since tag
 *
 * Revision 1.5  2004/04/09 14:44:04  yan
 * removal of events
 *
 * Revision 1.4  2003/10/02 23:50:50  yan
 * introduced complexity computation
 *
 * Revision 1.3  2003/08/15 10:10:03  yan
 * fixed javadoc
 *
 * Revision 1.2  2003/04/03 17:22:46  yan
 * fixed style
 *
 * Revision 1.1  2003/04/03 15:13:27  yan
 * initial revision
 *
 */
