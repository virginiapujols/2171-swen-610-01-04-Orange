package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Board implements Iterable<Row>{

    private List<Row> rows;
    private boolean didMove = false;


    /**
     * This is the constructor for the "Board" class.
     */
    public Board() {
        rows = new ArrayList<>();
        for(int i=0; i<=7; i++) {
            rows.add(new Row(i));
        }
    }
   
    /**
     * This function updates the board after moves
     * @return the updated board
     */
    public Board updateBoard() {
        Board board = new Board();
        return board;
    }

    public List<Row> getRows() {
        return this.rows;
    }

    public void setRows(List<Row> _rows) {
        this.rows = _rows;
    }

    public boolean didMove() {
        return didMove;
    }

    public void setDidMove(boolean didMove) {
        this.didMove = didMove;
    }

    public void movePiece(Move move) {
        int startRow = move.getStart().getRow();
        int startCell = move.getStart().getCell();
        int endRow = move.getEnd().getRow();
        int endCell = move.getEnd().getCell();

        Space startSpace = rows.get(startRow).getSpaces().get(startCell);
        Space endSpace = rows.get(endRow).getSpaces().get(endCell);
        Piece piece = startSpace.getPiece();

        startSpace.setPiece(null);
        endSpace.setPiece(piece);
        didMove = true;
    }

    public Message validateMove(Move move) {
        int startRow = move.getStart().getRow();
        int startCell = move.getStart().getCell();
        int endRow = move.getEnd().getRow();
        int endCell = move.getEnd().getCell();

        Space startSpace = rows.get(startRow).getSpaces().get(startCell);
        Space endSpace = rows.get(endRow).getSpaces().get(endCell);
        Piece piece = startSpace.getPiece();

        if(!didMove) {
            if(endSpace.getPiece() == null) {
                if (piece.getColor().equals("RED")) {
                    if (endRow == startRow - 1) {
                        return new Message("Valid Move", "info");
                    } else {
                        return new Message("You must move 1 row forward", "error");
                    }
                } else if (piece.getColor().equals("WHITE")) {
                    if (endRow == startRow + 1) {
                        return new Message("Valid Move", "info");
                    } else {
                        return new Message("You must move 1 row forward", "error");
                    }
                }
            } else {
                return new Message("A Piece is already in that Square", "error");
            }
        } else {
            return new Message("You have already made a move this turn", "error");
        }
        //catch all
        return new Message("Invalid Move", "error");
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
