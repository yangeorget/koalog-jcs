package com.koalog.jcs.examples;

import org.apache.log4j.PropertyConfigurator;
import com.koalog.util.matrix.BaseMatrix;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.arithmetic.Dist;
import com.koalog.jcs.constraint.arithmetic.Eq;
import com.koalog.jcs.constraint.arithmetic.Shift;
import com.koalog.jcs.constraint.arithmetic.AllDifferent;

/**
 *
 * Lewis Carrol's classical puzzle with five houses and a zebra:
 * 
 * <P>Five men with different nationalities live in the first five houses
 * of a street.  They practise five distinct professions, and each of
 * them has a favourite animal and a favourite drink, all of them
 * different.  The five houses are painted in different colours.
 * 
 * <P>The Englishman lives in a red house.
 * The Spaniard owns a dog.
 * The Japanese is a painter.
 * The Italian drinks tea.
 * The Norwegian lives in the first house on the left.
 * The owner of the green house drinks coffee.
 * The green house is on the right of the white one.
 * The sculptor breeds snails.
 * The diplomat lives in the yellow house.
 * Milk is drunk in the middle house.
 * The Norwegian's house is next to the blue one.
 * The violinist drinks fruit juice.
 * The fox is in a house next to that of the doctor.
 * The horse is in a house next to that of the diplomat.
 * 
 * <P>Who owns a Zebra, and who drinks water?
 * 
 *
 * @author Yan Georget
 */
public class ZebraProblem extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     */
    public ZebraProblem() {
        super();
        IntegerVariable[][] vars = new IntegerVariable[5][5]; // the houses
        vars[0][0] = new IntegerVariable("English", 0, 4);
        vars[0][1] = new IntegerVariable("Spaniard", 0, 4);
        vars[0][2] = new IntegerVariable("Japanese", 0, 4);
        vars[0][3] = new IntegerVariable("Italian", 0, 4);
        vars[0][4] = new IntegerVariable("Norwegian", 0);
        vars[1][0] = new IntegerVariable("Red", 0, 4);
        vars[1][1] = new IntegerVariable("Green", 0, 4);
        vars[1][2] = new IntegerVariable("White", 0, 4);
        vars[1][3] = new IntegerVariable("Yellow", 0, 4);
        vars[1][4] = new IntegerVariable("Blue", 0, 4);
        vars[2][0] = new IntegerVariable("Painter", 0, 4);
        vars[2][1] = new IntegerVariable("Sculptor", 0, 4);
        vars[2][2] = new IntegerVariable("Diplomat", 0, 4);
        vars[2][3] = new IntegerVariable("Violonist", 0, 4);
        vars[2][4] = new IntegerVariable("Doctor", 0, 4);
        vars[3][0] = new IntegerVariable("Dog", 0, 4);
        vars[3][1] = new IntegerVariable("Snails", 0, 4);
        vars[3][2] = new IntegerVariable("Fox", 0, 4);
        vars[3][3] = new IntegerVariable("Horse", 0, 4);
        vars[3][4] = new IntegerVariable("Zebra", 0, 4);
        vars[4][0] = new IntegerVariable("Tea", 0, 4);
        vars[4][1] = new IntegerVariable("Coffee", 0, 4);
        vars[4][2] = new IntegerVariable("Milk", 2);
        vars[4][3] = new IntegerVariable("Juice", 0, 4);
        vars[4][4] = new IntegerVariable("Water", 0, 4);
        BaseMatrix m = new BaseMatrix(vars);
        for (int i=0; i<5; i++) {
            add(new AllDifferent((IntegerVariable[]) m.getLine(i)));
        }
        add(new Eq(vars[0][0], vars[1][0]));
        add(new Eq(vars[0][1], vars[3][0]));
        add(new Eq(vars[0][2], vars[2][0]));
        add(new Eq(vars[0][3], vars[4][0]));
        add(new Eq(vars[1][1], vars[4][1]));
        add(new Eq(vars[2][1], vars[3][1]));
        add(new Eq(vars[2][2], vars[1][3]));
        add(new Eq(vars[2][3], vars[4][3]));
        add(new Shift(vars[1][1], vars[1][2], 1));
        add(new Dist(new IntegerVariable(1), vars[0][4], vars[1][4]));
        add(new Dist(new IntegerVariable(1), vars[3][2], vars[2][4]));
        add(new Dist(new IntegerVariable(1), vars[3][3], vars[2][2]));
        setVariables((IntegerVariable[]) m.getElements());
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
        new DefaultSplitSolver(new ZebraProblem()).solve();
    }
}

