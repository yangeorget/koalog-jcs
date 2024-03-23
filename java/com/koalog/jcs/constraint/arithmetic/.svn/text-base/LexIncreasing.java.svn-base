package com.koalog.jcs.constraint.arithmetic;

import com.koalog.util.matrix.BaseMatrix;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.InvalidConstraintException;

/**
 * This relation enforces that the 
 * columns and/or rows of its matrix are lex-increasing.
 *
 * @author Yan Georget
 * @since 3.1
 */
public class LexIncreasing extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Main constructor.
     * @param m a matrix of integer variables
     * @param rows remove symmetries on rows when true
     * @param columns remove symmetries on columns when true
     */    
    public LexIncreasing(BaseMatrix m, 
                         boolean rows, 
                         boolean columns) {
        super("lex_increasing");
        checkArguments(rows, columns);
        // TODO: implement 
        // Arc-Consistency for a Chain of Lexicographic Ordering Constraints
        if (columns) {
            for(int i=1; i<m.getWidth(); i++) {
                add(new LexLeq((IntegerVariable[]) m.getColumn(i-1), 
                               (IntegerVariable[]) m.getColumn(i)));
            }
        }
        if (rows) {
            for(int i=1; i<m.getHeight(); i++) {
                add(new LexLeq((IntegerVariable[]) m.getLine(i-1), 
                               (IntegerVariable[]) m.getLine(i)));
            }
        }
        setVariables((IntegerVariable[]) m.getElements());
    }
    
    /**
     * Auxilliary constructor.
     * @param m a matrix of integer variables as an array of arrays
     * @param rows remove symmetries on rows when true
     * @param columns remove symmetries on columns when true
     */    
    public LexIncreasing(IntegerVariable[][] m, 
                         boolean rows, 
                         boolean columns) {
        this(new BaseMatrix(m), rows, columns);
    }

    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    static void checkArguments(boolean rows, boolean columns) {
        if (!columns && !rows) {
            throw new InvalidConstraintException("columns or rows == false");
        }
    }
}
