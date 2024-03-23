package com.koalog.jcs.examples;

import java.util.*;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Category;

import com.koalog.util.Arithmetic;
import com.koalog.util.matrix.BaseMatrix;

import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.arithmetic.WeightedSum;
import com.koalog.jcs.constraint.arithmetic.Max;
import com.koalog.jcs.constraint.arithmetic.Less;
import com.koalog.jcs.constraint.arithmetic.GCC_SPARSE;
import com.koalog.jcs.constraint.arithmetic.*;
import com.koalog.jcs.constraint.bool.Equals_SPARSE;
import com.koalog.jcs.domain.SparseDomain;

/**
 * The Balanced Academic Curriculum Problem.
 *
 * <P>Note: This is problem #30 in CSPLib.  
 *
 * @author Yan Georget
 */
public class BACProblem extends BaseProblem {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    IntegerVariable c;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param data the data
     */
    public BACProblem(BACData data) {
        super();
        // boolean model
        BooleanVariable[][] cur = 
            new BooleanVariable[data.coursesNb][data.periodsNb];
        for (int i=0; i<data.coursesNb; i++) {
            for (int j=0; j<data.periodsNb; j++) {
                cur[i][j] = new BooleanVariable();
            }  
        }
        BaseMatrix curriculumMatrix = new BaseMatrix(cur);
        // integer model
        IntegerVariable[] curriculum = new IntegerVariable[data.coursesNb];
        for (int i=0; i<data.coursesNb; i++) {
            curriculum[i] = 
                new IntegerVariable("cur_" + i, 
                                    new SparseDomain(0, data.periodsNb-1));
        }
        // channelling constraints
        for (int i=0; i<data.coursesNb; i++) {
            for (int j=0; j<data.periodsNb; j++) {
                add(new Equals_SPARSE((BooleanVariable) 
                                      curriculumMatrix.getElement(i, j), 
                                      curriculum[i],
                                      j));
            }  
        }
        // academic load constraints
        IntegerVariable[] load = new IntegerVariable[data.periodsNb];
        for (int j=0; j<data.periodsNb; j++) {
            load[j] = new IntegerVariable(data.minAcademicLoad, 
                                          data.maxAcademicLoad);
            add(new WeightedSum(load[j], 
                                data.credits, 
                                (BooleanVariable[])
                                curriculumMatrix.getColumn(j)));
        }
        c = new IntegerVariable(Arithmetic.ceilDivide(data.getTotalAcademicLoad(), 
                                                      data.periodsNb), 
                                data.maxAcademicLoad);
        add(new Max(c, load));
        // prerequisites constraints
        for (int l=0; l<data.prerequisites.length; l++) {
            int i = data.prerequisites[l][0];
            int j = data.prerequisites[l][1];
            add(new Less(curriculum[i], curriculum[j]));
        }
        // min-max amount of courses constraints
        int[] p = new int[data.periodsNb];
        for (int i=0; i<data.periodsNb; i++) {
            p[i] = i;
        }
        add(new GCC_SPARSE(curriculum, 
                           p,
                           data.minCoursesAmount, 
                           data.maxCoursesAmount));
        // rows symmetries
        for (int l=1; l<=data.getMaxAcademicLoad(); l++) {
            int[] c = data.getFreeCourses(l);
            // courses of same academic load and not appearing in prerequisites 
            // can be ordered
            for (int i=1; i<c.length; i++) {
                add(new Leq(curriculum[c[i-1]], curriculum[c[i]]));
            }
        }
        // setVariables(curriculum);
        setVariables((BooleanVariable[])curriculumMatrix.getElementsByColumns());
    }

    //------------------------------------------------------------------------
    // INNER CLASSES
    //------------------------------------------------------------------------
    static class BACData {
        int periodsNb;
        int coursesNb;
        int minAcademicLoad;
        int maxAcademicLoad;
        int minCoursesAmount;
        int maxCoursesAmount;
        int[] credits;
        int[][] prerequisites;

        BACData(int periodsNb,
                int coursesNb,
                int minAcademicLoad,
                int maxAcademicLoad,
                int minCoursesAmount,
                int maxCoursesAmount,
                int[] credits,
                int[][] prerequisites) {
            this.periodsNb = periodsNb;
            this.coursesNb = coursesNb;
            this.minAcademicLoad = minAcademicLoad;
            this.maxAcademicLoad = maxAcademicLoad;
            this.minCoursesAmount = minCoursesAmount;
            this.maxCoursesAmount = maxCoursesAmount;
            this.credits = credits;
            this.prerequisites = prerequisites;
        }
        
        int getTotalAcademicLoad() {
            int l = 0;
            for (int i=0; i<credits.length; i++) {
                l += credits[i];
            }
            return l;
        }

        int getMaxAcademicLoad() {
            int l = 0;
            for (int i=0; i<credits.length; i++) {
                l = Math.max(l, credits[i]);
            }
            return l;
        }

        boolean notInPrerequisites(int i) {
            for (int j=0; j<prerequisites.length; j++) {
                if (prerequisites[j][0]==i || prerequisites[j][1]==i) {
                    return false;
                }
            }
            return true;
        }

        int[] getFreeCourses(int load) {
            List l = new ArrayList();
            for (int i=0; i<credits.length; i++) {
                if (load == credits[i] && notInPrerequisites(i)) {
                    l.add(new Integer(i));
                }
            }
            int[] a = new int[l.size()];
            int j=0;
            for (Iterator i = l.iterator(); i.hasNext();) {
                a[j++] = ((Integer) i.next()).intValue();
            }
            return a;
        }
    }

    //------------------------------------------------------------------------
    // CONSTANTS
    //------------------------------------------------------------------------
    /** CSPLIB instance 1. */
    public static final BACData INSTANCE1 = 
       new BACData(8, 
                   46, 
                   10, 
                   24, 
                   2, 
                   10, 
                   new int[] {
                       1, 3, 1, 2, 4, 4, 1, 5, 3, 4, 
                       4, 5, 1, 3, 3, 4, 1, 1, 3, 3, 
                       3, 3, 3, 3, 1, 4, 4, 3, 3, 3, 
                       2, 4, 3, 3, 3, 3, 3, 3, 3, 3, 
                       3, 3, 2, 3, 3, 3
                   },
                   new int[][] {
                       {6, 0},
                       {7, 1},
                       {7, 5},
                       {9, 4},
                       {10, 4},
                       {10, 5},
                       {11, 7},
                       {11, 10},
                       {13, 8},
                       {14, 8},
                       {15, 9 },
                       {15, 10},
                       {16, 6},
                       {17, 2},
                       {18, 13},
                       {19, 13},
                       {20, 14},
                       {23, 15},
                       {25, 13},
                       {26, 20},
                       {27, 21},
                       {28, 23},
                       {31, 25},
                       {32, 25},
                       {33, 29},
                       {34, 27},
                       {35, 27},
                       {35, 27},
                       {36, 29},
                       {36, 29},
                       {37, 35},
                       {37, 35},
                       {38, 29},
                       {39, 35},
                       {39, 35},
                       {44, 37},
                       {45, 31},
                       {45, 31}
                   });
  

    /** CSPLIB instance 2. */
    public static final BACData INSTANCE2 = 
       new BACData(10, 
                   42, 
                   10, 
                   24, 
                   2, 
                   10, 
                   new int[] {
                       1, 3, 2, 2, 5, 3, 1, 5, 2, 3, 
                       5, 1, 4, 1, 2, 4, 3, 4, 1, 4, 
                       3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 
                       4, 3, 4, 4, 3, 4, 4, 3, 3, 3, 
                       3, 3 
                   },
                   new int[][] {
                       {6, 0},
                       {7, 1}, 
                       {7, 4},
                       {10, 4},
                       {11, 6},
                       {12, 7},
                       {12, 10},
                       {15, 9},
                       {16, 9},
                       {17, 10},
                       {18, 13},
                       {19, 15},
                       {20, 15},
                       {20, 16},
                       {23, 17},
                       {24, 7},
                       {24, 10},
                       {25, 19},
                       {26, 20},
                       {27, 20},
                       {28, 12},
                       {28, 24},
                       {29, 25},
                       {30, 26},
                       {31, 21},
                       {31, 22},
                       {32, 23},
                       {33, 30},
                       {34, 21},
                       {34, 22},
                       {35, 29},
                       {36, 20}, 
                       {36, 32},
                       {39, 34}
                   });
  
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(BACProblem.class);

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
        new BACMinimizer(new BACProblem(BACProblem.INSTANCE1)).optimize();
    }
}
