package com.koalog.jcs.timetabling;

import org.apache.log4j.Category;
import java.util.Collection;
import com.koalog.util.matrix.BaseMatrix;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.arithmetic.Count_SPARSE;
import com.koalog.jcs.constraint.arithmetic.Exactly_SPARSE;
import com.koalog.jcs.constraint.arithmetic.GCC_SPARSE;
import com.koalog.jcs.constraint.arithmetic.Neq_1SPARSE;
import com.koalog.jcs.constraint.arithmetic.Eq_1;

/**
 * A relation for matrix allocation.
 *
 * <P>Cells are colored with a number. 
 * Projections on rows and colums give the numbers of cells of each color.
 * 
 * @author Yan Georget
 */
public class ColoredMatrix_SPARSE extends ColoredMatrix {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
       Category.getInstance(ColoredMatrix_SPARSE.class);

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
    public ColoredMatrix_SPARSE(IntegerVariable[][] colors, 
                                IntegerVariable[] nbByColor,
                                IntegerVariable[][] nbByColumnAndColor, 
                                IntegerVariable[][] nbByLineAndColor,
                                boolean redundantModeling) {
        super(colors,
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
    public ColoredMatrix_SPARSE(BaseMatrix colors, 
                                IntegerVariable[] nbByColor,
                                IntegerVariable[][] nbByColumnAndColor, 
                                IntegerVariable[][] nbByLineAndColor,
                                boolean redundantModeling) {
        super(colors,
              nbByColor,
              nbByColumnAndColor,
              nbByLineAndColor,
              redundantModeling);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    void postProjectionConstraints(Collection variables,
                                   BaseMatrix colors,
                                   IntegerVariable[][] nbByColumnAndColor, 
                                   IntegerVariable[][] nbByLineAndColor) {
        // TODO: replace all this with a new GCC (if it exists)
        for (int k=0; k<d; k++) {
            for (int j=0; j<m; j++) {
                // c_jk = card { x_ij / x_ij = k}
                if (nbByColumnAndColor[j][k].isInstantiated()) {
                    int p = nbByColumnAndColor[j][k].getMin();
                    IntegerVariable[] column = 
                        (IntegerVariable[]) colors.getColumn(j);
                    if (p == 0) {
                        for (int i=0; i<n; i++) {
                            add(new Neq_1SPARSE(column[i], k));
                        }
                    } else if (p == n) {
                        for (int i=0; i<n; i++) {
                            add(new Eq_1(column[i], k));
                        }
                    } else {
                        add(new Exactly_SPARSE(p, column, k));
                    }
                } else {
                    add(new Count_SPARSE(nbByColumnAndColor[j][k], 
                                         (IntegerVariable[]) colors
                                         .getColumn(j), 
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
                            add(new Neq_1SPARSE(line[j], k));
                        }
                    } else if (p == m) {
                        for (int j=0; j<m; j++) {
                            add(new Eq_1(line[j], k));
                        }
                    } else {
                        add(new Exactly_SPARSE(p, line, k));
                    }
                } else {
                    add(new Count_SPARSE(nbByLineAndColor[i][k], 
                                         (IntegerVariable[]) colors
                                         .getLine(i),
                                         k));
                    variables.add(nbByLineAndColor[i][k]);
                }
            }            
        }
        // redundant constraints
        if (redundantModeling) {
            int[] col = new int[d];
            for (int k=0; k<d; k++) {
                col[k] = k;
            }
            for (int j=0; j<m; j++) {
                int[] min = new int[d];
                int[] max = new int[d];
                for (int k=0; k<d; k++) {
                    min[k] = nbByColumnAndColor[j][k].getMin();
                    max[k] = nbByColumnAndColor[j][k].getMax();
                }
                add(new GCC_SPARSE((IntegerVariable[]) colors.getColumn(j), 
                                   col, 
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
                add(new GCC_SPARSE((IntegerVariable[]) colors.getLine(i), 
                                   col, 
                                   min, 
                                   max));
            }
        }
    }
}
/*
 * $Log$
 * Revision 1.7  2005/08/31 13:53:42  yan
 * added jdoc
 *
 * Revision 1.6  2005/07/29 15:04:05  yan
 * added a flag for redundant modelling
 *
 * Revision 1.5  2005/07/22 14:52:15  yan
 * various fixes
 *
 * Revision 1.4  2005/07/22 12:05:31  yan
 * optimized
 *
 * Revision 1.3  2005/07/22 09:49:54  yan
 * optimized
 *
 * Revision 1.2  2005/07/21 07:21:34  yan
 * fixed complexity in jdoc
 *
 * Revision 1.1  2005/07/19 15:53:28  yan
 * initial revision
 *
 */
