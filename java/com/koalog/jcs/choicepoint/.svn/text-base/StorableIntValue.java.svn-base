package com.koalog.jcs.choicepoint;

/**
 * A wrapper class for storing integers on the choice point stack.
 *
 * @see ChoicePointStack
 * @since 2.1
 * @author Yan Georget
 */
public class StorableIntValue implements StorableValue {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------  
    /** The value. */
    int value;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------  
    /**
     * Sole constructor.
     * @param value an integer
     */
    public StorableIntValue(int value) {
        this.value = value;
    }
 
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------  
    /** @see com.koalog.jcs.choicepoint.StorableValue */
    public final StorableValue copy() {
        return new StorableIntValue(value);
    }

    /**
     * Returns the value as an integer.
     * @return an int
     */
    public final int getInt() {
        return value;
    }
    
    /** 
     * Sets the value to an integer.
     * @param value an int
     */
    public final void setInt(int value) {
        this.value = value;
    }
}
/*
 * $Log$
 * Revision 1.2  2004/11/10 09:31:11  yan
 * made some local variables final
 *
 * Revision 1.1  2004/06/15 14:07:30  yan
 * initial revision
 *
 */
