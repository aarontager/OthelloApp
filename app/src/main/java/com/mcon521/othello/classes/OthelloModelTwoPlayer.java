package com.mcon521.othello.classes;

import com.google.gson.Gson;

public class OthelloModelTwoPlayer extends OthelloModel {
    @Override
    public boolean playerMove(CellState state) {
        return true;
    }

    public static OthelloModelTwoPlayer getModelFromJSONString (String json)
    {
        return new Gson().fromJson(json, OthelloModelTwoPlayer.class);
    }
}