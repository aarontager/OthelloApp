package com.mcon521.othello.classes;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mcon521.othello.R;

public class OthelloVH extends RecyclerView.ViewHolder {

    ImageView imgtile;

    public OthelloVH(@NonNull View itemView) {
        super(itemView);
        imgtile = itemView.findViewById(R.id.img_tile);
    }

}
