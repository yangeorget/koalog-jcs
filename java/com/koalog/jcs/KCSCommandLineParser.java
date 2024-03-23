package com.koalog.jcs;

import org.apache.log4j.xml.DOMConfigurator;
import com.koalog.util.cmdline.InvalidCommandLineException;
import com.koalog.util.cmdline.CommandLineOption;
import com.koalog.util.cmdline.DefaultCommandLineParser;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.constraint.BaseProblem;

/**
 * A basic command line parser: mainly to test the version number.
 *
 * @author Yan Georget
 */
public class KCSCommandLineParser extends DefaultCommandLineParser {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     */
    public KCSCommandLineParser() {
        super("kcs",
              null,
              new CommandLineOption[] {});
    }

    //------------------------------------------------------------------------
    // MAIN METHODS
    //------------------------------------------------------------------------
    /**
     * The main method. 
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        DefaultCommandLineParser parser = 
            new KCSCommandLineParser();
        try {
            parser.parse(args);
            String log4j = parser.getValue("log4j");
            if (log4j != null) {
                DOMConfigurator
                    .configure(log4j);
            } else {
                DOMConfigurator
                    .configure(ClassLoader
                               .getSystemResource
                               ("com/koalog/jcs/BasicLogConfig.xml"));
            }
            // hack to check the license
            new DefaultSplitSolver(new BaseProblem());
            if (parser.isEnabled("help")) {
                System.err.println(parser.getUsage());
            } else if (parser.isEnabled("version")) {
                // version already displayed
            } 
        } catch (InvalidCommandLineException icle) {
            DOMConfigurator
                .configure(ClassLoader
                           .getSystemResource
                           ("com/koalog/jcs/BasicLogConfig.xml"));
            System.err.println(parser.getUsage());
        }
    }

}
/*
 * $Log$
 * Revision 1.3  2005/07/03 14:28:40  yan
 * fixed style
 *
 * Revision 1.2  2005/02/18 12:12:08  yan
 * moved main method
 *
 * Revision 1.1  2005/02/18 11:40:34  yan
 * initial revision
 *
 */
