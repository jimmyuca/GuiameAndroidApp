package edu.dami.guiameapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.security.InvalidParameterException;

import edu.dami.guiameapp.data.UserConfig;
import edu.dami.guiameapp.models.UserModel;

public class SignUpActivity extends AppCompatActivity {

    TextInputLayout tilFullname, tilEmail;
    EditText etFullname, etEmail;

    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setup();
    }

    private void setup() {
        tilFullname = findViewById(R.id.til_fullname);
        tilEmail = findViewById(R.id.til_email);

        etFullname = tilFullname.getEditText();
        etEmail = tilEmail.getEditText();

        Button btnSignUp = findViewById(R.id.btn_signup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        //loadDefaultDataIfDebug();
    }


    private void signUp() {
        if(!validateFields()) {
            return;
        }
        userModel = new UserModel(etFullname.getText().toString(), etEmail.getText().toString());
        saveUser(userModel);
        navigateToMain(userModel);
    }

    private void saveUser(UserModel user) {
        UserConfig userConfig = new UserConfig(getApplicationContext());
        userConfig.setUser(user);
    }

    private void navigateToMain(UserModel user) {
        Intent intent = new Intent(this, MainActivity.class);
        //la proxima activity ahora será la primera en el back stack
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(MainActivity.FULLNAME_KEY, user.getFullname());
        intent.putExtra(MainActivity.EMAIL_KEY, user.getEmail());
        startActivity(intent);
    }

    private boolean validateFields() {
        if(etFullname.getText() == null || TextUtils.isEmpty(etFullname.getText().toString())) {
            tilFullname.setError(getString(R.string.fullname_error));
            return false;
        }
        if(etEmail.getText() == null || TextUtils.isEmpty(etEmail.getText().toString())) {
            tilEmail.setError(getString(R.string.email_error));
            return false;
        }

        showMessage("Todo bien, todo correcto, gracias!");
        return true;
    }

    private void showMessage(String message) {
        Toast.makeText(
                this,
                message,
                Toast.LENGTH_LONG
        ).show();
    }

    @SuppressLint("SetTextI18n")
    private void loadDefaultDataIfDebug() {
        if(!BuildConfig.DEBUG) return;
        etFullname.setText("Jimmy Saenz");
        etEmail.setText("jimmy.saenz@uca.edu.ni");
    }
}