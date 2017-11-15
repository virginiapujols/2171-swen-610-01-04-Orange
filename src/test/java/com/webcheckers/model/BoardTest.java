package com.webcheckers.model;

import org.junit.Test;
import org.mockito.Mock;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BoardTest {

    Board CuT = new Board();

    @Test
    public void setRows() throws Exception {
        List<Row> rows = CuT.getRows();
        assertEquals(rows, CuT.getRows());
    }

    @Test
    public void didMove() throws Exception {
        Board test = new Board();
        test.setDidMove(false);
        assertEquals(false, test.didMove());
    }

    @Test
    public void movePiece() throws Exception {
        Move move = mock(Move.class);
        Coordinate c1 = mock(Coordinate.class);
        Coordinate c2 = mock(Coordinate.class);
        when(move.getStart()).thenReturn(c1);
        when(move.getEnd()).thenReturn(c2);

        when(c1.getRow()).thenReturn(2);
        when(c1.getCell()).thenReturn(1);
        when(c2.getRow()).thenReturn(3);
        when(c2.getCell()).thenReturn(0);
        assertFalse(CuT.didMove());
        CuT.movePiece(move);
        assertTrue(CuT.didMove());
    }

    @Test
    public void validateMoveIsValidRED() throws Exception {
        Coordinate c1 = mock(Coordinate.class);
        Coordinate c2 = mock(Coordinate.class);

        when(c1.getRow()).thenReturn(2);
        when(c1.getCell()).thenReturn(1);
        when(c2.getRow()).thenReturn(3);
        when(c2.getCell()).thenReturn(0);

        Move move = new Move(c1, c2);
        Message message = CuT.validateMove(move);
        assertEquals(MessageStatus.info, message.getType());
        assertEquals("Valid Move", message.getText());
    }

    @Test
    public void validateMoveIsInvalidRED() throws Exception {
        Move move = mock(Move.class);
        Coordinate c1 = mock(Coordinate.class);
        Coordinate c2 = mock(Coordinate.class);
        PieceColor test = PieceColor.RED;

        when(move.getStart()).thenReturn(c1);
        when(move.getEnd()).thenReturn(c2);
        when(c1.getRow()).thenReturn(0);
        when(c1.getCell()).thenReturn(1);
        when(c2.getRow()).thenReturn(0);
        when(c2.getCell()).thenReturn(0);

        Message message = CuT.validateMove(move);
        assertEquals(MessageStatus.error, message.getType());
        assertEquals("You must move diagonally 1 cell forward", message.getText());
    }

    @Test
    public void validateMoveIsINValidWHITE() throws Exception {
        Move move = mock(Move.class);
        Coordinate c1 = mock(Coordinate.class);
        Coordinate c2 = mock(Coordinate.class);
        PieceColor test = PieceColor.WHITE;
        when(move.getStart()).thenReturn(c1);
        when(move.getEnd()).thenReturn(c2);
        when(c1.getRow()).thenReturn(5);
        when(c1.getCell()).thenReturn(1);
        when(c2.getRow()).thenReturn(7);
        when(c2.getCell()).thenReturn(0);

        Message message = CuT.validateMove(move);
         //System.out.println(message.getType());
         //System.out.println(message.getText());
        assertEquals(MessageStatus.error, message.getType());
        assertEquals("A Piece is already in that Square", message.getText());
    }

    @Test
    public void validateMoveIsInvalidWHITE() throws Exception {
        Move move = mock(Move.class);
        Coordinate c1 = mock(Coordinate.class);
        Coordinate c2 = mock(Coordinate.class);
        PieceColor test = PieceColor.WHITE;

        when(move.getStart()).thenReturn(c1);
        when(move.getEnd()).thenReturn(c2);
        when(c1.getRow()).thenReturn(5);
        when(c1.getCell()).thenReturn(0);
        when(c2.getRow()).thenReturn(4);
        when(c2.getCell()).thenReturn(1);

        Message message = CuT.validateMove(move);
        assertEquals(MessageStatus.error, message.getType());
        assertEquals("You must move diagonally 1 cell forward", message.getText());
    }

    @Test
    public void moveMade() {
        Move move = mock(Move.class);
        Coordinate c1 = mock(Coordinate.class);
        Coordinate c2 = mock(Coordinate.class);
        when(move.getStart()).thenReturn(c1);
        when(move.getEnd()).thenReturn(c2);
        when(c1.getRow()).thenReturn(5);
        when(c1.getCell()).thenReturn(0);
        when(c2.getRow()).thenReturn(4);
        when(c2.getCell()).thenReturn(1);
        CuT.setDidMove(true);

        Message message = CuT.validateMove(move);
        assertEquals(MessageStatus.error, message.getType());
        assertEquals("You have already made a move this turn", message.getText());
    }

    @Test
    public void validateMoveIsJumpAction() {
        Coordinate startPos = mock(Coordinate.class);
        Coordinate endPos = mock(Coordinate.class);

        when(startPos.getRow()).thenReturn(2);
        when(startPos.getCell()).thenReturn(3);
        
        when(endPos.getRow()).thenReturn(4);
        when(endPos.getCell()).thenReturn(1);

        Move move = new Move(startPos, endPos);

        assertFalse(CuT.getDidJump());
        CuT.movePiece(move);
        assertTrue(CuT.getDidJump());
    }

    @Test
    public void testJumpInvalid() {
        Piece jumpingPiece = mock(Piece.class);
        when(jumpingPiece.getColor()).thenReturn(PieceColor.WHITE);
        when(jumpingPiece.getType()).thenReturn(PieceType.SINGLE);

        Move move = mock(Move.class);
        Coordinate startPos = mock(Coordinate.class);
        Coordinate endPos = mock(Coordinate.class);
        when(startPos.getRow()).thenReturn(3);
        when(startPos.getCell()).thenReturn(4);
        when(endPos.getRow()).thenReturn(5);
        when(endPos.getCell()).thenReturn(2);

        int rowVal = (startPos.getRow() + endPos.getRow()) / 2;
        int cellVal = (startPos.getCell() + endPos.getCell()) / 2;
        Coordinate jumpedCoordinate = new Coordinate(rowVal, cellVal);

        when(move.getStart()).thenReturn(startPos);
        when(move.getEnd()).thenReturn(endPos);
        when(move.getJumpedCoordinate()).thenReturn(jumpedCoordinate);

        Message message = CuT.validateJump(move, jumpingPiece);
        assertEquals(MessageStatus.error, message.getType());
        assertEquals("You cannot jump an empty square!", message.getText());
    }

    @Test
    public void testUndoMove() {
        Coordinate startPos = mock(Coordinate.class);
        Coordinate endPos = mock(Coordinate.class);
        when(startPos.getRow()).thenReturn(2);
        when(startPos.getCell()).thenReturn(3);
        when(endPos.getRow()).thenReturn(4);
        when(endPos.getCell()).thenReturn(1);

        Move move = new Move(startPos, endPos);
        CuT.undoMove(move);
        assertFalse(CuT.didMove());
    }

    @Test
    public void testUndoCapture() {
        Space jumpedSpace = mock(Space.class);
        Piece capturedPiece = mock(Piece.class);
        int stillCapturedCount = 0;
        CuT.undoCapture(jumpedSpace, capturedPiece, stillCapturedCount);
        assertEquals(jumpedSpace.getPiece(), null);
        assertFalse(CuT.getDidJump());
    }

    @Test
    public void testMoveKing() {

        Coordinate c1 = mock(Coordinate.class);
        Coordinate c2 = mock(Coordinate.class);

        when(c1.getRow()).thenReturn(2);
        when(c1.getCell()).thenReturn(1);
        when(c2.getRow()).thenReturn(3);
        when(c2.getCell()).thenReturn(0);

        CuT.setDidMove(false);
        CuT.getRows().get(c1.getRow()).getSpaces().get(c1.getCell()).getPiece().makeKing();

        Move move = new Move(c1, c2);

        Message message = CuT.validateMove(move);
        assertEquals(MessageStatus.info,message.getType());
        assertEquals("Valid Move", message.getText());
    }

    @Test
    public void testValidJumpKing() {

        Coordinate c1 = mock(Coordinate.class);
        Coordinate c2 = mock(Coordinate.class);

        when(c1.getRow()).thenReturn(2);
        when(c1.getCell()).thenReturn(1);
        when(c2.getRow()).thenReturn(4);
        when(c2.getCell()).thenReturn(3);

        CuT.setDidMove(false);
        CuT.getRows().get(c1.getRow()).getSpaces().get(c1.getCell()).getPiece().makeKing();

        // Inserting opponent piece to simulate available jump
        CuT.getRows().get(3).getSpaces().get(2).setPiece(new Piece(PieceType.SINGLE, PieceColor.RED));

        Move move = new Move(c1, c2);

        Message message = CuT.validateMove(move);
        assertEquals(MessageStatus.info,message.getType());
        assertEquals("Valid Move", message.getText());
    }

    @Test
    public void testInvalidJumpKing() {

        Coordinate c1 = mock(Coordinate.class);
        Coordinate c2 = mock(Coordinate.class);

        when(c1.getRow()).thenReturn(2);
        when(c1.getCell()).thenReturn(1);
        when(c2.getRow()).thenReturn(3);
        when(c2.getCell()).thenReturn(0);

        CuT.setDidMove(false);
        CuT.getRows().get(c1.getRow()).getSpaces().get(c1.getCell()).getPiece().makeKing();

        // Inserting opponent piece to simulate available jump
        CuT.getRows().get(3).getSpaces().get(2).setPiece(new Piece(PieceType.SINGLE, PieceColor.RED));

        Move move = new Move(c1, c2);

        Message message = CuT.validateMove(move);
        assertEquals(MessageStatus.error,message.getType());
        assertEquals("You have a jump that you must take", message.getText());
    }

    @Test
    public void testMoveInvalidKing() {
        Coordinate c1 = mock(Coordinate.class);
        Coordinate c2 = mock(Coordinate.class);

        when(c1.getRow()).thenReturn(2);
        when(c1.getCell()).thenReturn(1);
        when(c2.getRow()).thenReturn(3);
        when(c2.getCell()).thenReturn(0);

        CuT.setDidMove(false);
        CuT.setDidJump(true);
        CuT.getRows().get(c1.getRow()).getSpaces().get(c1.getCell()).getPiece().makeKing();

        Move move = new Move(c1, c2);

        Message message = CuT.validateMove(move);
        assertEquals(MessageStatus.error, message.getType());
        assertEquals("You cannot make a regular move after jumping!", message.getText());
    }

    @Test
    public void testArePiecesLeft() throws Exception {
        Piece piece = new Piece(PieceType.KING,PieceColor.WHITE);
        Space space = mock(Space.class);
        when(space.getPiece()).thenReturn(piece);
        CuT.arePiecesLeft(PieceColor.WHITE);
        assertEquals(true, CuT.arePiecesLeft(PieceColor.WHITE));
    }
    @Test
    public void testAreNotPiecesLeft() throws Exception {
        Piece piece = new Piece(PieceType.KING,PieceColor.WHITE);
        Space space = mock(Space.class);
        when(space.getPiece()).thenReturn(piece);
        when(space.getPiece()).thenReturn(null);
        assertEquals(false, CuT.arePiecesLeft(null));
    }
}
