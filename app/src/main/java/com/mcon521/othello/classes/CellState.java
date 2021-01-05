package com.mcon521.othello.classes;

public enum CellState {
    NONE,
    BLACK,
    WHITE;

    public String toString() {
        return this.equals(CellState.NONE) ? " " : this.equals(CellState.BLACK) ? "BLACK" : "WHITE";
    }
}
