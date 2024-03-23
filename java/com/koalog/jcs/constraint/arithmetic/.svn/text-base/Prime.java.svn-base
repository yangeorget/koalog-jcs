package com.koalog.jcs.constraint.arithmetic;

import org.apache.log4j.Category;
import com.koalog.jcs.variable.IntegerVariable;

/**
 * This constraint forces x to be a prime number (less than 256). 
 *
 * <P>Note: this constraint is of no interest if the domain of x is sparse.
 *
 * <P>Algorithmic informations 
 * (see 
 * {@link com.koalog.jcs.constraint.Constraint Constraint}
 * for more details on complexity and idempotence):
 * <TABLE border="1">
 * <TR>
 *    <TD><B>Idempotent</B></TD>
 *    <TD align="right">yes</TD>
 * </TR>
 * <TR>
 *    <TD><B>Complexity</B></TD>
 *    <TD align="right">1</TD>
 * </TR>
 * </TABLE>
 *
 * @author Yan Georget
 * @since 2.3
 */
public class Prime extends InDomain {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(Prime.class);

    /** The list of the prime numbers less than 256. */
    public static final int[] PRIMES_256 = new int[] {
        2,      
        3,
        5,
        7,      
        11,
        13,
        17,
        19,      
        23,
        29,
        31,
        37,      
        41,
        43,
        47, 
        53,      
        59,
        61,
        67,
        71,      
        73,
        79, 
        83,  
        89,      
        97,
        101, 
        103, 
        107,      
        109,
        113,
        127,
        131,      
        137,
        139, 
        149,
        151,      
        157,
        163, 
        167,   
        173,      
        179,
        181,
        191, 
        193,      
        197,
        199,  
        211, 
        223,      
        227,  
        229, 
        233, 
        239,      
        241,
        251      
    };
 
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param x an integer variable
     */
    public Prime(IntegerVariable x) {
        super(x, PRIMES_256);
        name = "prime(x)";
    }
}
/*
 * $Log$
 * Revision 1.3  2004/09/15 16:03:17  yan
 * fixed style
 *
 * Revision 1.2  2004/09/08 13:15:56  yan
 * fixed style
 *
 * Revision 1.1  2004/09/08 13:13:10  yan
 * initial revision
 *
 */
