package edu.dami.guiameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String FULLNAME_KEY = "FULLNAME";
    public static final String EMAIL_KEY = "EMAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
    }

    private void setup() {
        Intent startIntent = getIntent();
        if(startIntent == null) {
            Toast.makeText(
                    this,
                    "Algo salió mal al obtener los datos :(",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        String fullname = startIntent.getStringExtra(FULLNAME_KEY);
        if(TextUtils.isEmpty(fullname)) {
            fullname = "Usuario";
        }
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.welcome_user_title, fullname));
        }

        String email = startIntent.getStringExtra(EMAIL_KEY);
        if(TextUtils.isEmpty(email)) {
            Toast.makeText(
                    this,
                    R.string.cannot_get_email,
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }
        Log.d("MainActivity", String.format("Valor extra: %s",
                startIntent.getDoubleExtra("NUMERO", 0.0)));
        TextView tvConfirmDesc = findViewById(R.id.tv_confirm_desc);
        tvConfirmDesc.setText(getString(R.string.confirm_desc, email));
    }
}