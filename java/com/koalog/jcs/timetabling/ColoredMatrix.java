package com.koalog.jcs.timetabling;

import org.apache.log4j.Category;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import com.koalog.util.matrix.BaseMatrix;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.arithmetic.Sum;
import com.koalog.jcs.constraint.arithmetic.ConstantSum;
import com.koalog.jcs.constraint.arithmetic.Count;
import com.koalog.jcs.constraint.arithmetic.Exactly;
import com.koalog.jcs.constraint.arithmetic.GCC;
import com.koalog.jcs.constraint.arithmetic.Eq_1;
import com.koalog.jcs.constraint.arithmetic.Neq_1;

/**
 * A relation for matrix allocation.
 *
 * <P>Cells are colored with a number. 
 * Projections on rows and colums give the numbers of cells of each color.
 * 
 * @author Yan Georget
 */
public class ColoredMatrix extends BaseProblem {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(ColoredMatrix.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The number of lines. */
    int n;
    /** The number of columns. */
    int m;
    /** The number of colors. */
    int d;
    /** A boolean indicating if a redundant modeling should be used. */
    boolean redundantModeling;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Auxilliary constructor.
     * @param colors the matrix of colors
     * @param nbByColor the numbers of colors of each type 
     * @param nbByColumnAndColor the projections by columns and by colors
     * @param nbByLineAndColor the projections by lines/rows and by colors
     * @param redundantModeling a boolean indicating 
     * if a redundant modeling should be used
     */
    public ColoredMatrix(IntegerVariable[][] colors, 
                         IntegerVariable[] nbByColor,
                         IntegerVariable[][] nbByColumnAndColor, 
                         IntegerVariable[][] nbByLineAndColor,
                         boolean redundantModeling) {
        this(new BaseMatrix(colors),
             nbByColor,
             nbByColumnAndColor,
             nbByLineAndColor,
             redundantModeling);
    }
  
    /**
     * Main constructor.
     * @param colors the matrix of colors
     * @param nbByColor the numbers of colors of each type 
     * @param nbByColumnAndColor the projections by columns and by colors
     * @param nbByLineAndColor the projections by lines/rows and by colors
     * @param redundantModeling a boolean indicating 
     * if a redundant modeling should be used
     */
    public ColoredMatrix(BaseMatrix colors, 
                         IntegerVariable[] nbByColor,
                         IntegerVariable[][] nbByColumnAndColor, 
                         IntegerVariable[][] nbByLineAndColor,
                         boolean redundantModeling) {
        super("coloredmatrix");
        this.redundantModeling = redundantModeling;
        n = colors.getHeight();
        m = colors.getWidth();
        d = nbByColumnAndColor[0].length;
        Collection variables = new ArrayList(n*m + nbByColor.length + d*(n+m));
        variables.addAll(Arrays.asList(colors.getElements()));
        variables.addAll(Arrays.asList(nbByColor));    
        postProjectionConstraints(variables, 
                                  colors, 
                                  nbByColumnAndColor, 
                                  nbByLineAndColor);
        postCountConstraints(variables, 
                                 nbByColor, 
                                 nbByColumnAndColor, 
                                 nbByLineAndColor);
        setVariables((IntegerVariable[]) variables
                     .toArray(new IntegerVariable[] {}));
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    void postProjectionConstraints(Collection variables,
                                   BaseMatrix colors,
                                   IntegerVariable[][] nbByColumnAndColor, 
                                   IntegerVariable[][] nbByLineAndColor) {
        // TODO: replace all this with a new GCC
        for (int k=0; k<d; k++) {
            for (int j=0; j<m; j++) {
                // c_jk = card { x_ij / x_ij = k}
                if (nbByColumnAndColor[j][k].isInstantiated()) {
                    int p = nbByColumnAndColor[j][k].getMin();
                    IntegerVariable[] column = 
                        (IntegerVariable[]) colors.getColumn(j);
                    if (p == 0) {
                        for (int i=0; i<n; i++) {
                            add(new Neq_1(column[i], k));
                        }
                    } else if (p == n) {
                        for (int i=0; i<n; i++) {
                            add(new Eq_1(column[i], k));
                        }
                    } else {
                        add(new Exactly(p, column, k));
                    }
                } else {
                    add(new Count(nbByColumnAndColor[j][k], 
                                  (IntegerVariable[]) colors.getColumn(j), 
                                  k));
                    variables.add(nbByColumnAndColor[j][k]);
                }
            }            
            for (int i=0; i<n; i++) {
                // l_ik = card { x_ij / x_ij = k}
                if (nbByLineAndColor[i][k].isInstantiated()) {
                    int p = nbByLineAndColor[i][k].getMin();
                    IntegerVariable[] line = 
                        (IntegerVariable[]) colors.getLine(i);
                    if (p == 0) {
                        for (int j=0; j<m; j++) {
                            add(new Neq_1(line[j], k));
                        }
                    } else if (p == m) {
                        for (int j=0; j<m; j++) {
                            add(new Eq_1(line[j], k));
                        }
                    } else {
                        add(new Exactly(p, line, k));
                    }
                } else {
                    add(new Count(nbByLineAndColor[i][k], 
                                  (IntegerVariable[]) colors.getLine(i),
                                  k));
                    variables.add(nbByLineAndColor[i][k]);
                }
            }            
        }
        // redundant constraints
        if (redundantModeling) {
            for (int j=0; j<m; j++) {
                int[] min = new int[d];
                int[] max = new int[d];
                for (int k=0; k<d; k++) {
                    min[k] = nbByColumnAndColor[j][k].getMin();
                    max[k] = nbByColumnAndColor[j][k].getMax();
                }
                add(new GCC((IntegerVariable[]) colors.getColumn(j), 
                            0,
                            d-1,
                            min, 
                            max));
            }
            for (int i=0; i<n; i++) {
                int[] min = new int[d];
                int[] max = new int[d];
                for (int k=0; k<d; k++) {
                    min[k] = nbByLineAndColor[i][k].getMin();
                    max[k] = nbByLineAndColor[i][k].getMax();
                }
                add(new GCC((IntegerVariable[]) colors.getLine(i), 
                            0,
                            d-1,
                            min, 
                            max));
            }
        }
    }

    void postCountConstraints(Collection variables,
                              IntegerVariable[] nbByColor,
                              IntegerVariable[][] nbByColumnAndColor, 
                              IntegerVariable[][] nbByLineAndColor) {
        // these are redundant constraints
        for (int j=0; j<m; j++) {
            add(new ConstantSum(nbByColumnAndColor[j], n));
        }
        for (int i=0; i<n; i++) {
            add(new ConstantSum(nbByLineAndColor[i], m));
        }
        // for all k, sum_i l[i][k] = sum_j c[j][k] 
        for (int k=0; k<d; k++) {
            IntegerVariable[] ll = new IntegerVariable[m];
            for (int j=0; j<m; j++) {
                ll[j] = nbByColumnAndColor[j][k];
            }
            add(new Sum(nbByColor[k], ll));
            IntegerVariable[] lc = new IntegerVariable[n];
            for (int i=0; i<n; i++) {
                lc[i] = nbByLineAndColor[i][k];
            }
            add(new Sum(nbByColor[k], lc));
        }
    }
}
/*
 * $Log$
 * Revision 1.9  2005/08/31 13:53:42  yan
 * added jdoc
 *
 * Revision 1.8  2005/07/29 15:04:05  yan
 * added a flag for redundant modelling
 *
 * Revision 1.7  2005/07/22 14:52:15  yan
 * various fixes
 *
 * Revision 1.6  2005/07/22 12:05:31  yan
 * optimized
 *
 * Revision 1.5  2005/07/22 09:49:54  yan
 * optimized
 *
 * Revision 1.4  2005/07/21 07:21:18  yan
 * fixed complexity in jdoc
 *
 * Revision 1.3  2005/07/19 17:32:35  yan
 * fixed bug
 *
 * Revision 1.2  2005/07/19 15:52:01  yan
 * not sparse anymore
 *
 * Revision 1.1  2005/02/02 14:14:04  yan
 * added back
 *
 * Revision 1.2  2005/02/01 14:03:28  yan
 * various fixes
 *
 * Revision 1.1  2005/01/31 11:59:14  yan
 * reintroduced
 *
 * Revision 1.5  2003/06/17 13:47:11  yan
 * fixed jdoc
 *
 * Revision 1.4  2003/06/17 13:36:20  yan
 * added javadoc about algorithmic infos
 *
 * Revision 1.3  2003/06/15 22:06:10  yan
 * small optim
 *
 * Revision 1.2  2003/06/15 19:47:20  yan
 * fixed style
 *
 * Revision 1.1  2003/06/15 18:02:45  yan
 * initial revision
 *
 */
