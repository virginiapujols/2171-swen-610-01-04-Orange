package com.webcheckers.model;

/**
 * A Value Object that represents a Message sent to a User for feedback
 */
public class Message {
    //Attributes
    private String text;
    private MessageStatus type;

    /**
     * Parameterized Constructor for Message Class
     * @param _text The message's text
     * @param _type The message's type
     */
    public Message(String _text, MessageStatus _type) {
        this.text = _text;
        this.type = _type;
    }

    /**
     * Accessor for text property
     * @return text The Message's text
     */
    public String getText() {
        return this.text;
    }

    /**
     * Accessor for type property
     * @return type The Message's type
     */
    public MessageStatus getType() {
        return this.type;
    }

}
