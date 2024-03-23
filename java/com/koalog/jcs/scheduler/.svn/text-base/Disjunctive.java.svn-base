package com.koalog.jcs.scheduler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.constraint.BaseConstraint;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.constraint.bool.IsSmaller_3;
import com.koalog.jcs.variable.BaseVariable;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.variable.IntegerVariable;

/**
 * A constraint for disjunctive scheduling of tasks on a single resource.
 *
 * @since 2.2
 * @author Yan Georget
 */
public class Disjunctive extends BaseProblem {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The number of tasks to be scheduled. */
    final int n;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param task an array of tasks
     * @param order an array of order variables
     */
    public Disjunctive(Task[] task, BooleanVariable[] order) {
        super("disjunctive");
        n = task.length;
        add(new Disjunctive_AUX1(task));
        add(new Disjunctive_AUX4(task));
        for (int j1=0; j1<n-1; j1++) {
            for (int j2=j1+1; j2<n; j2++) {
                int o12 = orderIndex(n, j1,j2);
                // order[o12] <=> 
                // task[j1].getDueDate <= task[j1].getReleaseDate
                add(new IsSmaller_3(order[o12], 
                                            task[j1],
                                            task[j2],
                                            1-task[j1].getDuration()));
                // order[o12] <=> 
                // task[j1].getReleaseDate < task[j1].getDueDate
                add(new IsSmaller_3(order[o12], 
                                            task[j1], 
                                            task[j2],
                                            task[j2].getDuration()));
            }
        }
        // useless...
        // for (int j1=0; j1<n-2; j1++) {
        //     for (int j2=j1+1; j2<n-1; j2++) {
        //        int o12 = orderIndex(n, j1,j2);
        //        for (int j3=j2+1; j3<n; j3++) {
        //             int o23 = orderIndex(n, j2,j3);
        //             int o13 = orderIndex(n, j1,j3);
        //             BooleanVariable and13 = new BooleanVariable();
        //             internalAdd(new And(and13, order[o12], order[o23]));
        //             internalAdd(new Leq(and13, order[o13]));
        //             BooleanVariable or13 = new BooleanVariable();
        //             internalAdd(new Or(or13, order[o12], order[o23]));
        //             internalAdd(new Leq(order[o13], or13));
        //         }
        //     }
        // }
        for (int j=0; j<n; j++) {
            add(new Disjunctive_AUX2(j, task, order));
            add(new Disjunctive_AUX3(j, task, order));
        }
        List vars = new ArrayList(n+order.length);
        vars.addAll(Arrays.asList(task));
        vars.addAll(Arrays.asList(order));
        variables = (IntegerVariable[]) vars.toArray(new IntegerVariable[]{});
    }

    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    /**
     * Returns the index of the order variable corresponding to (j1, j2).
     * @param n the number of tasks
     * @param j1 the index of a task
     * @param j2 the index of a task
     * @return <CODE>n*(n-1)/2 -(n-j1)*(n-j1-1)/2 +j2-1-j1</CODE>
     * @since 2.2
     */
    public static final int orderIndex(int n, int j1, int j2) {
        return j1*n - j1*(j1+3)/2 + j2 - 1;
        
    }

    /**
     * Returns the number of tasks given the number of order variables.
     * @param nb an int
     * @return the greatest solution of <CODE>(n-1)*n/2=nb</CODE>
     * @since 2.2
     */
    public static int getTaskNb(int nb) {
        return (int) (Math.sqrt(2*nb + 0.25) + 0.5);
    }

    /**
     * Returns the disorder of an array of order variables.
     * @param order an array of order variables
     * @return an int
     * @since 2.2
     */
    public static int getDisorder(BooleanVariable[] order) {
        return BaseVariable.nbNotInstantiated(order);
    }

    /**
     * Returns the disorder of a task.
     * @param order an array of order variables
     * @param j0 the index of a task
     * @return an int
     * @since 2.2
     */
    public static int getDisorder(BooleanVariable[] order, int j0) {
        int n = getTaskNb(order.length);
        int disorder = 0;
        for (int j=0; j<j0; j++) {
            if (order[orderIndex(n, j, j0)].isNotInstantiated()) {
                disorder++;
            }
        }
        for (int j=j0+1; j<n; j++) {
            if (order[orderIndex(n, j0, j)].isNotInstantiated()) {
                disorder++;
            }
        }
        return disorder;
    }
    
    /**
     * Returns the minimal value (discarding 0 value) of the disorders
     * of the tasks.
     * @param order an array of order variables
     * @return an int
     * @since 2.2
     */
    public static int getLocalDisorder(BooleanVariable[] order) {
        int n = getTaskNb(order.length);
        int localDisorder = Integer.MAX_VALUE;
        for (int j0=0; j0<n; j0++) {
            int disorder = getDisorder(order, j0);
            if (disorder != 0 && disorder < localDisorder) {
                localDisorder = disorder;
            }            
        }
        return localDisorder;
    }

    //------------------------------------------------------------------------
    // INNER CLASSES
    //------------------------------------------------------------------------
    /**
     * A constraint to ensure that not too many
     * task are scheduled in a given interval.
     *
     * <P>This constraint is based on the Hall theorem: 
     * it is simply a modified version of the AllDifferent constraint 
     * (ZHOU version) taking into account the durations of the tasks. 
     *
     * <P>This constraint enforces <EM>edge-finding</EM>.
     *
     * @see com.koalog.jcs.constraint.arithmetic.AllDifferent_ZHOU
     * @author Yan Georget
     */
    class Disjunctive_AUX1 extends BaseConstraint {
        // TODO: write a nlog(n) version 
        private Comparator lb;
        private Comparator ub;
        private TaskDomain[] lbs;
        private TaskDomain[] ubs;

        /**
         * Sole constructor.
         * @param task an array of tasks
         */
        public Disjunctive_AUX1(Task[] task) {
            super(task);
            lbs = new TaskDomain[n];
            ubs = new TaskDomain[n];
            name = "disjunctive_AUX1";            
            complexity = n*n;
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
            for (int i=n; --i>=0;) {
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
         * @throws InconsistencyException when it detects an inconsistency
         */
        private void contractLB(ChoicePointStack cp, 
                                ConstraintScheduler cs) 
            throws ConstraintInconsistencyException {
            int p; // amount of ressource consumed
            int q; 
            int size; // size of the current interval
            int release;
            for (int i=0; i<n; i++) {
                // release is increasing
                release = lbs[i].getReleaseMin();
                if (i==0 || release != lbs[i-1].getReleaseMin()) {
                    p = 0;
                    q = -1;
                    for (int j=0; j<n; j++) {
                        // the due date is increasing
                        if (release <= ubs[j].getReleaseMin()) {
                            size = ubs[j].getDueMax() - release;
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
         * @throws InconsistencyException when it detects an inconsistency
         */
        private void contractUB(ChoicePointStack cp, 
                                ConstraintScheduler cs) 
            throws ConstraintInconsistencyException {
            int p; // number of domains included in the current interval
            int q; 
            int size; // size of the current interval
            int due;
            for (int i=0; i<n; i++) {
                // due is increasing
                due = ubs[i].getDueMax();
                if (i==0 || due != ubs[i-1].getDueMax()) {
                    p = 0;
                    q = -1;
                    for (int j=n-1; j>=0; j--) {
                        // the release date is decreasing
                        if (lbs[j].getDueMax() <= due) {
                            size = due - lbs[j].getReleaseMin();
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
        private void applyContractor(ChoicePointStack cp,
                                       ConstraintScheduler cs,
                                       int release, 
                                       int due) 
            throws ConstraintInconsistencyException {
            for (int l=n; --l>=0;) {
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
     * A constraint to adjust the release date of a task 
     * using the order variables.
     *
     * <P>This constraint is based on a idea of Jianyang Zhou 
     * as described in his PhD thesis (LIM, France).
     *
     * @author Yan Georget
     */
    class Disjunctive_AUX2 extends BaseConstraint { 
        private Task[] task;
        private int j;

        /**
         * Sole constructor.
         * @param j the index of a task
         * @param task an array of task
         * @param order the order variables
         */
        public Disjunctive_AUX2(int j, Task[] task, BooleanVariable[] order) {
            super();
            this.task = task;
            this.j = j;
            complexity = n;
            idempotent = true; 
            name = "disjunctive_AUX2";
            variables = new IntegerVariable[2*n-1];
            for (int j1=0; j1<n; j1++) {
                variables[j1] = task[j1];
            }
            for (int j1=0; j1<j; j1++) {
                variables[n+j1] = order[orderIndex(n, j1, j)];
            }
            for (int j1=j+1; j1<n; j1++) {
                variables[n+j1-1] = order[orderIndex(n, j, j1)];
            }
        }
        
        /** @see com.koalog.jcs.constraint.Constraint */
        public void updateDependencies() {
            for (int i=0; i<n; i++) {
                if (i != j) {
                    task[i].addMinConstraint(this);
                }
            }
            for (int j1=n; j1<2*n-1; j1++) {
                ((BooleanVariable) variables[j1]).addGroundConstraint(this);
            }
        }
        
        /** @see com.koalog.jcs.constraint.Constraint */
        public void filter(ChoicePointStack cp, ConstraintScheduler cs) 
            throws ConstraintInconsistencyException {
            int min = task[j].getReleaseMin();
            int dur = 0;
            for (int k=n; k<j+n; k++) {
                if (((BooleanVariable) variables[k]).isTrue()) {
                    Task t = task[k-n];
                    int rmin = t.getReleaseMin();
                    if (rmin < min) {
                        min = rmin;
                    }
                    dur += t.getDuration();
                } 
            }
            for (int k=j+n; k<2*n-1; k++) {
                if (((BooleanVariable) variables[k]).isFalse()) {
                    Task t = task[k-n+1];
                    int rmin = t.getReleaseMin();
                    if (rmin < min) {
                        min = rmin;
                    }
                    dur += t.getDuration();
                } 
            }
            task[j].adjustMin(cp, 
                              cs, 
                              this, 
                              min + dur);
        }
    }
    
    /**
     * A constraint to adjust the due date of a task 
     * using the order variables.
     *
     * <P>This constraint is based on a idea of Jianyang Zhou 
     * as described in his PhD thesis (LIM, France).
     *
     * @author Yan Georget
     */
    class Disjunctive_AUX3 extends BaseConstraint { 
        private Task[] task;
        private int j;
        
        /**
         * Sole constructor.
         * @param j the index of a task
         * @param task an array of task
         * @param order the order variables
         */
        public Disjunctive_AUX3(int j, Task[] task, BooleanVariable[] order) {
            super();
            this.task = task;
            this.j = j;
            complexity = n;
            idempotent = true; 
            name = "disjunctive_AUX3";
            variables = new IntegerVariable[2*n-1];
            for (int j1=0; j1<n; j1++) {
                variables[j1] = task[j1];
            }
            for (int j1=0; j1<j; j1++) {
                variables[n+j1] = order[orderIndex(n, j1, j)];
            }
            for (int j1=j+1; j1<n; j1++) {
                variables[n+j1-1] = order[orderIndex(n, j, j1)];
            }
        }
        
        /** @see com.koalog.jcs.constraint.Constraint */
        public void updateDependencies() {
            for (int i=0; i<task.length; i++) {
                if (i != j) {
                    task[i].addMaxConstraint(this);
                }
            }
            for (int j1=n; j1<2*n-1; j1++) {
                ((BooleanVariable) variables[j1]).addGroundConstraint(this);
            }
        }
        
        /** @see com.koalog.jcs.constraint.Constraint */
        public void filter(ChoicePointStack cp, ConstraintScheduler cs) 
            throws ConstraintInconsistencyException {
            int max = task[j].getDueMax();
            int dur = 0;
            for (int k=n; k<j+n; k++) {
                if (((BooleanVariable) variables[k]).isFalse()) {
                    Task t = task[k-n];
                    int dmax = t.getDueMax();
                    if (dmax > max) {
                        max = dmax;
                    }
                    dur += t.getDuration();
                }
            }
            for (int k=j+n; k<2*n-1; k++) {
                if (((BooleanVariable) variables[k]).isTrue()) {
                    Task t = task[k-n+1];
                    int dmax = t.getDueMax();
                    if (dmax > max) {
                        max = dmax;
                    }
                    dur += t.getDuration();
                }
            }
            task[j].adjustMax(cp, 
                              cs, 
                              this, 
                              max - dur - task[j].getDuration());
        }
    }
    
    /**
     * A constraint to enforce the rules: not-first, not-last.
     *
     * <P>This constraint is based on a idea of Caseau and Laburthe.
     *
     * @author Yan Georget
     */
    class Disjunctive_AUX4 extends BaseConstraint {
        /** The tasks. */
        private Task[] task;
        /** 
         * A reusable collection to store the tasks belonging to an interval. 
         */
        private Collection tasks;
        /**
         * A comparator to sort the tasks 
         * according to their minimal release dates.
         */
        private Comparator lb;
        /**
         * A comparator to sort the tasks 
         * according to their maximal due dates.
         */
        private Comparator ub;
        private TaskDomain[] lbs;
        private TaskDomain[] ubs;

        /**
         * Sole constructor.
         * @param task an array of tasks
         */
        public Disjunctive_AUX4(Task[] task) {
            super(task);
            this.task = task;
            name = "disjunctive_AUX4";            
            complexity = n*n*n*n;
            idempotent = false;
            tasks = new LinkedList();
            lbs = new TaskDomain[n];
            ubs = new TaskDomain[n];
            // the following code could be static 
            // but it is not allowed in an inner class 
            lb = new Comparator() {
                    public int compare(Object o1, Object o2) {
                        TaskDomain d1 = (TaskDomain) o1;
                        TaskDomain d2 = (TaskDomain) o2;
                        int d = d1.getReleaseMin()-d2.getReleaseMin();
                        if (d != 0) {
                            return d;
                        } else {
                            return d1.getDueMax()-d2.getDueMax();
                        } 
                    }
                };
            ub = new Comparator() {
                    public int compare(Object o1, Object o2) {
                        TaskDomain d1 = (TaskDomain) o1;
                        TaskDomain d2 = (TaskDomain) o2;
                        int d = d2.getDueMax()-d1.getDueMax();
                        if (d != 0) {
                            return d;
                        } else {
                            return d2.getReleaseMin()-d1.getReleaseMin();
                        } 
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
            for (int i=0; i<n; i++) {
                lbs[i] = ubs[i] = new TaskDomain((Task) variables[i]);
            }
            // initialisations of the sorted arrays of domains 
            Arrays.sort(lbs, lb); 
            Arrays.sort(ubs, ub);
            for (int i=0; i<n; i++) {
                int iMin = lbs[i].getReleaseMin();
                if (i==0 || iMin > lbs[i-1].getReleaseMin()) {
                    int iMax = lbs[i].getDueMax();
                    for (int j=0; j<n; j++) {
                        int jMax = ubs[j].getDueMax();
                        if ((j==0 || jMax < ubs[j-1].getDueMax()) 
                            && iMax <= jMax 
                            && iMin <= ubs[j].getReleaseMin()) {
                            filter(cp, cs, iMin, jMax);
                        }
                    }
                }
            }
        }

        /**
         * Filters the domains of the tasks belonging to an interval.
         * @param cp the choice point stack
         * @param cs the constraint scheduler
         * @param releaseMin the min of the interval
         * @param dueMax the max of the interval
         */
        private void filter(ChoicePointStack cp, 
                            ConstraintScheduler cs, 
                            int releaseMin,
                            int dueMax) 
            throws ConstraintInconsistencyException {
            int duration = 0;
            tasks.clear();
            for (int k=0; k<n; k++) {
                Task t = task[k];
                if (releaseMin<=t.getReleaseMin() && t.getDueMax()<=dueMax) {
                    tasks.add(t);
                    duration += t.getDuration();
                }
            }
            int dueMin = releaseMin + duration;
            int releaseMax = dueMax - duration;
            Iterator l = tasks.iterator();
            while (l.hasNext()) {
                Task t = (Task) l.next();
                if (t.getReleaseMin() > releaseMax) { // can not be first
                    t.adjustMin(cp, 
                                cs, 
                                this, 
                                due(tasks, 
                                    t, 
                                    t.getReleaseMin() - releaseMax, 
                                    dueMin));
                }                
                if (dueMin > t.getDueMax()) { // can not be last
                    t.adjustMax(cp, 
                                cs, 
                                this, 
                                release(tasks, 
                                        t, 
                                        dueMin - t.getDueMax(), 
                                        releaseMax) - t.getDuration());
                }
            }
        }
        
        private int due(Collection tasks, Task t, int d, int due) {
            int v = Integer.MAX_VALUE;
            Iterator m = tasks.iterator();
            while (m.hasNext()) {
                Task t1 = (Task) m.next();
                if (t1 != t) {
                    int v1 = t1.getDueMin();
                    if (v1 < v) {
                        int d1= t1.getDuration();
                        if (d1 >= d) {
                            v = v1;
                        } else {
                            d -= d1;
                        }
                    }
                }
            }
            return v == Integer.MAX_VALUE ? due : v;
        }

        private int release(Collection tasks, Task t, int d, int release) {
            int v = Integer.MIN_VALUE;
            Iterator m = tasks.iterator();
            while (m.hasNext()) {
                Task t1 = (Task) m.next();
                if (t1 != t) {
                    int v1 = t1.getReleaseMax();
                    if (v1 > v) {
                        int d1= t1.getDuration();
                        if (d1 >= d) {
                            v = v1;
                        } else {
                            d -= d1;
                        }
                    }
                }
            }
            return v == Integer.MIN_VALUE ? release : v;
        }
    }
}
/*
 * $Log$
 * Revision 1.18  2005/07/18 17:36:50  yan
 * fixed style
 *
 * Revision 1.17  2005/07/18 15:22:54  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.16  2004/11/29 09:32:24  yan
 * using isNotInstantiated
 *
 * Revision 1.15  2004/11/09 17:05:42  yan
 * throwing static exceptions
 *
 * Revision 1.14  2004/11/02 13:54:28  yan
 * optimized for loops
 *
 * Revision 1.13  2004/09/17 12:53:10  yan
 * renamed updateConstraints
 *
 * Revision 1.12  2004/09/17 10:06:55  yan
 * changed internalAdd into add
 *
 * Revision 1.11  2004/07/22 12:12:23  yan
 * added methods about disorder
 *
 * Revision 1.10  2004/07/21 14:18:55  yan
 * removed useless constraints
 *
 * Revision 1.9  2004/07/20 14:03:38  yan
 * fixed style
 *
 * Revision 1.8  2004/07/20 13:17:22  yan
 * cleaned code
 *
 * Revision 1.7  2004/07/20 09:36:06  yan
 * optimized
 *
 * Revision 1.6  2004/07/20 08:57:11  yan
 * added not-first not-last
 *
 * Revision 1.5  2004/07/19 14:33:41  yan
 * optimized a bit
 *
 * Revision 1.4  2004/07/19 08:03:25  yan
 * added comments
 *
 * Revision 1.3  2004/07/15 15:32:13  yan
 * optimized a bit
 *
 */
