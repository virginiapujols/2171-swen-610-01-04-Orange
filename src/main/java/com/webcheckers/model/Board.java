package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Board implements Iterable<Row>{

    private List<Row> rows;

    public Board() {
        rows = new ArrayList<>();
        for(int i=0; i<=7; i++) {
            rows.add(new Row(i));
        }
    }

    public Board updateBoard() {
        Board board = new Board();
        return board;
    }

    @Override
    public Iterator<Row> iterator() {
        return rows.iterator();
    }

    @Override
    public void forEach(Consumer<? super Row> action) {

    }

    @Override
    public Spliterator<Row> spliterator() {
        return null;
    }
}
