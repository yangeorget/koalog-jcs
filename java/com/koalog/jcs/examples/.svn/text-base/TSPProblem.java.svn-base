package com.koalog.jcs.examples;

import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.PropertyConfigurator;
import com.koalog.jcs.domain.SparseDomain;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.arithmetic.Element;
import com.koalog.jcs.constraint.arithmetic.Cycle;
import com.koalog.jcs.constraint.arithmetic.Permutation;
import com.koalog.jcs.constraint.arithmetic.Sum;

/**
 * This is the Travelling Salesman Problem.
 * It consists in finding an hamiltonian path of minimal weight 
 * in a weighted graph. 
 *
 * @author Yan Georget
 */
public class TSPProblem extends BaseProblem {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The weight of the cycle. */
    IntegerVariable sum;
    /** A map mapping a variable to an array of costs (weights). */
    Map costs;
    
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param graph the graph
     * @param lowerBound a lower bound of the weight of the cycle
     * @param upperBound an upper bound of the weight of the cycle
     */
    public TSPProblem(int[][] graph, int lowerBound, int upperBound) {
        super();
        // variables & constraints definitions
        sum = new IntegerVariable("sum", lowerBound, upperBound);
        costs = new HashMap(graph.length);
        // next[i] is the successor of node i
        IntegerVariable next[] = new IntegerVariable[graph.length];
        // prev[j] is the predecessor of node j
        IntegerVariable prev[] = new IntegerVariable[graph.length];
        // nweight[i] is the weight of edge i -> next[i]
        IntegerVariable nweight[] = new IntegerVariable[graph.length];
        // pweight[j] is the weight of edge prev[j] -> j
        IntegerVariable pweight[] = new IntegerVariable[graph.length];
        for (int i=0; i<graph.length; i++) {
            SparseDomain domain = new SparseDomain(0, graph.length-1);
            domain.remove(i);
            next[i] = new IntegerVariable("next" + i, domain);
            nweight[i] = new IntegerVariable("nweight" + i, 
                                             getMin(graph[i]), 
                                             getMax(graph[i]));
            add(new Element(nweight[i], next[i], graph[i]));
            costs.put(next[i], graph[i]);
        }
        for (int j=0; j<graph.length; j++) {
            SparseDomain domain = new SparseDomain(0, graph.length-1);
            domain.remove(j);
            prev[j] = new IntegerVariable("prev" + j, domain); 
            int[] columnj = new int[graph.length];
            for (int i=0; i<graph.length; i++) {
                columnj[i] = graph[i][j];
            }
            pweight[j] = new IntegerVariable("pweight" + j, 
                                             getMin(columnj), 
                                             getMax(columnj));
            add(new Element(pweight[j], prev[j], columnj));
            costs.put(prev[j], columnj);
        }
        add(new Permutation(next, prev));
        add(new Cycle(next));
        add(new Cycle(prev));
        add(new Sum(sum, nweight));
        add(new Sum(sum, pweight));
        // definition of the problem variables (variables to be labelled)
        List vars = new ArrayList(2*graph.length);
        vars.addAll(Arrays.asList(prev));
        vars.addAll(Arrays.asList(next));
        setVariables((IntegerVariable[]) 
                     vars.toArray(new IntegerVariable[]{})); 
    }
    
    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    private static int getMax(int[] a) {
        int max = Integer.MIN_VALUE;
        for (int j=0; j<a.length; j++) {
            if (a[j] > max) {
                max = a[j];
            }
        }
        return max;
    }
    
    private static int getMin(int[] a) {
        int min = Integer.MAX_VALUE;
        for (int j=0; j<a.length; j++) {
            if (a[j] < min) {
                min = a[j];
            }
        }
        return min;
    }

    private static int[][] matrixCompletion(int[][] g) {
        int n = g.length;
        int[][] c = new int[n][n];
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                if (j <= i) {
                    c[i][j] = g[i][j];
                } else {
                    c[i][j] = g[j][i];
                }
            }
        }
        return c;
    }

    /**
     * Runs the problem.
     * @param args the command line arguments:
     * args[0] must contain a log4j properties file location
     */
    public static void main(String[] args) {
        PropertyConfigurator.configure(args[0]);
        new TSPMinimizer(new TSPProblem(TSPProblem.GR21, 0, 10000))
            .optimize();
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    /** Instance GR24 of TSPLIB (optimum is 1272) */
    public static final int[][] GR24 = matrixCompletion(new int[][]{
        {0},
        {257,0},
        {187,196,0},
        {91,228,158,0},
        {150,112,96,120,0},
        {80,196,88,77,63,0},
        {130,167,59,101,56,25,0},
        {134,154,63,105,34,29,22,0},
        {243,209,286,159,190,216,229,225,0},
        {185,86,124,156,40,124,95,82,207,0},
        {214,223,49,185,123,115,86,90,313,151,0},
        {70,191,121,27,83,47,64,68,173,119,148,0},
        {272,180,315,188,193,245,258,228,29,159,342,209,0},
        {219,83,172,149,79,139,134,112,126,62,199,153,97,0},
        {293,50,232,264,148,232,203,190,248,122,259,227,219,134,0},
        {54,219,92,82,119,31,43,58,238,147,84,53,267,170,255,0},
        {211,74,81,182,105,150,121,108,310,37,160,145,196,99,125,173,0},
        {290,139,98,261,144,176,164,136,389,116,147,224,275,178,154,190,79,0},
        {268,53,138,239,123,207,178,165,367,86,187,202,227,130,68,230,57,86,0},
        {261,43,200,232,98,200,171,131,166,90,227,195,137,69,82,223,90,176,90,0},
        {175,128,76,146,32,76,47,30,222,56,103,109,225,104,164,99,57,112,114,134,0},
        {250,99,89,221,105,189,160,147,349,76,138,184,235,138,114,212,39,40,46,136,96,0},
        {192,228,235,108,119,165,178,154,71,136,262,110,74,96,264,187,182,261,239,165,151,221,0},
        {121,142,99,84,35,29,42,36,220,70,126,55,249,104,178,60,96,175,153,146,47,135,169,0} 
    });
    
    /** Instance GR21 of TSPLIB (optimum is 2707) */
    public static final int[][] GR21 = matrixCompletion(new int[][]{
        {0},
        {510,0},
        {635,355,0},  
        {91,415,605,0},
        {385,585,390,350,0}, 
        {155,475,495,120,240,0}, 
        {110,480,570,78,320,96,0}, 
        {130,500,540,97,285,36,29,0}, 
        {490,605,295,460,120,350,425,390,0}, 
        {370,320,700,280,590,365,350,370,625,0}, 
        {155,380,640,63,430,200,160,175,535,240,0},  
        {68,440,575,27,320,91,48,67,430,300,90,0}, 
        {610,360,705,520,835,605,590,610,865,250,480,545,0}, 
        {655,235,585,555,750,615,625,645,775,285,515,585,190,0}, 
        {480,81,435,380,575,440,455,465,600,245,345,415,295,170,0},          
        {265,480,420,235,125,125,200,165,230,475,310,205,715,650,475,0}, 
        {255,440,755,235,650,370,320,350,680,150,175,265,400,435,385,485,0}, 
        {450,270,625,345,660,430,420,440,690,77,310,380,180,215,190,545,225,0}, 
        {170,445,750,160,495,265,220,240,600,235,125,170,485,525,405,375,87,315,0},          
        {240,290,590,140,480,255,205,220,515,150,100,170,390,425,255,395,205,220,155,0},          
        {380,140,495,280,480,340,350,370,505,185,240,310,345,280,105,380,280,165,305,150,0}
    });

    /** Instance GR17 of TSPLIB (optimum is 2085) */
    public static final int[][] GR17 = matrixCompletion(new int[][]{
        {0},
        {633,0},
        {257,390,0},
        {91,661,228,0},
        {412,227,169,383,0},
        {150,488,112,120,267,0},
        {80,572,196,77,351,63,0},
        {134,530,154,105,309,34,29,0},
        {259,555,372,175,338,264,232,249,0},
        {505,289,262,476,196,360,444,402,495,0},
        {353,282,110,324,61,208,292,250,352,154,0},
        {324,638,437,240,421,329,297,314,95,578,435,0},
        {70,567,191,27,346,83,47,68,189,439,287,254,0},
        {211,466,74,182,243,105,150,108,326,336,184,391,145,0},
        {268,420,53,239,199,123,207,165,383,240,140,448,202,57,0},
        {246,745,472,237,528,364,332,349,202,685,542,157,289,426,483,0},
        {121,518,142,84,297,35,29,36,236,390,238,301,55,96,153,336,0}
    });
}
