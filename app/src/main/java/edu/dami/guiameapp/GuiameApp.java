package edu.dami.guiameapp;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

public class GuiameApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        setup();
    }

    private void setup() {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}
