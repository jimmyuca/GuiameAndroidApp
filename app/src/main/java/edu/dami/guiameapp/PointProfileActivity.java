package edu.dami.guiameapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Locale;

import edu.dami.guiameapp.fragments.PointProfileFragment;
import edu.dami.guiameapp.helpers.events.PointProfileListener;
import edu.dami.guiameapp.models.PointModel;

public class PointProfileActivity extends AppCompatActivity implements PointProfileListener {

    public static final String ARG_POINT = "point";
    private static final String TAG = PointProfileActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_profile);
        setup();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_point_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.photo) {
            return launchCameraIfIsAvailable();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG, "onActivityResult fired!");
        if(requestCode == CAMERA_REQUEST_ID && resultCode == RESULT_OK && data != null) {
            sendResultToFragment(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
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

    private void loadPointFragment(PointModel point) {
        FragmentTransaction frgTran = getSupportFragmentManager().beginTransaction();
        frgTran.replace(R.id.point_ph, PointProfileFragment.newInstance(point));
        frgTran.commit();
    }

    private boolean launchCameraIfIsAvailable() {
        //TODO: mover a helper para reutilizar validacion entre pantallas
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            //TODO: presentar mensaje
            Log.d(TAG, "Camara no disponible");
            return false;
        }

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(cameraIntent.resolveActivity(getPackageManager()) == null) {
            Log.e(TAG, "Ocurrió un error en el Intent de Camara");
            return false;
        }

        startActivityForResult(cameraIntent, CAMERA_REQUEST_ID);
        return true;
    }

    private void sendResultToFragment(int requestCode, int resultCode, Intent data) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.point_ph);
        if(fragment == null) {
            Log.e(TAG, "Fragment null");
            return;
        }
        fragment.onActivityResult(requestCode, resultCode, data);
    }


}