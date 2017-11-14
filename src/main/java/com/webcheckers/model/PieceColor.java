package com.webcheckers.model;

public enum PieceColor {
    RED("RED"),
    WHITE("WHITE");

    private String description;
    PieceColor(String name) {
        this.description = name;
    }

    public String getDescription() {
        return description;
    }
}
