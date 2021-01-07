package com.mcon521.othello.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.mcon521.othello.R;
import com.mcon521.othello.classes.CellState;
import com.mcon521.othello.classes.OthelloAdapter;
import com.mcon521.othello.classes.OthelloModel;
import com.mcon521.othello.classes.OthelloModelTwoPlayer;
import com.mcon521.othello.lib.Utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView tvWhite, tvBlack, tvTurn;
    private String gameKeyString = "GAME",
            turnKeyString = "TURN",
            whiteWinString = "WHITE",
            blackWinString = "BLACK",
            gameCountString = "PLAYED";

    public static OthelloModel mGame;
    public static CellState turn;
    public static int gamesPlayedCount, blackWinCount, whiteWinCount;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(gameKeyString, mGame.getJSONStringFromThis());
        outState.putString(turnKeyString, turn.toString());
        outState.putInt(gameCountString, gamesPlayedCount);
        outState.putInt(whiteWinString, whiteWinCount);
        outState.putInt(blackWinString, blackWinCount);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // game is restored in onCreate
        gamesPlayedCount = savedInstanceState.getInt(gameCountString);
        turn = savedInstanceState.getString(turnKeyString).equals(blackWinString) ? CellState.BLACK : CellState.WHITE;
        whiteWinCount = savedInstanceState.getInt(whiteWinString);
        blackWinCount = savedInstanceState.getInt(blackWinString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolBar();

        if (savedInstanceState != null) {
            startNewGame(savedInstanceState.getString(gameKeyString));
            turn = savedInstanceState.getString(turnKeyString).equals(blackWinString) ? CellState.BLACK : CellState.WHITE;
        } else {
            startNewGame(null);
            turn = getDefaultSharedPreferences(this).
                    getBoolean(getString(R.string.white_first_key), false) ? CellState.WHITE : CellState.BLACK;
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
        tvWhite = findViewById(R.id.tv_white);
        tvBlack = findViewById(R.id.tv_black);
        tvTurn = findViewById(R.id.tv_turn);
        recyclerView.setAdapter(new OthelloAdapter(tvWhite, tvBlack, tvTurn));
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
            recyclerView.setAdapter(new OthelloAdapter(tvWhite, tvBlack, tvTurn));
            return true;
        } else if (id == R.id.action_about) {
            showAbout();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void showAbout() {
        Utils.showInfoDialog(this, "About", "Created by Aaron and Bryan");
    }

    private void launchSettings() {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivityForResult(intent, 1);
    }


    @Override
    protected void onStop() {
        super.onStop();
        saveOrDeleteGameInSharedPrefs();
    }

    private void saveOrDeleteGameInSharedPrefs() {
        SharedPreferences defaultSharedPreferences = getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = defaultSharedPreferences.edit();

        // Save current game or remove any prior game to/from default shared preferences
        if (defaultSharedPreferences.getBoolean(getString(R.string.auto_save_key), true)) {
            editor.putString(gameKeyString, mGame.getJSONStringFromThis());
            editor.putString(turnKeyString, turn.toString());
        } else
            editor.remove(gameKeyString);

        editor.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        restoreFromPreferences_SavedGameIfAutoSaveWasSetOn();
    }

    private void restoreFromPreferences_SavedGameIfAutoSaveWasSetOn() {
        SharedPreferences defaultSharedPreferences = getDefaultSharedPreferences(this);
        if (defaultSharedPreferences.getBoolean(getString(R.string.auto_save_key), true)) {
            String gameString = defaultSharedPreferences.getString(gameKeyString, null);
            if (gameString != null) {
                mGame = OthelloModelTwoPlayer.getModelFromJSONString(gameString);
                turn = defaultSharedPreferences.getString(turnKeyString, "").equals(blackWinString) ? CellState.BLACK : CellState.WHITE;
            }
        }
    }
}