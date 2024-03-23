package com.koalog.jcs.choicepoint;

/**
 * A reference to a storable integer.
 *
 * @see ChoicePointStack
 * @since 2.1
 * @author Yan Georget
 */
public class StorableIntReference extends BaseStorableReference {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------  
    /**
     * Sole constructor.
     * @param i an integer
     */
    public StorableIntReference(int i) {
        super(new StorableIntValue(i));
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------  
    /**
     * Returns the reference as an integer.
     * @return an integer
     */
    public final int getIntValue() {
        return ((StorableIntValue) storableValue).getInt();
    }

    /**
     * Sets the value of the reference to an integer.
     * @param i an int
     */
    public final void setIntValue(int i) {
        ((StorableIntValue) storableValue).setInt(i);
    }
}
