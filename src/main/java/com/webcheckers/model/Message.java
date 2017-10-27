package com.webcheckers.model;

public class Message {
    private String text;
    private String type;

    public Message(String _text, String _type) {
        this.text = _text;
        this.type = _type;
    }

    public String getText() {
        return this.text;
    }

    public String getType() {
        return this.type;
    }

}
