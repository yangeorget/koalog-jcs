package com.koalog.jcs.examples;

import org.apache.log4j.PropertyConfigurator;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.solver.DefaultFFSolver;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.arithmetic.ConstantWeightedSums;
import com.koalog.jcs.constraint.arithmetic.AllDifferent;

/**
 * This problem comes from the newsgroup <TT>rec.puzzle</TT>.  
 * The numbers from 1 to 26 are assigned to the letters of the alphabet.
 * The numbers beside each word are the total of the values assigned to the 
 * letters in the word (e.g for LYRE: L,Y,R,E might be to equal 5,9,20 and 13 
 * or any other combination that add up to 47).
 *
 * Find the value of each letter under the equations:                      
 * <PRE>                                                                 
 *    BALLET  45     GLEE  66     POLKA      59     SONG     61            
 *    CELLO   43     JAZZ  58     QUARTET    50     SOPRANO  82            
 *    CONCERT 74     LYRE  47     SAXOPHONE 134     THEME    72            
 *    FLUTE   30     OBOE  53     SCALE      51     VIOLIN  100            
 *    FUGUE   50     OPERA 65     SOLO       37     WALTZ    34   
 * </PRE>
 *
 * @author Daniel Diaz
 * @author Yan Georget
 */
public class AlphaProblem extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     */
    public AlphaProblem() {
        super();
        // Note about the modelling: 
        // this problem is not suited for AllDifferentConstantSum
        IntegerVariable a = new IntegerVariable("a",1,26);
        IntegerVariable b = new IntegerVariable("b",1,26);
        IntegerVariable c = new IntegerVariable("c",1,26);
        IntegerVariable d = new IntegerVariable("d",1,26);
        IntegerVariable e = new IntegerVariable("e",1,26);
        IntegerVariable f = new IntegerVariable("f",1,26);
        IntegerVariable g = new IntegerVariable("g",1,26);
        IntegerVariable h = new IntegerVariable("h",1,26);
        IntegerVariable i = new IntegerVariable("i",1,26);
        IntegerVariable j = new IntegerVariable("j",1,26);
        IntegerVariable k = new IntegerVariable("k",1,26);
        IntegerVariable l = new IntegerVariable("l",1,26);
        IntegerVariable m = new IntegerVariable("m",1,26);
        IntegerVariable n = new IntegerVariable("n",1,26);
        IntegerVariable o = new IntegerVariable("o",1,26);
        IntegerVariable p = new IntegerVariable("p",1,26);
        IntegerVariable q = new IntegerVariable("q",1,26);
        IntegerVariable r = new IntegerVariable("r",1,26);
        IntegerVariable s = new IntegerVariable("s",1,26);
        IntegerVariable t = new IntegerVariable("t",1,26);
        IntegerVariable u = new IntegerVariable("u",1,26);
        IntegerVariable v = new IntegerVariable("v",1,26);
        IntegerVariable w = new IntegerVariable("w",1,26);
        IntegerVariable x = new IntegerVariable("x",1,26);
        IntegerVariable y = new IntegerVariable("y",1,26);
        IntegerVariable z = new IntegerVariable("z",1,26);
        IntegerVariable[] vars = new IntegerVariable[] 
            {a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z};
        add(new AllDifferent(vars));
        add(new ConstantWeightedSums(new int[][]{
           //A B C D E F G H I J K L M N O P Q R S T U V W X Y Z    
            {1,1,0,0,1,0,0,0,0,0,0,2,0,0,0,0,0,0,0,1,0,0,0,0,0,0}, //BALLET
            {0,0,2,0,1,0,0,0,0,0,0,0,0,1,1,0,0,1,0,1,0,0,0,0,0,0}, //CONCERT
            {0,0,1,0,1,0,0,0,0,0,0,2,0,0,1,0,0,0,0,0,0,0,0,0,0,0}, //CELLO
            {0,0,0,0,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1,1,0,0,0,0,0}, //FLUTE
            {0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0}, //FUGUE
            {0,0,0,0,2,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, //GLEE
            {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2}, //JAZZ
            {0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,1,0}, //LYRE
            {0,1,0,0,1,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0}, //OBOE
            {1,0,0,0,1,0,0,0,0,0,0,0,0,0,1,1,0,1,0,0,0,0,0,0,0,0}, //OPERA
            {1,0,0,0,0,0,0,0,0,0,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0}, //POLKA
            {1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,2,1,0,0,0,0,0}, //QUARTET
            {1,0,0,0,1,0,0,1,0,0,0,0,0,1,2,1,0,0,1,0,0,0,0,1,0,0}, //SAXOPHONE
            {1,0,1,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0}, //SCALE
            {0,0,0,0,0,0,0,0,0,0,0,1,0,0,2,0,0,0,1,0,0,0,0,0,0,0}, //SOLO
            {0,0,0,0,0,0,1,0,0,0,0,0,0,1,1,0,0,0,1,0,0,0,0,0,0,0}, //SONG
            {1,0,0,0,0,0,0,0,0,0,0,0,0,1,2,1,0,1,1,0,0,0,0,0,0,0}, //SOPRANO
            {0,0,0,0,2,0,0,1,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0}, //THEME
            {0,0,0,0,0,0,0,0,2,0,0,1,0,1,1,0,0,0,0,0,0,1,0,0,0,0}, //VIOLIN
            {1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,1,0,0,1}, //WALTZ
        }, 
                                   vars, 
                                   new int[] {45,
                                              74,
                                              43,
                                              30,
                                              50,
                                              66,
                                              58,
                                              47,
                                              53,
                                              65,
                                              59,
                                              50,
                                              134,
                                              51,
                                              37,
                                              61,
                                              82,
                                              72,
                                              100,
                                              34},
                                     ConstantWeightedSums.BOTH)); 
        setVariables(vars);
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
        new DefaultFFSolver(new AlphaProblem()).solve(1);
    }
}
