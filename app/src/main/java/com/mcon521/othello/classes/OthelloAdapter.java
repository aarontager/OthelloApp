package com.mcon521.othello.classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mcon521.othello.R;

import java.util.Objects;

public class OthelloAdapter extends RecyclerView.Adapter<OthelloVH> {

    private OthelloModel othelloModel;
    private CellState turn;
    private TextView white, black;


    public OthelloAdapter(OthelloModel othelloModel, TextView white, TextView black) {
        this.othelloModel = othelloModel;
        turn = CellState.BLACK;
        this.white = white;
        this.black = black;
    }

    @NonNull
    @Override
    public OthelloVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_othello_tile, parent, false);
        updateScoreTracker();
        return new OthelloVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OthelloVH holder, int position) {
        // Note that position is a 1-d array, while the model is a 2-d array
        int[] gridPos = mapPositionToBoard(position);
        switch (othelloModel.getCell(gridPos[0], gridPos[1])) {
            case NONE:
//                holder.imgtile.setImageResource(android.R.drawable.ic_media_pause);
                break;
            case BLACK:
                holder.imgtile.setImageResource(R.drawable.black_tile);
//                holder.imgtile.setColorFilter(R.color.black);
                break;
            case WHITE:
                holder.imgtile.setImageResource(R.drawable.white_tile);
//                holder.imgtile.setColorFilter(R.color.white);
                break;

        }

        holder.itemView.setOnClickListener(v -> {
            int[] boardPos = mapPositionToBoard(position);
            if(!othelloModel.isGameOver()) {
                if (othelloModel.checkMoveValidity(boardPos, turn, true) > 0) {
                    turn = (turn == CellState.WHITE) ? CellState.BLACK : CellState.WHITE;
                }
                this.notifyDataSetChanged();
                updateScoreTracker();
            }
        });
    }

    private void updateScoreTracker() {
        white.setText("White score: " + othelloModel.countPieces(CellState.WHITE));
        black.setText("Black score: " + othelloModel.countPieces(CellState.BLACK));
    }

    @Override
    public int getItemCount() {
        return othelloModel.getBoardSize();
    }

    private int[] mapPositionToBoard(int pos) {
        return new int[] {pos / 8, pos % 8};
    }
}
