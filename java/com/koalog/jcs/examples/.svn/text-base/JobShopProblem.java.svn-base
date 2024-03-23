package com.koalog.jcs.examples;

import org.apache.log4j.Category;
import org.apache.log4j.PropertyConfigurator;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.arithmetic.LeqShift;
import com.koalog.jcs.scheduler.Task;
import com.koalog.jcs.scheduler.Disjunctive;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.variable.IntegerVariable;

/**
 * A model for the job-shop problem. 
 *
 * @author Yan Georget
 */    
public class JobShopProblem extends BaseProblem {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The number of jobs. */
    int n;
    /** The number of machines. */
    int m;
    /** 
     * The problem data: one line for each job,
     * listing the machine number and processing time for each step of the job
     * (the machines are numbered starting with 0).  
     */
    int[][] data;
    /** An upper bound for the problem. */
    int ub;
    /** The makespan. */
    IntegerVariable makespan;
    /** 
     * The tasks per machine then per job. 
     */
    Task[][] task; 
    /** 
     * The order variables: 
     * for m and i &lt; j, order[m][k] <=> task[m][i] &lt; task[m][j], 
     * where k is an integer depending on i and j.
     */
    BooleanVariable[][] order;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Main constructor.
     * @param data a matrix of integers
     */
    public JobShopProblem(int[][] data) {
        this(data, makespanUB(data)); 
    }

    /**
     * Auxilliary constructor.
     * @param data a matrix of integers
     * @param ub an upper bound 
     */
    public JobShopProblem(int[][] data, int ub) {
        super();
        this.ub = ub;
        this.data = data;
        n = data.length; // number of jobs
        m = data[0].length/2; // number of machines
        task = new Task[m][n];
        // variables will contain the tasks and the makespan
        variables = new IntegerVariable[m*n+1];
        variables[m*n] = makespan = new IntegerVariable("makespan", 0, ub);
        for (int j=0; j<n; j++) {
            for (int k=0; k<m; k++) {
                int i = getMachine(data, j, k);
                variables[i+j*m] = task[i][j] = new Task(i + "_" + j,
                                                         getDuration(data,j,k),
                                                         taskLB(data,j,k),
                                                         taskUB(data,ub,j,k));
                if (k>0) {
                    // precedence constraints
                    int i0 = getMachine(data, j, k-1);
                    add(new LeqShift(task[i0][j], 
                                             task[i][j],
                                             -task[i0][j].getDuration()));
                    if (k == m-1) {
                        add(new LeqShift(task[i][j], 
                                                 makespan,
                                                 -task[i][j].getDuration())); 
                    }
                }
            }
        }
        order = new BooleanVariable[m][n*(n-1)/2];
        for (int i=0; i<m; i++) {
            for (int j1=0; j1<n-1; j1++) {
                for (int j2=j1+1; j2<n; j2++) {
                    order[i][Disjunctive.orderIndex(n, j1,j2)] = 
                        new BooleanVariable(i + "_" + j1 + "_" + j2);
                }
            }
            add(new Disjunctive(task[i], order[i]));
        }
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** 
     * Returns the schedule as a string.
     * @return a string
     */
    public String scheduleToString() {
        StringBuffer buf = new StringBuffer();
        for (int i=0; i<m; i++) {
            buf.append(Task.toString(task[i], ub));
            buf.append("\n");
        }
        return buf.toString();
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
        new JobShopMinimizer(new JobShopProblem(MT06)).optimize();
    }

    private static int getMachine(int[][] data, int j, int k) {
        return data[j][2*k];
    }

    private static int getDuration(int[][] data, int j, int k) {
        return data[j][2*k+1];
    }

    /** 
     * Computes a basic upper bound for the makespan.
     * @param data the data
     * @return an integer
     */
    public static int makespanUB(int[][] data) {
        int ub = 0;
        int n = data.length;
        int m = data[0].length/2;
        for (int k=0; k<m; k++) {
            for (int j=0; j<n; j++) {
                ub += getDuration(data, j, k);
            }
        }
        return ub;
    }

    /** 
     * Computes a basic lower bound for the kth task of a job.
     * @param data the data
     * @param j a job
     * @param k an integer
     * @return an integer
     */
    public static int taskLB(int[][] data, int j, int k) {
        int tmp = 0;
        for (int k0=0; k0<k; k0++) {
            tmp += getDuration(data, j, k0);
        }
        return tmp;
    }
   
    /** 
     * Computes a basic upper bound for the kth task of a job.
     * @param data the data
     * @param ub a global upper bound
     * @param j a job
     * @param k an integer
     * @return an integer
     */
    public static int taskUB(int[][] data, int ub, int j, int k) {
        int m = data[0].length/2;
        int tmp = ub;
        for (int k0=k+1; k0<m; k0++) {
            tmp -= getDuration(data, j, k0);
        }
        return tmp;
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(JobShopProblem.class);

    /** The MT06 instance. */
    public static final int[][] MT06 = {
        {2,1,0,3,1,6,3,7,5,3,4,6}, 
        {1,8,2,5,4,10,5,10,0,10,3,4},
        {2,5,3,4,5,8,0,9,1,1,4,7},   
        {1,5,0,5,2,5,3,3,4,8,5,9},   
        {2,9,1,3,4,5,5,4,0,3,3,1},   
        {1,3,3,3,5,9,0,10,4,4,2,1}
    };  
    
    /** The MT10 instance. */
    public static final int[][] MT10 = {
        {0,29,1,78,2,9,3,36,4,49,5,11,6,62,7,56,8,44,9,21},
        {0,43,2,90,4,75,9,11,3,69,1,28,6,46,5,46,7,72,8,30},
        {1,91,0,85,3,39,2,74,8,90,5,10,7,12,6,89,9,45,4,33},
        {1,81,2,95,0,71,4,99,6,9,8,52,7,85,3,98,9,22,5,43},
        {2,14,0,6,1,22,5,61,3,26,4,69,8,21,7,49,9,72,6,53},
        {2,84,1,2,5,52,3,95,8,48,9,72,0,47,6,65,4,6,7,25},
        {1,46,0,37,3,61,2,13,6,32,5,21,9,32,8,89,7,30,4,55},
        {2,31,0,86,1,46,5,74,4,32,6,88,8,19,9,48,7,36,3,79},
        {0,76,1,69,3,76,5,51,2,85,9,11,6,40,7,89,4,26,8,74},
        {1,85,0,13,2,61,6,7,8,64,9,76,5,47,3,52,4,90,7,45}
    };
    
    /** The MT20 instance. */
    public static final int[][] MT20 = {
        {0,29,1, 9,2,49,3,62,4,44},
        {0,43,1,75,3,69,2,46,4,72},
        {1,91,0,39,2,90,4,12,3,45},
        {1,81,0,71,4,9,2,85,3,22},
        {2,14,1,22,0,26,3,21,4,72},
        {2,84,1,52,4,48,0,47,3,6},
        {1,46,0,61,2,32,3,32,4,30},
        {2,31,1,46,0,32,3,19,4,36},
        {0,76,3,76,2,85,1,40,4,26},
        {1,85,2,61,0,64,3,47,4,90},
        {1,78,3,36,0,11,4,56,2,21},
        {2,90,0,11,1,28,3,46,4,30},
        {0,85,2,74,1,10,3,89,4,33},
        {2,95,0,99,1,52,3,98,4,43},
        {0,6,1,61,4,69,2,49,3,53},
        {1,2,0,95,3,72,4,65,2,25},
        {0,37,2,13,1,21,3,89,4,55},
        {0,86,1,74,4,88,2,48,3,79},
        {1,69,2,51,0,11,3,89,4,74},
        {0,13,1,7,2,76,3,52,4,45}
    };

    /** The LA01 instance. */
    public static final int[][] LA01 = {
        {1,21,0,53,4,95,3,55,2,34},
        {0,21,3,52,4,16,2,26,1,71},
        {3,39,4,98,1,42,2,31,0,12},
        {1,77,0,55,4,79,2,66,3,77},
        {0,83,3,34,2,64,1,19,4,37},
        {1,54,2,43,4,79,0,92,3,62},
        {3,69,4,77,1,87,2,87,0,93},
        {2,38,0,60,1,41,3,24,4,83},
        {3,17,1,49,4,25,0,44,2,98},
        {4,77,3,79,2,43,1,75,0,96}
    };

        /** The LA04 instance. */
    public static final int[][] LA04 = {
        {0,12,2,94,3,92,4,91,1,7},
        {1,19,3,11,4,66,2,21,0,87},
        {1,14,0,75,3,13,4,16,2,20},
        {2,95,4,66,0,7,3,7,1,77},
        {1,45,3,6,4,89,0,15,2,34},
        {3,77,2,20,0,76,4,88,1,53},
        {2,74,1,88,0,52,3,27,4,9},
        {1,88,3,69,0,62,4,98,2,52},
        {2,61,4,9,0,62,1,52,3,90},
        {2,54,4,5,3,59,1,15,0,88}
    };

    /** The LA31 instance. */
    public static final int[][] LA31 = {
        {4,21,7,26,9,16,2,34,3,55,8,52,5,95,6,71,1,21,0,53},
        {8,77,5,98,1,42,7,66,2,31,3,39,6,77,9,79,4,55,0,12},
        {2,64,4,92,3,34,1,19,8,62,6,54,7,43,0,83,9,79,5,37},
        {0,93,8,24,3,69,7,38,5,77,2,87,4,60,6,41,1,87,9,83},
        {9,77,0,44,4,96,8,79,6,75,2,98,5,25,3,17,7,43,1,49},
        {3,76,2,35,5,28,0,95,7,95,4,61,8,35,1,7,6,9,9,10},
        {1,91,7,27,8,50,3,16,4,28,5,59,6,52,0,46,2,59,9,43},
        {1,45,7,71,2,39,0,87,8,14,6,54,3,41,9,43,5,9,4,20},
        {2,37,3,26,4,33,9,42,0,78,6,89,7,8,8,66,1,28,5,33},
        {1,74,0,69,5,84,3,27,9,81,7,45,8,69,2,94,6,78,4,96},
        {5,76,7,32,6,18,0,20,3,87,2,17,9,25,4,24,1,31,8,81},
        {9,97,8,90,5,28,7,86,0,58,1,72,2,23,6,76,3,99,4,45},
        {9,48,5,27,6,67,7,62,4,98,0,42,1,46,8,27,3,48,2,17},
        {9,80,3,19,5,28,1,12,4,94,6,63,7,98,8,50,0,80,2,50},
        {2,50,1,41,4,61,8,79,5,14,9,72,7,18,3,55,6,37,0,75},
        {9,22,5,57,4,75,2,14,7,65,3,96,1,71,0,47,8,79,6,60},
        {3,32,2,69,4,44,1,31,9,51,0,33,6,34,5,58,7,47,8,58},
        {8,66,7,40,2,17,0,62,9,38,5,8,6,15,3,29,1,44,4,97},
        {3,50,2,58,6,21,4,63,7,57,8,32,5,20,9,87,0,57,1,39},
        {4,20,6,67,1,85,2,90,7,70,0,84,8,30,5,56,3,61,9,15},
        {6,29,0,82,4,18,3,38,7,21,8,50,1,23,5,84,2,45,9,41},
        {3,54,9,37,6,62,5,16,0,52,8,57,4,54,2,38,7,74,1,52},
        {4,79,1,61,8,11,0,81,7,89,6,89,5,57,3,68,9,81,2,30},
        {9,24,1,66,4,32,3,33,8,8,2,20,6,84,0,91,7,55,5,20},
        {3,54,2,64,6,83,9,40,7,8,0,7,4,19,5,56,1,39,8,7},
        {1,6,4,74,0,63,2,64,9,15,6,42,7,98,8,61,5,40,3,91},
        {1,80,3,75,0,26,2,87,9,22,7,39,8,24,4,75,6,44,5,6},
        {5,8,3,79,6,61,1,15,0,12,7,43,8,26,9,22,2,20,4,80},
        {1,36,0,63,7,10,4,22,3,96,5,40,9,5,8,18,6,33,2,62},
        {4,8,8,15,2,64,3,95,1,96,6,38,7,18,9,23,5,64,0,89}
    };
}
