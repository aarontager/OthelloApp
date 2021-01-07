package com.mcon521.othello.activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mcon521.othello.R;

import java.util.Locale;

import static com.mcon521.othello.activities.MainActivity.*;

public class StatsActivity extends AppCompatActivity {
    private TextView tvGamesPlayed,
            tvWhiteWins, tvWhitePercent,
            tvBlackWins, tvBlackPercent;

    final String FORMAT_STRING = "%2.1f%%", N_A = "N/A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        setupToolbar();
        setupFAB();
        setupViews();
        getData();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupFAB() {
        FloatingActionButton fab = findViewById(R.id.stats_back_button);
        fab.setOnClickListener(view -> onBackPressed());
    }
    private void setupViews() {
        tvGamesPlayed = findViewById(R.id.tv_games_played);
        tvWhiteWins = findViewById(R.id.tv_wins_white);
        tvWhitePercent = findViewById(R.id.tv_percent_white);
        tvBlackWins = findViewById(R.id.tv_wins_black);
        tvBlackPercent = findViewById(R.id.tv_percent_black);
    }

    private void getData() {
        Intent i = getIntent();
        int gamesPlayed = i.getIntExtra(gameCountString, 0),
                whiteWins = i.getIntExtra(whiteWinString, 0),
                blackWins = i.getIntExtra(blackWinString, 0);

        String whitePercent = gamesPlayed  == 0 ? N_A :
                String.format(Locale.US, FORMAT_STRING, (whiteWins/(double)gamesPlayed)*100);
        String blackPercent = gamesPlayed  == 0 ? N_A :
                String.format(Locale.US, FORMAT_STRING, (blackWins/(double)gamesPlayed)*100);

        tvGamesPlayed.setText(String.valueOf(gamesPlayed));
        tvWhiteWins.setText(String.valueOf(whiteWins));
        tvWhitePercent.setText(String.valueOf(whitePercent));
        tvBlackWins.setText(String.valueOf(blackWins));
        tvBlackPercent.setText(String.valueOf(blackPercent));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }
}