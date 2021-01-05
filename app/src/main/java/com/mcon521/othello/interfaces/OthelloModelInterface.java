package com.mcon521.othello.interfaces;

import com.mcon521.othello.classes.CellState;

public interface OthelloModelInterface {
    CellState getCell(int row, int col);

    int countPieces(CellState state);

    boolean isGameOver();

    boolean hasMove(CellState state);

    int checkMoveValidity(int[] input, CellState state, boolean shouldFlip);

    boolean playerMove(CellState state);

    String tallyScore();
}