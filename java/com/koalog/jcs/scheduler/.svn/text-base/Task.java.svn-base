package com.koalog.jcs.scheduler;

import com.koalog.jcs.domain.IntegerDomain;
import com.koalog.jcs.variable.IntegerVariable;

/**
 * This class implements a task.
 *
 * <P>A task is defined by its release date and its duration.
 * The due date is defined by:
 * <CODE>dueDate = releaseDate + duration</CODE>.
 *
 * @author Yan Georget
 */    
public class Task extends IntegerVariable {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The duration of the task. */
    protected int duration;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Main constructor.
     * @param name the name of the task
     * @param duration the task duration
     * @param lb a lower bound of the start date
     * @param ub an upper bound of the due date
     */
    public Task(String name, int duration, int lb, int ub) {
        super(name, lb, ub-duration);
        this.duration = duration;
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Returns the slack of the task.
     * @return an int
     */
    public final int getSlack() {
        return ((IntegerDomain) getDomain()).size();
    }

    /**
     * Returns the minimal release date of the task.
     * @return an int
     */
    public final int getReleaseMin() {
        return getMin();
    }
    
    /**
     * Returns the maximal release date of the task.
     * @return an int
     */
    public final int getReleaseMax() {
        return getMax();
    }

    /**
     * Returns the minimal due date of the task.
     * @return an int
     */
    public final int getDueMin() {
        return getMin()+getDuration();
    }
    
    /**
     * Returns the maximal due date of the task.
     * @return an int
     */
    public final int getDueMax() {
        return getMax()+getDuration();
    }

    /** 
     * Returns the task as a string.
     * @return a string
     */
    public String toString() {
        StringBuffer buf = new StringBuffer(super.toString());
        buf.append("<");
        buf.append(duration);
        buf.append(">");
        return buf.toString();
    }

    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------
    /** 
     * Gets the task duration.
     * @return an integer
     */
    public final int getDuration() {
        return duration;
    }

    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    /**
     * Returns a set of task running on the same machine as a string.
     * @param task an array of task
     * @param ub an upper bound for the due date of all tasks 
     * @return a string 
     */
    public static String toString(Task[] task, int ub) {
        StringBuffer buf = new StringBuffer();
        for (int l=0; l<ub; l++) {
            int v = 0;
            for (int j=0; j<task.length; j++) {
                if (task[j].getReleaseMax() <= l 
                    &&  l < task[j].getDueMin()) {
                    if (v > 0) {
                        //cat.error(task[j] + " from " + (v-1) + " to " + j);
                        throw new RuntimeException();
                    } else {
                        v = j+1;
                    }
                } else if (task[j].getReleaseMin() <= l  
                           &&  l < task[j].getDueMax()) {
                    if (v == 0) {
                        v = -j-1;
                    }
                }
            } 
            if (v == 0) {
                buf.append(" ");
            } else if (v < 0) {
                buf.append("?");
            } else {
                buf.append(v-1);
            }
        }
        return buf.toString();
    }

    /**
     * Returns the slack of an array of tasks.
     * @param task an array of task
     * @return an int
     * @since 2.2
     */
    public static int getSlack(Task[] task) {
        int n = task.length; 
        int dur = 0;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int j=n; --j>=0;) {
            dur += task[j].getDuration();
            if (task[j].getReleaseMin() < min) {
                min = task[j].getReleaseMin();
            }
            if (task[j].getDueMax() > max) {
                max = task[j].getDueMax();
            }
        }
        return max - min - dur;
    }

    /**
     * Returns the slack of a task interval.
     * @param task an array of task
     * @param j1 the index of the lower bound of the task interval
     * @param j2 the index of the upper bound of the task interval
     * @return an int
     * @since 2.2
     */
    public static int getSlack(Task[] task, int j1, int j2) {
        int n = task.length; 
        int dur = 0;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int j=n; --j>=0;) { 
            if (task[j1].getReleaseMin() <= task[j].getReleaseMin()
                && task[j].getDueMax() <= task[j2].getDueMax()) {
                dur += task[j].getDuration();
                if (task[j].getReleaseMin() < min) {
                    min = task[j].getReleaseMin();
                }
                if (task[j].getDueMax() > max) {
                    max = task[j].getDueMax();
                }
            }
        }
        return max - min - dur;
    }
    
    /**
     * Returns the minimal value (discarding 0 value) of the slacks 
     * of the task intervals corresponding to an array of tasks.
     * @param task an array of task
     * @return an int
     * @since 2.2
     */
    public static int getLocalSlack(Task[] task) {
        int n = task.length; 
        int localSlack = Integer.MAX_VALUE;
        for (int j1=0; j1<n-1; j1++) {
            for (int j2=j1; j2<n; j2++) {
                int slack = Integer.MAX_VALUE;
                if (task[j1].getReleaseMin() <= task[j2].getReleaseMin()
                    && task[j1].getDueMax() <= task[j2].getDueMax()) {
                    slack = getSlack(task, j1, j2);
                } else if (task[j2].getReleaseMin() <= task[j1].getReleaseMin()
                           && task[j2].getDueMax() <= task[j1].getDueMax()) {
                    slack = getSlack(task, j2, j1);
                }
                if (slack != 0 && slack < localSlack) {
                    localSlack = slack;
                }
            }            
        }
        return localSlack;
    }
}
/*
 * $Log$
 * Revision 1.17  2004/11/02 13:54:28  yan
 * optimized for loops
 *
 * Revision 1.16  2004/08/25 14:17:40  yan
 * fixed style
 *
 * Revision 1.15  2004/07/19 13:33:21  yan
 * fixed updateLocalStack
 *
 * Revision 1.14  2004/07/19 09:20:20  yan
 * added a method to display a machine
 *
 * Revision 1.13  2004/07/16 14:32:27  yan
 * cleaned code
 *
 * Revision 1.12  2004/07/13 23:15:37  yan
 * fixed style
 *
 * Revision 1.11  2004/07/13 22:21:34  yan
 * added many static methods
 *
 * Revision 1.10  2004/07/10 16:08:23  yan
 * now extends IntegerVariable
 *
 * Revision 1.9  2004/05/06 09:54:06  yan
 * fixed toString method
 *
 * Revision 1.8  2003/08/15 10:10:04  yan
 * fixed javadoc
 *
 * Revision 1.7  2003/04/03 15:19:14  yan
 * fixed style
 *
 * Revision 1.6  2003/04/03 15:08:57  yan
 * changed Task API
 *
 * Revision 1.5  2003/02/25 11:26:15  yan
 * fixed bug concerning due date in new methods (precede, noOverlap)
 *
 * Revision 1.4  2003/02/18 15:31:57  yan
 * added accessors and constructors
 *
 * Revision 1.3  2002/12/11 17:46:02  yan
 * fixed javadoc
 *
 * Revision 1.2  2002/12/09 13:12:24  yan
 * refactoring
 *
 */
