package com.mcon521.othello.activities;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mcon521.othello.R;
import com.mcon521.othello.classes.CellState;
import com.mcon521.othello.classes.OthelloAdapter;
import com.mcon521.othello.classes.OthelloModel;
import com.mcon521.othello.classes.OthelloModelOnePlayer;
import com.mcon521.othello.classes.OthelloModelTwoPlayer;
import com.mcon521.othello.lib.Utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView white, black;

    public static OthelloModel mGame;
    public static CellState turn;
    public static int gamesPlayedCount, blackWinCount, whiteWinCount;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("GAME", mGame.getJSONStringFromThis());
        outState.putString("TURN", turn.toString());
        outState.putInt("PLAYED", gamesPlayedCount);
        outState.putInt("WHITE", whiteWinCount);
        outState.putInt("BLACK", blackWinCount);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // game is restored in onCreate
        gamesPlayedCount = savedInstanceState.getInt("PLAYED");
        turn = savedInstanceState.getString("TURN").equals("BLACK") ? CellState.BLACK : CellState.WHITE;
        whiteWinCount = savedInstanceState.getInt("WHITE");
        blackWinCount = savedInstanceState.getInt("BLACK");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolBar();

        if (savedInstanceState != null) {
            startNewGame(savedInstanceState.getString("GAME"));
            turn = savedInstanceState.getString("TURN").equals("BLACK") ? CellState.BLACK : CellState.WHITE;
        } else {
            startNewGame(null);
            turn = CellState.WHITE;
        }

        setupRV();
    }

    private void startNewGame(String existingGame) {
        mGame = existingGame == null ? new OthelloModelTwoPlayer() :
                OthelloModelTwoPlayer.getModelFromJSONString(existingGame);
    }

    private void setupToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupRV() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 8));
        white = findViewById(R.id.tv_white);
        black = findViewById(R.id.tv_black);
        recyclerView.setAdapter(new OthelloAdapter(white, black));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            launchSettings();
            return true;
        } else if (id == R.id.action_new_game) {
            startNewGame(null);
            recyclerView.setAdapter(new OthelloAdapter(white, black));
            return true;
        } else if (id == R.id.action_about) {
            showAbout();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void showAbout() {
        Utils.showInfoDialog(this, "About", "created by Aaron and Bryan");
    }

    private void launchSettings() {
        // TODO - bring in Settings Activity Java and XML, and code to launch it here
    }
}