package com.koalog.jcs.constraint.arithmetic;

import org.apache.log4j.Category;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import com.koalog.util.matrix.SquareMatrix;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.bool.Equals;

/**
 * A relation for latin squares.
 *
 * @author Yan Georget
 * @since 3.0
 */
public class LatinSquare extends BaseProblem {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
       Category.getInstance(LatinSquare.class);

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Main constructor.
     * @param num the values by row then by column
     * @param row the rows by number then by column
     * @param col the columns by number then by row
     */
    public LatinSquare(SquareMatrix num, 
                       SquareMatrix row,
                       SquareMatrix col) {
        super("latinsquare");
        int n = num.getHeight();
        for (int c=0; c<n; c++) {
            for (int i=0; i<n; i++) {
                for (int j=0; j<n; j++) {
                    BooleanVariable b = new BooleanVariable();
                    add(new Equals(b, 
                                   (IntegerVariable)row.getElement(c,j),
                                   i));
                    add(new Equals(b, 
                                   (IntegerVariable)col.getElement(c,i), 
                                   j));
                    add(new Equals(b, 
                                   (IntegerVariable)num.getElement(i,j),
                                   c));
                }
            }
        }
        for (int l=0; l<n; l++) {
            add(new AllDifferent((IntegerVariable[]) num.getLine(l)));
            add(new AllDifferent((IntegerVariable[]) num.getColumn(l)));
            add(new AllDifferent((IntegerVariable[]) row.getLine(l)));
            add(new AllDifferent((IntegerVariable[]) row.getColumn(l)));
            add(new AllDifferent((IntegerVariable[]) col.getLine(l)));
            add(new AllDifferent((IntegerVariable[]) col.getColumn(l)));
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
 * Revision 1.3  2005/09/21 13:06:00  yan
 * improved modelling
 *
 * Revision 1.2  2005/09/20 17:32:46  yan
 * fixed jdoc
 *
 * Revision 1.1  2005/09/19 13:19:06  yan
 * initial revision
 *
 */
