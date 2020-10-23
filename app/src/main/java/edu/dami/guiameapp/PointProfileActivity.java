package edu.dami.guiameapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import edu.dami.guiameapp.fragments.PointProfileFragment;
import edu.dami.guiameapp.fragments.PointsFragment;
import edu.dami.guiameapp.models.PointModel;

public class PointProfileActivity extends AppCompatActivity {

    public static final String ARG_POINT = "point";
    private static final String TAG = PointProfileActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_profile);
        setup();
    }

    private void setup() {
        Intent navIntent = getIntent();
        PointModel selectedPoint = navIntent.getParcelableExtra(ARG_POINT);
        if(selectedPoint == null) {
            Log.e(TAG, "Punto seleccionado es null");
            return;
        }

        loadPointFragment(selectedPoint);
    }

    private void loadPointFragment(PointModel point) {
        FragmentTransaction frgTran = getSupportFragmentManager().beginTransaction();
        frgTran.replace(R.id.point_ph, PointProfileFragment.newInstance(point));
        frgTran.commit();
    }
    
}