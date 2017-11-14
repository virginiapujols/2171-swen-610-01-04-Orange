package com.webcheckers.model;

public enum MessageStatus {
    INFO("info"),
    ERROR("error");

    private String description;
    MessageStatus(String name) {
        this.description = name;
    }

    public String getDescription() {
        return description;
    }
}
