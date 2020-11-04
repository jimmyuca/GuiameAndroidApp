package edu.dami.guiameapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import edu.dami.guiameapp.data.UserConfig;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launchFirstActivity();
    }

    private void launchFirstActivity() {
        UserConfig userConfig = new UserConfig(getApplicationContext());
        Intent intent;
        if(userConfig.isFirstTime()) {
            intent = new Intent(getBaseContext(), OnboardingActivity.class);
        } else {
            if(userConfig.userExists()) {
                intent = new Intent(getBaseContext(), MainActivity.class);
            } else {
                intent = new Intent(getBaseContext(), SignUpActivity.class);
            }
        }
        startActivity(intent);
        //finalizar esta actividad
        finish();
    }
}
