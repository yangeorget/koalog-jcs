package com.koalog.jcs.examples;

import junit.framework.TestCase;

import org.apache.log4j.Category;

/**
 * @author Yan Georget
 */
public class CMC05Test extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public CMC05Test(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(CMC05Test.class);

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    public void testBMS_10_10_35_1() {
        new CMC05Minimizer(new CMC05Problem(CMC05Problem.BMS_10_10_35_1)).optimize();
    }

    public void testBMS_10_10_35_2() {
        new CMC05Minimizer(new CMC05Problem(CMC05Problem.BMS_10_10_35_2)).optimize();
    }

    public void testBMS_10_10_35_3() {
        new CMC05Minimizer(new CMC05Problem(CMC05Problem.BMS_10_10_35_3)).optimize();
    }

    public void testBMS_10_10_35_4() {
        new CMC05Minimizer(new CMC05Problem(CMC05Problem.BMS_10_10_35_4)).optimize();
    }

    public void testBMS_10_10_35_5() {
        new CMC05Minimizer(new CMC05Problem(CMC05Problem.BMS_10_10_35_5)).optimize();
    }

    public void testBMS_10_20_35_6() {
        new CMC05Minimizer(new CMC05Problem(CMC05Problem.BMS_10_20_35_6)).optimize();
    }

    public void testBMS_10_20_35_7() {
        new CMC05Minimizer(new CMC05Problem(CMC05Problem.BMS_10_20_35_7)).optimize();
    }

    public void testBMS_10_20_35_8() {
        new CMC05Minimizer(new CMC05Problem(CMC05Problem.BMS_10_20_35_8)).optimize();
    }

    public void testBMS_10_20_35_9() {
        new CMC05Minimizer(new CMC05Problem(CMC05Problem.BMS_10_20_35_9)).optimize();
    }

    public void testBMS_10_20_35_10() {
        new CMC05Minimizer(new CMC05Problem(CMC05Problem.BMS_10_20_35_10)).optimize();
    }

    public void testBMS_15_30_35_11() {
        new CMC05Minimizer(new CMC05Problem(CMC05Problem.BMS_15_30_35_11)).optimize();
    }
}
/*
 * $Log$
 * Revision 1.3  2005/06/13 14:02:54  yan
 * fastest version so far
 *
 * Revision 1.2  2005/05/24 09:37:26  yan
 * working version
 *
 * Revision 1.1  2005/05/16 14:43:59  yan
 * initial revision
 *
 */
