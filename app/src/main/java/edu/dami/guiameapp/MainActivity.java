package edu.dami.guiameapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.dami.guiameapp.adapters.PointsAdapter;
import edu.dami.guiameapp.data.IPointsSource;
import edu.dami.guiameapp.data.PointsRepository;
import edu.dami.guiameapp.helpers.events.ItemTapListener;
import edu.dami.guiameapp.models.PointModel;

public class MainActivity extends AppCompatActivity implements ItemTapListener {

    private static final String TAG = MainActivity.class.getName();
    public static final String FULLNAME_KEY = "FULLNAME";
    public static final String EMAIL_KEY = "EMAIL";

    private PointsRepository mPointsRepository;
    private List<PointModel> mModelList;
    private PointsAdapter mPointsAdapter;

    private ViewGroup rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void setup() {
        mPointsRepository = new PointsRepository();
        mModelList = new ArrayList<>();

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

        rootView = findViewById(R.id.ly_root);
        setupPointListView();
    }

    private void setupPointListView() {
        RecyclerView rvPoints = findViewById(R.id.rv_points);
        mPointsAdapter = new PointsAdapter(mModelList, this);
        rvPoints.setAdapter(mPointsAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        rvPoints.setLayoutManager(layoutManager);
        rvPoints.setHasFixedSize(true);
    }

    private void loadData() {
        if(!mModelList.isEmpty()) {
            Log.d(TAG, "Ya existen valores en la lista");
            return;
        }
        if(mPointsRepository == null) {
            Log.e(TAG, "mPointsRepository no debería ser null");
            return;
        }
        mModelList = mPointsRepository.getAll();
        mPointsAdapter.updateList(mModelList);
    }

    @Override
    public void onItemTap(View view, int position) {
        showMessageWithSelectedItem(position);
    }

    private void showMessageWithSelectedItem(int position) {
        if(mModelList == null) {
            Log.e(TAG, "invalid mModelList");
            return;
        }
        if(position > mModelList.size()) {
            Log.e(TAG, "invalid position");
            return;
        }

        PointModel selectedItemModel = mModelList.get(position);
        Snackbar.make(rootView,
                String.format(Locale.getDefault(),
                        "Has seleccionado %s", selectedItemModel.getName()
                ),
                Snackbar.LENGTH_LONG
        ).show();
    }
}