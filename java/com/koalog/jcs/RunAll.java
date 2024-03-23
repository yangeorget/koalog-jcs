package com.koalog.jcs;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Runs all the tests.
 * @author Yan Georget
 */
public class RunAll {
    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    /** 
     * Configures the logs and runs the test suite.
     * @param args the JVM arguments
     */
    public static void main(String[] args) {
        switch (args.length) {
        case 0:
            BasicConfigurator.configure();
            break;
        case 1:
            if (args[0].endsWith(".properties")) {
                PropertyConfigurator.configure(args[0]);
            } else {
                DOMConfigurator.configure(args[0]);
            }
            break;
        default:
            throw new IllegalArgumentException("Invalid number of arguments.");
        }
        TestRunner.run(suite());
    }
    
    /**
     * Returns a test suite.
     * @return a test
     */
    public static Test suite() {
        TestSuite suite= new TestSuite("all");
        suite.addTest(com.koalog.jcs.constraint.TestSuite.suite());
        suite.addTest(com.koalog.jcs.constraint.arithmetic.TestSuite.suite());
        suite.addTest(com.koalog.jcs.constraint.bool.TestSuite.suite());
        suite.addTest(com.koalog.jcs.constraint.set.TestSuite.suite());
        suite.addTest(com.koalog.jcs.domain.TestSuite.suite());
        suite.addTest(com.koalog.jcs.choicepoint.TestSuite.suite());
        suite.addTest(com.koalog.jcs.examples.TestSuite.suite());
        suite.addTest(com.koalog.jcs.optimizer.TestSuite.suite());
        suite.addTest(com.koalog.jcs.scheduler.TestSuite.suite());
        suite.addTest(com.koalog.jcs.solver.TestSuite.suite());
        suite.addTest(com.koalog.jcs.timetabling.TestSuite.suite());
        suite.addTest(com.koalog.jcs.variable.TestSuite.suite());
        return suite;
    }
}
/*
 * $Log$
 * Revision 1.10  2005/07/19 15:52:40  yan
 * added a testsuite for timetabling
 *
 * Revision 1.9  2004/04/13 15:29:19  yan
 * added a test for choice point stack
 *
 * Revision 1.8  2003/06/19 15:02:41  yan
 * added tests for constraint.set package
 *
 * Revision 1.7  2003/04/03 15:14:20  yan
 * added tests for scheduler
 *
 * Revision 1.6  2003/02/04 17:03:09  yan
 * no modif
 *
 * Revision 1.5  2002/10/07 10:17:39  yan
 * fixed style
 *
 * Revision 1.4  2002/09/16 08:04:05  yan
 * *** empty log message ***
 *
 * Revision 1.3  2002/05/02 16:23:47  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
