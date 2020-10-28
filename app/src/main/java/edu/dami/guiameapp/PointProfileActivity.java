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
        setupBar();
        Intent navIntent = getIntent();
        PointModel selectedPoint = navIntent.getParcelableExtra(ARG_POINT);
        if(selectedPoint == null) {
            Log.e(TAG, "Punto seleccionado es null");
            return;
        }

        loadPointFragment(selectedPoint);
    }

    private void setupBar() {
        if(getSupportActionBar() == null) {
            Log.d(TAG, "ActionBar null");
            return;
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /*
    Esta es una mala práctica en caso que se implemente navegación con hermanos o
    entre apps (incluyendo push notifications). Por el momento se establece así para
    simplicidad y no recurrir a Toolbar porque no se ha abordado en detalle aún.
    https://developer.android.com/training/appbar/up-action
    TODO: migrar a Toolbar y cambiar patrón Up+Back a Back
    TODO: (2) migrar a Jetpack Navigation
    * */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadPointFragment(PointModel point) {
        FragmentTransaction frgTran = getSupportFragmentManager().beginTransaction();
        frgTran.replace(R.id.point_ph, PointProfileFragment.newInstance(point));
        frgTran.commit();
    }
    
}