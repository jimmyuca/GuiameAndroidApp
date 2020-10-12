package edu.dami.guiameapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.dami.guiameapp.adapters.PointsAdapter;
import edu.dami.guiameapp.models.PointModel;

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

        setupPointListView();
    }

    private void setupPointListView() {
        RecyclerView rvPoints = findViewById(R.id.rv_points);
        List<PointModel> modelList = PointModel.build(30);
        PointsAdapter adapter = new PointsAdapter(modelList);
        rvPoints.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        rvPoints.setLayoutManager(layoutManager);
        rvPoints.setHasFixedSize(true);
    }
}