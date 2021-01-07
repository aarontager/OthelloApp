package com.mcon521.othello.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mcon521.othello.R;
import com.mcon521.othello.lib.Utils;

import static com.mcon521.othello.activities.MainActivity.*;

public class OthelloAdapter extends RecyclerView.Adapter<OthelloVH> {
    private TextView tvWhite, tvBlack, tvTurn;

    public OthelloAdapter(TextView white, TextView black, TextView turn) {
        this.tvWhite = white;
        this.tvBlack = black;
        this.tvTurn = turn;
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
        ImageView img = holder.imgtile;
        // Note that position is a 1-d array, while the model is a 2-d array
        int[] gridPos = mapPositionToBoard(position);
        switch (mGame.getCell(gridPos[0], gridPos[1])) {
            case NONE:
                img.setImageResource(R.drawable.blank_tile);
                break;
            case BLACK:
                img.setImageResource(R.drawable.black_tile);
                break;
            case WHITE:
                img.setImageResource(R.drawable.white_tile);
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
            if (mGame.isGameOver()) {
                setWinner(img.getContext());
            }
        });
    }

    private void setWinner(Context c) {
        int whiteScore = mGame.countPieces(CellState.WHITE);
        int blackScore = mGame.countPieces(CellState.BLACK);

        if (whiteScore > blackScore) {
            whiteWinCount++;
            Utils.showInfoDialog(c, "Winner!", "White won this time!");
        } else {
            blackWinCount++;
            Utils.showInfoDialog(c, "Winner!", "Black won this time!");
        }

        mGame = new OthelloModelTwoPlayer();
        this.notifyDataSetChanged();
    }

    private void updateScoreTracker() {
        tvWhite.setText("White score: " + mGame.countPieces(CellState.WHITE));
        tvBlack.setText("Black score: " + mGame.countPieces(CellState.BLACK));
        tvTurn.setText(turn.toString() + "'s turn");
    }

    @Override
    public int getItemCount() {
        return mGame.getBoardSize();
    }

    private int[] mapPositionToBoard(int pos) {
        return new int[]{pos / 8, pos % 8};
    }
}
