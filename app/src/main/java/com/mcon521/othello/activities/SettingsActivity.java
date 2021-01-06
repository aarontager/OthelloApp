package com.mintedtech.othello.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.mcon521.othello.R;
import com.mcon521.othello.lib.Utils;

public class SettingsActivity extends AppCompatActivity
{
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.settings_activity);
        attachFragment ();
        setupActionBar ();
    }

    private void attachFragment ()
    {
        getSupportFragmentManager ()
                .beginTransaction ()
                .replace (R.id.settings, new SettingsFragment ())
                .commit ();
    }

    private void setupActionBar ()
    {
        ActionBar actionBar = getSupportActionBar ();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled (true);
        }
    }

    @Override public boolean onOptionsItemSelected (@NonNull MenuItem item)
    {
        if (item.getItemId () == android.R.id.home) {
            onBackPressed ();
            return true;
        }
        else {
            return super.onOptionsItemSelected (item);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat
    {
        @Override
        public void onCreatePreferences (Bundle savedInstanceState, String rootKey)
        {
            setPreferencesFromResource (R.xml.root_preferences, rootKey);

            setNightModePreferenceListener();
        }

        private void setNightModePreferenceListener() {
            Preference nightModePreference = findPreference(getString(R.string.night_mode_key));
            if (nightModePreference != null)
                nightModePreference.setOnPreferenceChangeListener((preference, newValue) -> {
                    Boolean newBooleanValue = (Boolean) newValue;
                    Utils.setNightModeOnOrOff(newBooleanValue);
                    return true;
                });
        }


    }
}