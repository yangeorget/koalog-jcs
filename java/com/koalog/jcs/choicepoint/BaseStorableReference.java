package com.koalog.jcs.choicepoint;

/**
 * The base implementation of a reference to a storable object.
 *
 * @see ChoicePointStack
 * @since 2.1
 * @author Yan Georget
 */
public class BaseStorableReference implements StorableReference {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------  
    /** The value. */
    protected StorableValue storableValue;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------  
    /**
     * Sole constructor.
     * @param storableValue the storable value
     */
    public BaseStorableReference(StorableValue storableValue) {
        this.storableValue = storableValue;
    }

    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------  
    /** @see com.koalog.jcs.choicepoint.StorableReference */
    public final StorableValue getStorableValue() {
        return storableValue;
    }

    /** @see com.koalog.jcs.choicepoint.StorableReference */
    public final void setStorableValue(StorableValue storableValue) {
        this.storableValue = storableValue;
    }
}
