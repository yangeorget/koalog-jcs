package com.koalog.jcs;

/**
 * A base class for named objects.
 *
 * @since 2.3
 * @author Yan Georget
 */
public class BaseNamedObject implements NamedObject {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The unid. */
    protected int unid;
    /** The user name. */
    protected String name;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------ 
    /** 
     * Main constructor. 
     */
    public BaseNamedObject() {
    }
    
    /** 
     * Auxilliary constructor. 
     * @param name a name
     */
    public BaseNamedObject(String name) {
        this.name = name;
    }

    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.NamedObject */
    public String getName() {
        return name;
    }

    /** @see com.koalog.jcs.NamedObject */
    public final int getUnid() {
        return unid;
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.NamedObject */
    public String getFullName() {
        StringBuffer buf = new StringBuffer(name);
        buf.append(UNID_SEPARATOR);
        buf.append(unid);
        return buf.toString();
    }
}
/*
 * $Log$
 * Revision 1.6  2005/09/20 17:27:53  yan
 * getName not final anymore
 *
 * Revision 1.5  2004/10/12 14:56:04  yan
 * named object are now comparable
 *
 * Revision 1.4  2004/10/08 10:11:42  yan
 * fixed jdoc
 *
 * Revision 1.3  2004/10/01 17:40:03  yan
 * various refactoring
 *
 * Revision 1.2  2004/09/15 15:58:39  yan
 * fixed style
 *
 * Revision 1.1  2004/09/15 15:52:18  yan
 * intial revision
 *
 */
