package com.koalog.jcs.examples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.apache.log4j.Category;
import org.apache.log4j.PropertyConfigurator;
import com.koalog.jcs.variable.SetVariable;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.set.ConstantCard;
import com.koalog.jcs.constraint.set.Partition;
import com.koalog.jcs.constraint.set.IntersectionMaxSize;
import com.koalog.jcs.domain.SetDomain;

/**
 * Social golfer problem: schedule a golf tournament during n weeks.
 *
 * <P>Each week, 8 groups of 4 golfers are formed. 
 * Each golfer should only golf with the same persons only once.
 * 
 * <P>The problem has no solution if n is strictly greater than 10.
 *
 * <P>Note: This is problem #10 in CSPLib.
 *
 * @author Yan Georget
 */
public class GolfersProblem extends BaseProblem {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(GolfersProblem.class);
    /** Number of groups. */
    public static final int GROUP_NB = 8;
    /** Number of golfers in a group. */
    public static final int GROUP_CARD = 4;
    /** Number of golfers. */ 
    public static final int GOLFERS_NB = GROUP_NB*GROUP_CARD;

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    SetVariable[][] group;
    
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param n the number of weeks
     */
    public GolfersProblem(int n) {
        super();
        group = new SetVariable[n][GROUP_NB];
        Collection vars = new ArrayList(n*GROUP_NB);
        Collection golfers = new ArrayList(GOLFERS_NB);
        for (int i=1; i<=GOLFERS_NB; i++) {
            golfers.add(new Integer(i));
        }
        for (int i=0; i<n; i++) {
            for (int j=0; j<GROUP_NB; j++) {
                group[i][j] = 
                    new SetVariable(new SetDomain(Collections.EMPTY_SET, 
                                                  golfers));
                vars.add(group[i][j]);
                add(new ConstantCard(GROUP_CARD, group[i][j]));
            }
            add(new Partition(group[i], golfers));
        }
        // socialization constraints
        for (int i=0; i<n-1; i++) {
            for (int l=i+1; l<n; l++) {
                for (int j=0; j<GROUP_NB; j++) {
                    for (int k=0; k<GROUP_NB; k++) {
                        add(new IntersectionMaxSize(1, 
                                                    group[i][j], 
                                                    group[l][k]));
                    }
                }
            }
        }
        setVariables((SetVariable[]) vars.toArray(new SetVariable[] {}));
    }

    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    /**
     * Runs the problem.
     * @param args the command line arguments
     * args[0] must contain a log4j properties file location
     */
    public static void main(String[] args) {
        PropertyConfigurator.configure(args[0]);
        new GolfersSolver(new GolfersProblem(9)).solve(1);
    }
}
