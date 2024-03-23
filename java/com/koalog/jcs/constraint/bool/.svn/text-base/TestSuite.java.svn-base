package com.koalog.jcs.constraint.bool;

import java.lang.reflect.Constructor;
import junit.framework.Test;
import junit.textui.TestRunner;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Runs all the tests of the bool package.
 * @author Matthieu Philip
 * @author Yan Georget
 */
public class TestSuite extends junit.framework.TestSuite {
    /**
     * Construct a suite with all test classes found in the package.
     * So far, no automatic way to load all the package classes has been
     * found. The classes have to be manually added here in the constructor.
     * @param name the name of this test suite.
     */
    public TestSuite(String name) {
        super(name);
        addTestSuite(AndTest.class);
        addTestSuite(AreEqualTest.class);
        addTestSuite(ConjunctionTest.class);
        addTestSuite(EqualsTest.class);
        addTestSuite(FalseTest.class);
        addTestSuite(IfThenElseTest.class);
        addTestSuite(IsInDomainTest.class);
        addTestSuite(IsSmallerTest.class);
        addTestSuite(NotTest.class);
        addTestSuite(OrTest.class);
        addTestSuite(TrueTest.class);
    }

    /**
     * Run a specified test or all tests in this package.
     * Use as follows:
     * <P><TT>TestSuite [&lt;class name&gt; &lt;function name&gt;] [&lt;log 
     * configuration filename&gt;]</TT>
     * <BR>where:
     * <UL>
     * <LI>&lt;class name&gt; is the name of the test class in case of a
     * single test execution. If this argument is specified, then the
     * following one must be specified as well.</LI>
     * <LI>&lt;function name&gt; is the name of the test function to execute
     * in the case of a single test execution. If this argument is specified,
     * then the previous one must be specified as well.</LI>
     * <LI>&lt;log configuration filename&gt; is the name of the Log4j
     * configuration file.</LI>
     * </UL>
     * @param args the JVM arguments as an array of strings
     * @throws Exception when the arguments are not valid
     */
    public static void main(String[] args) throws Exception {
        if (args.length > 3) {
            throw new IllegalArgumentException("Invalid number of arguments.");
        }
        String logFilename = null;
        Test test;
        if (args.length % 2 == 1) {
            logFilename = args[args.length - 1];
        }
        // enable logging
        if (logFilename != null) {
            if (logFilename.endsWith(".properties")) { 
                // didn't find a better way than checking the file extension
                PropertyConfigurator.configure(logFilename);
            } else {
                DOMConfigurator.configure(logFilename);
            }
        } else {
            BasicConfigurator.configure();
        }
        // now instantiate the test class
        if (args.length > 1) {
            // we're in the single test case
            // get the class
            Class testClass = Class.forName(args[0]);
            // get the constructor
            Constructor c = testClass.getConstructor(new Class[] {
                String.class
            });
            // instantiate
            test = (Test)c.newInstance(new Object[] {args[1]});
        } else {
            // run all tests
            test = suite();
        }
        TestRunner.run(test);
    }

    /**
     * Build a test suite for all test classes in this package.
     * @return the resulting test suite.
     */
    public static Test suite() {
        return new TestSuite("bool");
    }
}
/*
 * $Log$
 * Revision 1.10  2005/07/25 10:10:32  yan
 * renamed InSet into InRange
 *
 * Revision 1.9  2005/07/22 17:03:29  yan
 * added a test for InSet
 *
 * Revision 1.8  2004/09/17 09:54:31  yan
 * removed IsGreater
 *
 * Revision 1.7  2004/04/13 17:04:47  yan
 * removed some tests
 *
 * Revision 1.6  2002/10/05 12:45:17  yan
 * added tests
 *
 * Revision 1.5  2002/09/16 08:04:05  yan
 * *** empty log message ***
 *
 * Revision 1.4  2002/05/02 16:19:06  yan
 * moved
 *
 * Revision 1.3  2002/04/22 12:44:42  yan
 * *** empty log message ***
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
