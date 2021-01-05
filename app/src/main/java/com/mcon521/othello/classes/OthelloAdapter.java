package com.mcon521.othello.classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mcon521.othello.R;

import java.util.Objects;

public class OthelloAdapter extends RecyclerView.Adapter<OthelloVH> {

    private OthelloModel othelloModel;


    public OthelloAdapter(OthelloModel othelloModel) {
        this.othelloModel = othelloModel;
    }

    @NonNull
    @Override
    public OthelloVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_othello_tile, parent, false);
// Add onclick
        return new OthelloVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OthelloVH holder, int position) {
        // TODO: Get from the Model what color tile (or empty) belongs here.
        // Note that position is a 1-d array, while the model is a 2-d array
        switch (othelloModel.getCell(position)) {
            case NONE:
                holder.imgtile.setImageResource(android.R.drawable.ic_media_pause);
                break;
            case BLACK:
                holder.imgtile.setImageResource(android.R.drawable.ic_media_ff);
                break;
            case WHITE:
                holder.imgtile.setImageResource(android.R.drawable.ic_media_rew);
                break;

        }
    }

    @Override
    public int getItemCount() {
        return othelloModel.getBoardSize();
    }
}
