package com.koalog.jcs.examples;

import java.util.Collection;
import java.util.ArrayList;
import org.apache.log4j.PropertyConfigurator;
import com.koalog.util.matrix.BaseMatrix;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.arithmetic.AllDifferent_SPARSE;
import com.koalog.jcs.constraint.arithmetic.GCC_SPARSE;
import com.koalog.jcs.constraint.arithmetic.Relation_SPARSE;
import com.koalog.jcs.constraint.arithmetic.Less;
import com.koalog.jcs.constraint.arithmetic.LexIncreasing;
import com.koalog.jcs.domain.SparseDomain;

/**
 * <P>The problem consists of scheduling games between n teams over n-1 weeks 
 * (here n for the sake of uniformity). Each week is divided into n/2 periods.
 * The goal is to schedule a game for each period of every week so that:
 * <UL>
 * <LI>every team plays against every other team,
 * <LI>a team plays exactly once a week,
 * <LI>a team plays at most twice 
 * (here exactly twice for the sake of uniformity) 
 * in the same period.
 * </UL>
 *
 * <P>Note: This is problem #26 in CSPLib.
 *
 * @author Yan Georget
 */
public class RoundRobinProblem extends BaseProblem {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The number of teams. */
    int n;
    /** The teams per periods, weeks and location (home/away). */ 
    IntegerVariable[][][] t;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param n the number of teams.
     */
    public RoundRobinProblem(int n) {
        super();
        this.n = n;
        t = new IntegerVariable[n/2][n][2];
        for (int w=0; w<n; w++) { // extended weeks
            Collection teamsPerWeek = new ArrayList();
            for (int p=0; p<n/2; p++) {
                for (int a = 0; a<2; a++) {
                    t[p][w][a] = new IntegerVariable("t_"+p+"_"+w+"_"+a,
                                                     new SparseDomain(a,n-2+a));
                    teamsPerWeek.add(t[p][w][a]);
                }
                add(new Less(t[p][w][0], t[p][w][1]));
            }
            // This enforces that a team plays once a week.
            add(new AllDifferent_SPARSE((IntegerVariable[]) teamsPerWeek
                                        .toArray(new IntegerVariable[]{})));
            
        }
        int[] val = new int[n];
        for (int i=0; i<n; i++) {
            val[i] = i;
        }
        for (int p=0; p<n/2; p++) {
            Collection teamsPerPeriod = new ArrayList();
            for (int w = 0; w<n; w++) { // extended weeks
                teamsPerPeriod.add(t[p][w][0]);
                teamsPerPeriod.add(t[p][w][1]);
            }
            // This enforces that a team plays twice per period.
            add(new GCC_SPARSE((IntegerVariable[]) teamsPerPeriod
                               .toArray(new IntegerVariable[]{}), 
                               val,
                               2)); 
        }
        // To enforce that each team meets each other team exactly one, 
        // we will consider the games/plays (pair of teams) and enforces 
        // that these are different.
        // The plays : each play is represented as a pair (h,a) where h<a.
        // where h is the team playing home and a the team playing away.
        // The pair is encoded as a number.
        int[][] plays = new int[n*(n-1)/2][3];
        int k=0;
        for (int i=0; i<n-1; i++) {
            for (int j=i+1; j<n; j++) {
                plays[k] = new int[] {i, j, k};
                k++;
            } 
        }
        // The games per periods and weeks.
        IntegerVariable[][] g = new IntegerVariable[n/2][n-1];
        for (int w=0; w<n-1; w++) {
            for (int p=0; p<n/2; p++) {
                g[p][w] = new IntegerVariable("g_" + p + "_" + w, 
                                              new SparseDomain(0, 
                                                               plays.length-1));
            }
        }
        // Relation team home, team away, game (plays).
        for (int w = 0; w<n-1; w++) {
            for (int p=0; p<n/2; p++) {
                add(new Relation_SPARSE(new IntegerVariable[] {t[p][w][0],
                                                               t[p][w][1],
                                                               g[p][w]},
                                        plays));
            }
        }
        BaseMatrix games = new BaseMatrix(g);
        add(new AllDifferent_SPARSE((IntegerVariable[]) games.getElements()));
        // Breaking symmetries on periods and weeks.
        add(new LexIncreasing(games, true, true));
        setVariables((IntegerVariable[]) games.getElements());
    }

    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    /**
     * Runs the problem.
     * @param args the command line arguments:
     * args[0] must contain a log4j properties file location
     */
    public static void main(String[] args) {
        PropertyConfigurator.configure(args[0]);
        new RoundRobinSolver(new RoundRobinProblem(14)).solve(1);
    }
}
