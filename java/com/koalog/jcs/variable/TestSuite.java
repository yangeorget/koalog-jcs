package com.koalog.jcs.variable;

import java.lang.reflect.Constructor;
import junit.framework.Test;
import junit.textui.TestRunner;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Runs all the tests of the variable package.
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
        addTestSuite(FalseBooleanVariableTest.class);
        addTestSuite(TrueBooleanVariableTest.class);
        addTestSuite(VariableTest.class);
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
        return new TestSuite("variable");
    }
}
/*
 * $Log$
 * Revision 1.3  2004/05/05 14:00:16  yan
 * fixed style
 *
 * Revision 1.2  2003/03/27 10:43:37  yan
 * added new test
 *
 * Revision 1.1  2002/09/16 08:10:57  yan
 * initial revision
 *
 */
