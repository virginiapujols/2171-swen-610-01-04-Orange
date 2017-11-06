package com.webcheckers.model;

/**
 * A Value Object that represents a Message sent to a User for feedback
 */
public class Message {
    //Constants
    public static String MESSAGE_ERROR = "error";
    public static String MESSAGE_INFO = "info";

    //Attributes
    private String text;
    private String type;

    /**
     * Parameterized Constructor for Message Class
     * @param _text The message's text
     * @param _type The message's type
     */
    public Message(String _text, String _type) {
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
    public String getType() {
        return this.type;
    }

}
