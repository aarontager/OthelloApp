package com.mcon521.othello.classes;

import android.media.tv.TvContentRating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mcon521.othello.R;

import static com.mcon521.othello.activities.MainActivity.*;

public class OthelloAdapter extends RecyclerView.Adapter<OthelloVH> {
    private TextView white, black;

    public OthelloAdapter(TextView white, TextView black) {
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
        switch (mGame.getCell(gridPos[0], gridPos[1])) {
            case NONE:
                holder.imgtile.setImageResource(R.drawable.blank_tile);
                break;
            case BLACK:
                holder.imgtile.setImageResource(R.drawable.black_tile);
                break;
            case WHITE:
                holder.imgtile.setImageResource(R.drawable.white_tile);
                break;

        }

        holder.itemView.setOnClickListener(v -> {
            int[] boardPos = mapPositionToBoard(position);
            if (!mGame.isGameOver()) {
                if (mGame.checkMoveValidity(boardPos, turn, true) > 0) {
                    turn = (turn == CellState.WHITE) ? CellState.BLACK : CellState.WHITE;
                    if (!mGame.hasMove(turn)) {
                        turn = (turn == CellState.WHITE) ? CellState.BLACK : CellState.WHITE;
                    }
                }
                this.notifyDataSetChanged();
                updateScoreTracker();
            }
        });
    }

    private void updateScoreTracker() {
        white.setText("White score: " + mGame.countPieces(CellState.WHITE));
        black.setText("Black score: " + mGame.countPieces(CellState.BLACK));
    }

    @Override
    public int getItemCount() {
        return mGame.getBoardSize();
    }

    private int[] mapPositionToBoard(int pos) {
        return new int[]{pos / 8, pos % 8};
    }
}
