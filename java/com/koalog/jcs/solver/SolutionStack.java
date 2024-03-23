package com.koalog.jcs.solver;

import com.koalog.jcs.domain.Domain;
import com.koalog.jcs.variable.Variable;

/**
 * This interface describes a solution stack.
 *
 * <P>A <CODE>SolutionStack</CODE> is a wrapper for a stack of solutions
 * but contains methods specific to solutions as:
 * <UL>
 * <LI><CODE>getVariables</CODE> which returns the current variables,</LI>
 * <LI><CODE>equals(Domain[][])</CODE>
 * which compares the stack with an array 
 * (array[i][j] is the domain of the jth variable 
 * in the ith solution found).</LI>
 * </UL>
 * </P>
 *
 * @see Domain
 * @see Variable
 * @author Yan Georget
 */
public interface SolutionStack {
    //------------------------------------------------------------------------
    // ABSTRACT METHODS
    //------------------------------------------------------------------------  
    /** 
     * Returns the variables of the solution stack.
     * @return an array of variables
     */
    Variable[] getVariables();

    /** 
     * Returns the ith solution.
     * @param i an integer
     * @return an array of domains
     */
    Domain[] getSolution(int i);

    /** 
     * Peeks the last solution.
     * @return an array of domains
     */
    Domain[] peek();

    /**
     * Pushes a solution on top of the solution stack.
     * @param domains an array of domains
     */
    void push(Domain[] domains);

    /**
     * Compares the solution stack with an array of solutions.
     * @param solutions an array of arrays of domains
     * @return a boolean
     */
    boolean equals(Domain[][] solutions);

    /**
     * Compares the solution stack with another solution stack.
     * @param solutions a solution stack
     * @return a boolean
     */
    boolean equals(SolutionStack solutions);

    /**
     * Returns the size of the stack (ie the number of solutions).
     * @return an int
     */
    int size();

    /**
     * Clears the solution stack.
     */
    void clear();
}
/*
 * $Log$
 * Revision 1.10  2004/06/29 16:09:25  yan
 * avoiding use of stacks
 *
 * Revision 1.9  2003/08/02 15:20:21  yan
 * added clear method
 *
 * Revision 1.8  2003/03/07 13:52:52  yan
 * fixed style
 *
 * Revision 1.7  2002/11/18 11:49:28  yan
 * added new equals method
 *
 * Revision 1.6  2002/06/16 13:59:15  yan
 * added javadoc
 *
 * Revision 1.5  2002/05/22 09:27:25  yan
 * added javadoc
 *
 * Revision 1.4  2002/05/03 14:26:49  yan
 * added javadoc
 *
 * Revision 1.3  2002/05/02 16:13:18  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:44  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
