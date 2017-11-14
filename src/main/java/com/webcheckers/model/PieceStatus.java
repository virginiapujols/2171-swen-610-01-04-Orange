package com.webcheckers.model;

public enum PieceStatus {
    SINGLE("SINGLE"),
    KING("KING");

    private String description;
    PieceStatus(String name) {
        this.description = name;
    }

    public String getDescription() {
        return description;
    }
}
