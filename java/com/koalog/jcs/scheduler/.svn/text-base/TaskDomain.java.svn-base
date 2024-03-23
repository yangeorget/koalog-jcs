package com.koalog.jcs.scheduler;

import com.koalog.jcs.domain.MinMaxDomain;

/**
 * This class implements a task domain.
 *
 * @author Yan Georget
 */    
public class TaskDomain extends MinMaxDomain {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The duration of the task. */
    protected int duration;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param task a task
     */
    public TaskDomain(Task task) {
        super(task.getReleaseMin(), 
              task.getReleaseMax());
        duration = task.getDuration();
    }
    
    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------
    /**
     * Returns the duration.
     * @return an integer
     */
    public int getDuration() {
        return duration;
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Converts the task domain to a string.
     * @return a string
     */
    public String toString() {
        StringBuffer buf = new StringBuffer(super.toString());
        buf.append("<");
        buf.append(duration);
        buf.append(">");
        return buf.toString();
    }
    
    /**
     * Returns the min of the release date.
     * @return an int
     */
    public final int getReleaseMin() {
        return getMin();
    }
    
    /**
     * Returns the max of the release date.
     * @return an int
     */
    public final int getReleaseMax() {
        return getMax();
    }
    
    /**
     * Returns the min of the due date.
     * @return an int
     */
    public final int getDueMin() {
        return getMin() + duration;
    }
    
    /**
     * Returns the max of the due date.
     * @return an int
     */
    public final int getDueMax() {
        return getMax() + duration;
    }
}
/*
 * $Log$
 * Revision 1.4  2004/07/10 16:08:39  yan
 * various modifs
 *
 * Revision 1.3  2004/05/06 09:54:06  yan
 * fixed toString method
 *
 * Revision 1.2  2003/04/03 15:25:03  yan
 * fixed style
 *
 * Revision 1.1  2003/04/03 15:13:27  yan
 * initial revision
 *
 */
