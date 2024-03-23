package com.koalog.jcs.solver;

import java.util.LinkedList;
import org.apache.log4j.Category;
import com.koalog.jcs.domain.Domain;
import com.koalog.jcs.variable.Variable;

/**
 * This class is the reification of the concept of solution stack. 
 * @author Yan Georget
 */
public class BaseSolutionStack implements SolutionStack {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------  
    private static Category cat = 
        Category.getInstance(BaseSolutionStack.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------  
    /** The solutions of the problem.*/
    private LinkedList solutions;
    /** The variables of the problem.*/
    private Variable[] variables;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------  
    /**
     * Constructs a solution stack.
     * @param variables an array of variables
     */
    public BaseSolutionStack(Variable[] variables) {
        this.solutions = new LinkedList();
        this.variables = variables;
    }

    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------  
    /** @see com.koalog.jcs.solver.SolutionStack */
    public final Variable[] getVariables() {
        return variables;
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------  
    /** @see com.koalog.jcs.solver.SolutionStack */
    public final Domain[] getSolution(int i) {
        return (Domain[]) solutions.get(i);
    }

    /** @see com.koalog.jcs.solver.SolutionStack */
    public final Domain[] peek() {
        return (Domain[]) solutions.getLast();
    }

    /** @see com.koalog.jcs.solver.SolutionStack */
    public final void push(Domain[] domains) {
        solutions.addLast(domains);
    }

    /**
     * Returns the solution stack as a string. 
     * @return a string
     */
    public String toString() {
        StringBuffer b = new StringBuffer();
        for(int i=0; i<variables.length; i++) {
            b.append(variables[i].getName());
            b.append(" ");
        }
        b.append("\n");
        for (int i=0; i<solutions.size(); i++) {
            Domain[] s = getSolution(i);
            for (int j=0; j<s.length; j++) {
                b.append(s[j]);
                b.append(" ");
            }
            if (i != solutions.size()-1) {
                b.append("\n");
            }
        }
        return b.toString();
    }
   
    /** @see com.koalog.jcs.solver.SolutionStack */
    public final boolean equals(Domain[][] solutions) {
        if (size() != solutions.length) {
            return false;
        }
        for (int i=0; i<solutions.length; i++) {
            Domain[] a = getSolution(i);
            Domain[] b = solutions[i];
            if (a.length != b.length) {
                return false;
            }
            for (int j=0; j<a.length; j++) {
                if (!a[j].equals(b[j])) {
                    return false;
                }
            }
        }
        return true;
    }

    /** @see com.koalog.jcs.solver.SolutionStack */
    public final boolean equals(SolutionStack solutions) {
        if (size() != solutions.size()) {
            return false;
        }
        for (int i=0; i<solutions.size(); i++) {
            Domain[] a = getSolution(i);
            Domain[] b = solutions.getSolution(i);
            if (a.length != b.length) {
                return false;
            }
            for (int j=0; j<a.length; j++) {
                if (!a[j].equals(b[j])) {
                    return false;
                }
            }
        }
        return true;
    }

    /** @see com.koalog.jcs.solver.SolutionStack */
    public final int size() {
        return solutions.size();
    }

    /** @see com.koalog.jcs.solver.SolutionStack */
    public final void clear() {
        solutions.clear();
    }
}
/*
 * $Log$
 * Revision 1.9  2004/09/15 16:02:31  yan
 * using new counter mechanism
 *
 * Revision 1.8  2004/09/15 14:18:45  yan
 * using getShortName for display
 *
 * Revision 1.7  2004/06/29 16:09:19  yan
 * avoiding use of stacks
 *
 * Revision 1.6  2003/08/02 15:20:21  yan
 * added clear method
 *
 * Revision 1.5  2003/03/07 13:52:52  yan
 * fixed style
 *
 * Revision 1.4  2002/11/18 11:49:29  yan
 * added new equals method
 *
 * Revision 1.3  2002/05/02 16:13:18  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:44  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
