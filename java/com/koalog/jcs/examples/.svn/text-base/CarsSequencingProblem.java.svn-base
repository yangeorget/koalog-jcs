package com.koalog.jcs.examples;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Category;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.arithmetic.SmallerSum;
import com.koalog.jcs.constraint.arithmetic.GreaterSum;
import com.koalog.jcs.constraint.arithmetic.Exactly_SPARSE;
import com.koalog.jcs.constraint.arithmetic.Element;
import com.koalog.jcs.domain.SparseDomain;

/**
 * The cars sequencing problem.
 *
 * <P>Cars have to be scheduled on a production line.
 * The cars differ by their options 
 * (the set of options of a car defines its type/class).
 * Options are installed by stations: 
 * for each block of b consecutive cars,
 * each station can handle at most s cars 
 * (s and b depend on the option/station). 
 *
 * <P>The problem has been shown to be NP-complete (Gent 1999).
 * 
 * <P>Note: This is problem #1 in CSPLib.  
 *
 * @author Yan Georget
 */
public class CarsSequencingProblem extends BaseProblem {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /**
     * The problem data:
     * <UL>
     * <LI>First line: number of cars; number of options; number of types.
     * <LI>Second line: for each option, 
     * the maximum number of cars (s) with that option in a block. 
     * <LI>Third line: for each option, 
     * the block size (b) to which the maximum number refers; 
     * then for each type: index; number of cars in this type;
     * for each option, whether or not this type requires it (1 or 0). 
     * </UL>
     */
    int[][] data;
    /** A map mapping a variable to an array of costs. */
    Map costs;

    //------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param data the problem data
     */
    public CarsSequencingProblem(int[][] data) {
        super();
        this.data = data;
        int carsNb = data[0][0];
        int optionsNb = data[0][1];
        int typesNb = data[0][2];
        IntegerVariable[] type = new IntegerVariable[carsNb];
        for (int i=0; i<carsNb; i++) {
            type[i] = new IntegerVariable("type"+i,
                                          new SparseDomain(0, typesNb-1));
        }
        for (int k=0; k<typesNb; k++) {
            // faster than a GCC_SPARSE
            add(new Exactly_SPARSE(data[3+k][1], type, k));
        }
        BooleanVariable[][] o = new BooleanVariable[optionsNb][carsNb];
        for (int j=0; j<optionsNb; j++) {
            int[] l = new int[typesNb];
            for (int k=0; k<typesNb; k++) {
                l[k] = data[3+k][2+j];
            }
            for (int i=0; i<carsNb; i++) {
                o[j][i] = new BooleanVariable("o"+j+"_"+i);
                add(new Element(o[j][i], type[i], l));
            }
        }
        // using[j] is the number of cars using option j
        int[] using = new int[optionsNb]; 
        // utilization[j] is the utilization of option j
        final int[] utilization = new int[optionsNb];
        for (int j=0; j<optionsNb; j++) {
            int b = data[2][j]; // the block size 
            int s = data[1][j]; // the maximal size 
            using[j] = 0;
            for (int k=0; k<typesNb; k++) {
                 using[j] += data[3+k][1] * data[3+k][2+j];
            }
            // utilization is rounded to an int using the precision/scale
            utilization[j] = (SCALE*using[j]*b) / (carsNb*s);
            cat.debug("using[" + j + "]=" + using[j]);
            cat.debug("utilization[" + j + "]=" + utilization[j]);
            // sequence constraints
            for (int i=0; i<carsNb+1-b; i++) {
                BooleanVariable[] block = new BooleanVariable[b];
                for (int k=0; k<b; k++) {
                    block[k] = o[j][i+k]; 
                }
                // faster than atmost?
                add(new SmallerSum(block, s+1));
            }
            // redundant constraints
            for (int t=1; b*t<carsNb; t++) {
                if (using[j]-s*t > 0) {
                    int length = carsNb-b*t;
                    BooleanVariable[] pf = new BooleanVariable[length];
                    System.arraycopy(o[j], 0, pf, 0, length);
                    // faster than atleast?
                    add(new GreaterSum(pf, using[j]-s*t-1));
                    // useless with the variable heuristic chosen
                    // BooleanVariable[] pl = new BooleanVariable[length];
                    // System.arraycopy(o[j], carsNb-length, pl, 0, length);
                    // add(new Atleast(using[j]-s*t, pl, 1));
                }
            }
        }
        setVariables(type);
        // an array of pairs (option index, option utilization) 
        // as an array of arrays
        int[][] optionUtilization = new int[optionsNb][2];
        for (int j=0; j<optionsNb; j++) {
            optionUtilization[j] = new int[] {j, utilization[j]};
        }
        // we sort the options according to their utilization
        Arrays.sort(optionUtilization, new Comparator() {
                public int compare(Object o1, Object o2) {
                    int[] ou1 = (int[]) o1;
                    int[] ou2 = (int[]) o2;
                    return ou1[1] - ou2[1];
                }
            });
        // for each option, its 'cost'
        int[] optionCost = new int[optionsNb];
        int shift = 1;
        for (int j=0; j<optionsNb; j++) {
            optionCost[optionUtilization[j][0]] = 
                optionUtilization[j][1] * shift;
            shift *= SCALE;
            cat.debug("optionCost[" + j + "]=" + optionCost[j]);
        }
        costs = new HashMap(carsNb);
        for (int i=0; i<carsNb; i++) {
            // associates to each type, a cost 
            // which is the sum of the costs of the options in this type
            int[] c = new int[typesNb];
            for (int k=0; k<typesNb; k++) {
                c[k] = 0; 
                for (int j=0; j<optionsNb; j++) {
                    if (data[3+k][2+j] == 1) {
                        // because we use MinCost instead of MaxCost
                        c[k] += -optionCost[j];
                    }
                }
            }
            costs.put(type[i], c);
        }
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
        new CarsSequencingSolver(new CarsSequencingProblem(ECAI88)).solve();
    }
        
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
       Category.getInstance(CarsSequencingTest.class);

    //------------------------------------------------------------------------
    // CONSTANTS
    //------------------------------------------------------------------------
    /** 
     * A scale/precision for ordering options.
     */
    public static final int SCALE = 20;

    /**
     * A problem proposed by Dincbas at ECAI88.
     */
    public static final int[][] ECAI88 = new int[][] {
        {10, 5, 6},
        {1, 2, 1, 2, 1},
        {2, 3, 3, 5, 5},
        {0, 1, 1, 0, 1, 1, 0}, 
        {1, 1, 0, 0, 0, 1, 0}, 
        {2, 2, 0, 1, 0, 0, 1}, 
        {3, 2, 0, 1, 0, 1, 0}, 
        {4, 2, 1, 0, 1, 0, 0}, 
        {5, 2, 1, 1, 0, 0, 0} 
    };

    /**
     * A problem proposed by Regin & Puget (no solution).
     */
    public static final int[][] RP1 = new int[][] {
        {100, 5, 22},
        {1, 2, 1, 2, 1},
        {2, 3, 3, 5, 5},
        {0, 6, 1, 0, 0, 1, 0},
        {1, 10, 1, 1, 1, 0, 0}, 
        {2, 2, 1, 1, 0, 0, 1}, 
        {3, 2, 0, 1, 1, 0, 0}, 
        {4, 8, 0, 0, 0, 1, 0}, 
        {5, 15, 0, 1, 0, 0, 0}, 
        {6, 1, 0, 1, 1, 1, 0}, 
        {7, 5, 0, 0, 1, 1, 0}, 
        {8, 2, 1, 0, 1, 1, 0}, 
        {9, 3, 0, 0, 1, 0, 0}, 
        {10, 2, 1, 0, 1, 0, 0}, 
        {11, 1, 1, 1, 1, 0, 1}, 
        {12, 8, 0, 1, 0, 1, 0}, 
        {13, 3, 1, 0, 0, 1, 1}, 
        {14, 10, 1, 0, 0, 0, 0}, 
        {15, 4, 0, 1, 0, 0, 1}, 
        {16, 4, 0, 0, 0, 0, 1}, 
        {17, 2, 1, 0, 0, 0, 1}, 
        {18, 4, 1, 1, 0, 0, 0}, 
        {19, 6, 1, 1, 0, 1, 0}, 
        {20, 1, 1, 0, 1, 0, 1}, 
        {21, 1, 1, 1, 1, 1, 1}, 
    };

    /**
     * A problem proposed by Smith (60%).
     */
    public static final int[][] S60_01 = new int[][] {
        {200, 5, 24},
        {1, 2, 1, 2, 1},
        {2, 3, 3, 5, 5},
        {0, 3, 1, 0, 0, 1, 0},
        {1, 84, 0, 1, 0, 0, 0},
        {2, 5, 0, 1, 0, 1, 0},
        {3, 1, 1, 0, 0, 1, 1},
        {4, 8, 1, 1, 0, 0, 0},
        {5, 34, 0, 0, 1, 0, 0},
        {6, 3, 1, 1, 1, 1, 0},
        {7, 11, 0, 0, 0, 0, 1},
        {8, 3, 0, 1, 1, 0, 0},
        {9, 1, 1, 0, 1, 0, 1},
        {10, 4, 1, 1, 0, 1, 0},
        {11, 2, 0, 0, 1, 0, 1},
        {12, 1, 1, 1, 0, 0, 1},
        {13, 1, 1, 1, 1, 0, 1},
        {14, 3, 0, 0, 1, 1, 0},
        {15, 1, 1, 0, 0, 0, 1},
        {16, 12, 0, 0, 0, 1, 0},
        {17, 15, 1, 0, 0, 0, 0},
        {18, 2, 0, 1, 1, 1, 0},
        {19, 1, 1, 0, 1, 1, 0},
        {20, 1, 1, 1, 0, 1, 1},
        {21, 1, 1, 1, 1, 0, 0},
        {22, 1, 0, 0, 1, 1, 1},
        {23, 2, 0, 1, 0, 0, 1}
    };
        
    /**
     * A problem proposed by Smith (70%).
     */
    public static final int[][] S70_01 = new int[][] {
        {200, 5, 26},
        {1, 2, 1, 2, 1},
        {2, 3, 3, 5, 5},
        {0, 4, 1, 0, 1, 0, 0},
        {1, 6, 1, 0, 0, 1, 0},
        {2, 59, 0, 1, 0, 0, 0},
        {3, 10, 0, 1, 0, 1, 0},
        {4, 1, 1, 0, 0, 1, 1},
        {5, 13, 1, 1, 0, 0, 0},
        {6, 23, 0, 0, 1, 0, 0},
        {7, 3, 1, 1, 1, 1, 0},
        {8, 11, 0, 0, 0, 0, 1},
        {9, 6, 0, 1, 1, 0, 0},
        {10, 1, 1, 0, 1, 0, 1},
        {11, 7, 1, 1, 0, 1, 0},
        {12, 2, 0, 0, 1, 0, 1},
        {13, 1, 1, 1, 0, 0, 1},
        {14, 1, 1, 1, 1, 0, 1},
        {15, 5, 0, 0, 1, 1, 0},
        {16, 2, 1, 0, 0, 0, 1},
        {17, 1, 0, 0, 0, 1, 1},
        {18, 14, 0, 0, 0, 1, 0},
        {19, 17, 1, 0, 0, 0, 0},
        {20, 2, 0, 1, 1, 1, 0},
        {21, 2, 1, 0, 1, 1, 0},
        {22, 1, 1, 1, 0, 1, 1},
        {23, 1, 1, 1, 1, 0, 0},
        {24, 1, 0, 0, 1, 0, 1},
        {25, 6, 0, 1, 0, 0, 0}
    };

    /**
     * A problem proposed by Smith (70%).
     */
    public static final int[][] S70_02 = new int[][] {
        {200, 5, 23},
        {1, 2, 1, 2, 1},
        {2, 3, 3, 5, 5},
        {0, 3, 1, 0, 1, 1, 0},
        {1, 4, 1, 0, 0, 0, 1},
        {2, 12, 1, 1, 0, 0, 0},
        {3, 3, 1, 1, 1, 0, 0},
        {4, 19, 0, 0, 0, 0, 1},
        {5, 2, 1, 1, 0, 0, 1},
        {6, 3, 1, 1, 0, 1, 0},
        {7, 7, 0, 1, 0, 1, 0},
        {8, 2, 0, 0, 1, 0, 1},
        {9, 1, 1, 0, 1, 0, 1},
        {10, 26, 0, 0, 1, 0, 0},
        {11, 35, 0, 0, 0, 1, 0},
        {12, 2, 0, 1, 1, 0, 1},
        {13, 6, 0, 0, 1, 1, 0},
        {14, 7, 1, 0, 1, 0, 0},
        {15, 1, 1, 0, 0, 1, 1},
        {16, 1, 0, 0, 1, 1, 1},
        {17, 2, 0, 0, 0, 1, 1},
        {18, 48, 0, 1, 0, 0, 0},
        {19, 2, 0, 1, 1, 0, 0},
        {20, 2, 0, 1, 0, 0, 1},
        {21, 9, 1, 0, 0, 0, 0},
        {22, 3, 1, 0, 0, 1, 0}
    };

    /**
     * A problem proposed by Smith (80%).
     */
    public static final int[][] S80_01 = new int[][] {
        {200, 5, 26},
        {1, 2, 1, 2, 1},
        {2, 3, 3, 5, 5},
        {0, 6, 1, 0, 1, 0, 0},
        {1, 8, 1, 0, 0, 1, 0},
        {2, 39, 0, 1, 0, 0, 0},
        {3, 14, 0, 1, 0, 1, 0},
        {4, 1, 1, 0, 0, 1, 1},
        {5, 15, 1, 1, 0, 0, 0},
        {6, 13, 0, 0, 1, 0, 0},
        {7, 4, 1, 1, 1, 1, 0},
        {8, 11, 0, 0, 0, 0, 1},
        {9, 9, 0, 1, 1, 0, 0},
        {10, 1, 1, 0, 1, 0, 1},
        {11, 9, 1, 1, 0, 1, 0},
        {12, 4, 0, 0, 1, 0, 1},
        {13, 2, 1, 1, 0, 0, 1},
        {14, 1, 1, 1, 1, 0, 1},
        {15, 6, 0, 0, 1, 1, 0},
        {16, 3, 1, 0, 0, 0, 1},
        {17, 2, 0, 0, 0, 1, 1},
        {18, 14, 0, 0, 0, 1, 0},
        {19, 20, 1, 0, 0, 0, 0},
        {20, 3, 0, 1, 1, 1, 0},
        {21, 3, 1, 0, 1, 1, 0},
        {22, 1, 1, 1, 0, 1, 1},
        {23, 1, 1, 1, 1, 0, 0},
        {24, 1, 0, 0, 1, 0, 1},
        {25, 9, 0, 1, 0, 0, 0},
    };

    /**
     * A problem proposed by Smith (90%).
     */
    public static final int[][] S90_10 = new int[][] {
        {200, 5, 26},
        {1, 2, 1, 2, 1},
        {2, 3, 3, 5, 5},
        {0, 5, 0, 1, 0, 0, 1},
        {1, 16, 0, 1, 0, 1, 0},
        {2, 10, 0, 1, 1, 0, 0},
        {3, 8, 0, 0, 1, 0, 0},
        {4, 1, 1, 1, 1, 1, 0},
        {5, 15, 1, 0, 0, 0, 0},
        {6, 3, 0, 0, 0, 1, 1},
        {7, 8, 0, 1, 1, 1, 0},
        {8, 5, 1, 0, 1, 0, 0},
        {9, 1, 1, 0, 1, 1, 1},
        {10, 5, 0, 0, 1, 1, 0},
        {11, 9, 1, 1, 0, 1, 0},
        {12, 2, 0, 0, 1, 0, 1},
        {13, 25, 1, 1, 0, 0, 0},
        {14, 9, 1, 1, 1, 0, 0},
        {15, 28, 0, 1, 0, 0, 0},
        {16, 1, 0, 0, 1, 1, 1},
        {17, 3, 0, 1, 0, 1, 1},
        {18, 1, 0, 1, 1, 1, 1},
        {19, 2, 0, 1, 1, 0, 1},
        {20, 11, 0, 0, 0, 0, 1},
        {21, 4, 1, 1, 0, 0, 1},
        {22, 4, 1, 0, 0, 0, 0},
        {23, 11, 0, 0, 0, 1, 0},
        {24, 5, 1, 0, 1, 0, 0},
        {25, 8, 1, 0, 0, 0, 0}
    };
}
