package com.koalog.jcs.solver;

import org.apache.log4j.Category;
import java.math.BigInteger;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Date;
import com.koalog.util.I18N;
import com.koalog.util.License;
import com.koalog.util.Infos;
import com.koalog.jcs.InconsistencyException;
import com.koalog.jcs.TimeOutException;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.domain.Domain;
import com.koalog.jcs.variable.Variable;

/**
 * This class is the reification of the concept of solver. 
 *
 * <P>Note: Solvers, starting from <CODE>AbstractSolver</CODE>, 
 * may generate internationalized log messages.</P>
 *
 * @author Yan Georget
 * @since 2.3
 */
public abstract class AbstractSolver implements Solver {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(AbstractSolver.class);

    /** 
     * Modulus of the public key, as string to beneficiate from obfuscation.
     */
    private static final String MODULUS = "19465831884475611385319191764924176715316775747713204757738021873489429333096981659750825659328004473810800014574980053887693298940893940881008501060797556833513994610391663331312433774137311213811692675161724029062065527972250612344718767267931358311998928118902880116379140463120072714042329741978384708764299731245556539778727310886023856603269404782020718496994845402313509825810995998910474195856707282463838876332617866759754724566103058121826928565745367292576532456740953911525404904340267404490896743596017579770198985209378077819118789790750766933665228990297507799360319523907036983957462352666045727148121";
    /** 
     * Exponent of the public key, as string to beneficiate from obfuscation.
     */
    private static final String EXPONENT = "3";
    /**
     * The installed license.
     */
    private static License license;

    private static String runtimeInfos;
    
    static final String KEY_ERR_INV_LIC = "ERR_INV_LIC";
    static final String KEY_ERR_INV_LIC_FMT = "ERR_INV_LIC_FMT";
    static final String KEY_ERR_INV_LIC_PROD = "ERR_INV_LIC_PROD";
    static final String KEY_ERR_INV_LIC_DATE = "ERR_INV_LIC_DATE";
    static final String KEY_LAB_EXPIRES = "LAB_EXPIRES";
    static final String KEY_LAB_EVAL_LIC = "LAB_EVAL_LIC";
    static final String KEY_LAB_DEV_LIC = "LAB_DEV_LIC";
    static final String KEY_LAB_RUN_LIC = "LAB_RUN_LIC";
    static final String KEY_LAB_LICENSED_TO = "LAB_LICENSED_TO";

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------  
    /** The problem to be solved.*/
    protected Problem problem;
    /** The solutions found. */
    protected SolutionStack solutions;
    /** The total time for finding the solutions. */
    protected long totalTime;

    //------------------------------------------------------------------------
    // STATIC CODE
    //------------------------------------------------------------------------
    static {
        // log4j should have been configured 
        checkLicense();
        System.err.println(new Infos("jcs").getFullMessage(getRuntimeInfos()));
    }

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------ 
    /**
     * Constructs a solver for a given problem.
     * @param problem the problem to be solved.
     */
    public AbstractSolver(Problem problem) {
        cat.debug("problem created");
        this.problem = problem;
        this.solutions = new BaseSolutionStack(problem.getVariables());
    }

    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------ 
    /** @see com.koalog.jcs.solver.Solver */
    public final Problem getProblem() {
        return problem;
    }

    /** @see com.koalog.jcs.solver.Solver */
    public final void setProblem(Problem problem) {
        this.problem = problem;
    }

    /** @see com.koalog.jcs.solver.Solver */
    public final SolutionStack getSolutions() {
        return solutions;
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------ 
     /** @see com.koalog.jcs.solver.Solver */
    public void solve() {
        solve(Integer.MAX_VALUE);
    }

    /** @see com.koalog.jcs.solver.Solver */
    public void solve(int max) {
        solve(max, Long.MAX_VALUE/2);
    }

    /** @see com.koalog.jcs.solver.Solver */
    public void solve(int max, long time) {
        initCounters();
        try {
            findSolutions(max, System.currentTimeMillis() + time);
        } catch (InconsistencyException ie) {
            resetCounters();
            noMoreSolution();
        } catch (TimeOutException toe) {
            resetCounters();
            timeOut();
        }
        solved();
    }

    /** @see com.koalog.jcs.solver.Solver */
    public void noMoreSolution() {
        cat.info(I18N.getString(I18N_RESOURCE, 
                                "NO_MORE_SOLUTION", 
                                Locale.getDefault()));
    }
    
    /** @see com.koalog.jcs.solver.Solver */
    public void timeOut() {
        cat.info(I18N.getString(Solver.I18N_RESOURCE, 
                                "TIMEOUT", 
                                Locale.getDefault()));
    }

    /** @see com.koalog.jcs.solver.Solver */
    public void clear() {
        solutions.clear();
        initCounters();
    }

    /** @see com.koalog.jcs.solver.Solver */
    public void initCounters() {
        totalTime = System.currentTimeMillis();
    }

    /** @see com.koalog.jcs.solver.Solver */
    public void resetCounters() {
    }

    /** 
     * Converts a solver to a string.
     * @return a string
     */
    public String toString() {
        return problem.toString();
    }

    /** @see com.koalog.jcs.solver.Solver */
    public void solved() {
        long elapsedTime = System.currentTimeMillis() - totalTime;
        Locale locale = Locale.getDefault();
        StringBuffer buf = 
            new StringBuffer(I18N.getString(I18N_RESOURCE, 
                                            "SOLVED_IN", 
                                            locale));
        buf.append(" ");
        buf.append(elapsedTime);
        buf.append(" ");
        buf.append(I18N.getString(I18N_RESOURCE, 
                                  "MS", 
                                  locale));
        cat.info(buf); 
        cat.info(solutions);
    }

    /**
     * Stores the current solution.
     */
    public void storeSolution() {
        solutions.push(getDomains());
    }

    /**
     * Returns the domains of the variables of the problem.
     * @return an array of domains
     */
    Domain[] getDomains() {
        Variable[] variables = problem.getVariables();
        Domain[] a = new Domain[variables.length];
        for(int i=0; i<a.length; i++) {
            a[i] = (Domain) variables[i].getDomain().copy();
        }
        return a;
    }


    //------------------------------------------------------------------------
    // ABSTRACT METHODS
    //------------------------------------------------------------------------  
    /**
     * Finds at most max solutions during a given time.
     * @param max an integer greater than 0
     * @param timeOut the time-out in milliseconds
     * @throws InconsistencyException when no solution can be found
     * @throws TimeOutException when a time-out occurs
     */
    public abstract void findSolutions(int max, long timeOut) 
        throws InconsistencyException, TimeOutException;

    //------------------------------------------------------------------------
    // PROTECTION METHODS
    //------------------------------------------------------------------------
    /**
     * Decode a string coded with Koalog's private key.
     * It is assumed that the coded string is in hexa, where
     * two consecutive chars represent a byte.
     * @param mes The coded version of the license.
     * @return The license in clear text.
     */
    private static String decode(String mes) {
        try {
            BigInteger n = new BigInteger(MODULUS);
            BigInteger e = new BigInteger(EXPONENT);
            byte[] buf = new byte[mes.length() / 2];
            for (int i = 0; i < mes.length() / 2; i++) {
                buf[i] = (byte)
                    (Integer.parseInt(mes.substring(i * 2, i * 2 + 2),16)-128);
            }
            BigInteger intMes = new BigInteger(buf);
            BigInteger intRes = intMes.modPow(e, n);
            return new String(intRes.toByteArray());
        } catch (NumberFormatException nfe) {
            return null;
        }
    }

    /**
     * Does the necessary check regarding the license. This method
     * should be called right after the log4j configuration.
     * Also updates a string to be displayed.
     */
    private static final void checkLicense() {
        Locale locale = Locale.getDefault();
        license = new License("jcs");
        String codedLicense = license.getLicense();
        // license non null
        if (codedLicense == null) {
            System.out.println(I18N.getString(I18N_RESOURCE, 
                                              KEY_ERR_INV_LIC, 
                                              locale));
            System.exit(1);
        }
        String lic = decode(codedLicense);
        // decoded license non null
        if (lic == null) {
            // invalid format
            System.out.println(I18N.getString(I18N_RESOURCE, 
                                              KEY_ERR_INV_LIC, 
                                              locale));
            System.exit(1);
        }
        String[] properties = new String[5];
        StringTokenizer st = new StringTokenizer(lic, ";");
        int i = 0;
        while (st.hasMoreTokens()) {
            properties[i] = st.nextToken();
            i++;
        }
        if (i < 5) {
            // invalid format
            System.out.println(I18N.getString(I18N_RESOURCE, 
                                              KEY_ERR_INV_LIC_FMT,
                                              locale));
            System.exit(1);
        }
        if (!"jcs".equals(properties[0])) {
            // wrong product
            System.out.println(I18N.getString(I18N_RESOURCE, 
                                              KEY_ERR_INV_LIC_PROD,
                                              locale));
            System.exit(1);
        }
        StringBuffer message = new StringBuffer();
        if ("false".equals(properties[3]) // old encoding 
            || "dev".equals(properties[3])) {
            message.append(I18N.getString(I18N_RESOURCE, 
                                          KEY_LAB_DEV_LIC,
                                          locale));
        } else if ("true".equals(properties[3]) // old encoding 
                   || "eval".equals(properties[3])) {
            message.append(I18N.getString(I18N_RESOURCE, 
                                          KEY_LAB_EVAL_LIC,
                                          locale));
        } else if ("run".equals(properties[3])) {
            message.append(I18N.getString(I18N_RESOURCE, 
                                          KEY_LAB_RUN_LIC,
                                          locale));
        } else {
            System.out.println(I18N.getString(I18N_RESOURCE, 
                                              KEY_ERR_INV_LIC_FMT,
                                              locale));
            System.exit(1);
        }
        // get the date, which is in minutes and coded in hexa
        long date = Long.parseLong(properties[2], 16);
        long diff =  date * Integer.parseInt("60") * Integer.parseInt("1000") 
            - new Date().getTime();
        if (diff < 0) { 
            // has expired
            System.out.println(I18N.getString(I18N_RESOURCE, 
                                              KEY_ERR_INV_LIC_DATE,
                                              locale));
            System.exit(1);
        }
        if (diff / (Integer.parseInt("24") 
                    * Integer.parseInt("60") 
                    * Integer.parseInt("60") 
                    * Integer.parseInt("1000")) < Integer.parseInt("30")) { 
            // this is a time limited license (and will be valid during less
            // than 30 days)
            message.append(" (");
            message.append(I18N.getString(I18N_RESOURCE, 
                                          KEY_LAB_EXPIRES,
                                          locale));
            message.append(" "); 
            message.append(new Date(date 
                                    * Integer.parseInt("60") 
                                    * Integer.parseInt("1000")));
            message.append(")");
        }
        message.append("\n");
        message.append(I18N.getString(I18N_RESOURCE, 
                                      KEY_LAB_LICENSED_TO,
                                      locale));
        message.append(": ");
        message.append(properties[1]);
        runtimeInfos = message.toString();
    }

    /**
     * Returns a dynamic message.
     * @return a string
     */
    public static String getRuntimeInfos() {
        return runtimeInfos;
    }

    /**
     * Indicate whether this is an evaluation license.
     * @return <code>true</code> if this in an evaluation license.
     */
    private static boolean isEval() {
        String codedLicense = license.getLicense();
        String lic = decode(codedLicense);
        String[] properties = new String[5];
        StringTokenizer st = new StringTokenizer(lic, ";");
        int i = 0;
        while (st.hasMoreTokens()) {
            properties[i] = st.nextToken();
            i++;
        }
        return !"false".equals(properties[3]);
    }

    /**
     * Get the license load parameter.
     * @return The licensed load factor.
     */
    private static int getLoad() {
        String codedLicense = license.getLicense();
        String lic = decode(codedLicense);
        String[] properties = new String[5];
        StringTokenizer st = new StringTokenizer(lic, ";");
        int i = 0;
        while (st.hasMoreTokens()) {
            properties[i] = st.nextToken();
            i++;
        }
        return Integer.parseInt(properties[4]);
    }
}
/*
 * $Log$
 * Revision 1.9  2005/07/22 12:03:05  yan
 * made findSolutions public
 *
 * Revision 1.8  2005/07/19 07:40:38  yan
 * real anytime constraint solving
 *
 * Revision 1.7  2005/07/18 17:48:05  yan
 * fixed jdoc
 *
 * Revision 1.6  2005/07/18 17:26:08  yan
 * introduced TimeOutException + various simplifications
 *
 * Revision 1.5  2005/02/18 12:11:18  yan
 * no real change
 *
 * Revision 1.4  2004/10/07 12:14:33  yan
 * added back resetCounters in Solver
 *
 * Revision 1.3  2004/09/30 15:46:49  yan
 * made getDomains() package
 *
 * Revision 1.2  2004/09/17 15:38:14  yan
 * more refactoring
 *
 * Revision 1.1  2004/09/17 14:58:45  yan
 * initial revision
 *
 */
