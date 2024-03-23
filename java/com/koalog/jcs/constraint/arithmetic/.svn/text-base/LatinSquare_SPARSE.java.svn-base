package com.koalog.jcs.constraint.arithmetic;

import org.apache.log4j.Category;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import com.koalog.util.matrix.SquareMatrix;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.BaseProblem;

/**
 * A relation for latin squares.
 *
 * @author Yan Georget
 * @since 3.0
 */
public class LatinSquare_SPARSE extends BaseProblem {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
       Category.getInstance(LatinSquare_SPARSE.class);

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Main constructor.
     * @param num the numbers, with sparse domains, by row then by column
     * @param row the rows, with sparse domains, by number then by column
     * @param col the columns, with sparse domains, by number then by row
     */
    public LatinSquare_SPARSE(SquareMatrix num, 
                              SquareMatrix row,
                              SquareMatrix col) {
        super("latinsquare");
        int n = num.getHeight();
        for (int c=0; c<n; c++) {
            add(new Permutation((IntegerVariable[]) col.getLine(c),
                                (IntegerVariable[]) row.getLine(c)));
        }
        for (int j=0; j<n; j++) {
            add(new Permutation((IntegerVariable[]) num.getColumn(j),
                                (IntegerVariable[]) row.getColumn(j)));
        }
        for (int i=0; i<n; i++) {
            add(new Permutation((IntegerVariable[]) num.getLine(i),
                                (IntegerVariable[]) col.getColumn(i)));
        }
        Collection variables = new ArrayList(3*n*n);
        variables.addAll(Arrays.asList(num.getElements()));
        variables.addAll(Arrays.asList(row.getElements()));
        variables.addAll(Arrays.asList(col.getElements()));
        setVariables((IntegerVariable[]) variables
                     .toArray(new IntegerVariable[] {}));
    }
}
/*
 * $Log$
 * Revision 1.5  2005/09/23 07:50:11  yan
 * fixed style
 *
 * Revision 1.4  2005/09/21 13:06:00  yan
 * improved modelling
 *
 * Revision 1.3  2005/09/20 17:32:46  yan
 * fixed jdoc
 *
 * Revision 1.2  2005/09/19 14:11:19  yan
 * fixed modelling
 *
 * Revision 1.1  2005/09/19 13:19:06  yan
 * initial revision
 *
 */
