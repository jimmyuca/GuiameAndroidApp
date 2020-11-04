package edu.dami.guiameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.dami.guiameapp.data.UserConfig;

public class OnboardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        setup();
    }

    private void setup() {
        setupActions();
    }

    private void setupActions() {
        Button btnStart = findViewById(R.id.btn_signup);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSignUp();
            }
        });
    }

    private void navigateToSignUp() {
        saveFirstStart();
        Intent intent = new Intent(this, SignUpActivity.class);
        //la proxima activity ahora será la primera en el back stack
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void saveFirstStart() {
        UserConfig userConfig = new UserConfig(getApplicationContext());
        userConfig.setIsFirstTime(false);
    }
}