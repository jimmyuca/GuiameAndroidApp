package edu.dami.guiameapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
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

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.dami.guiameapp.adapters.PointsAdapter;
import edu.dami.guiameapp.data.IPointsSource;
import edu.dami.guiameapp.data.PointsRepository;
import edu.dami.guiameapp.data.UserConfig;
import edu.dami.guiameapp.fragments.PointProfileFragment;
import edu.dami.guiameapp.fragments.PointsFragment;
import edu.dami.guiameapp.helpers.events.ItemTapListener;
import edu.dami.guiameapp.models.PointModel;
import edu.dami.guiameapp.models.UserModel;

public class MainActivity extends AppCompatActivity implements ItemTapListener {

    private static final String TAG = MainActivity.class.getName();
    public static final String FULLNAME_KEY = "FULLNAME";
    public static final String EMAIL_KEY = "EMAIL";

    private PointsRepository mPointsRepository;
    private List<PointModel> mModelList;
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
        rootView = findViewById(R.id.ly_root);
        setupViewFromData();
    }

    private void setupViewFromData() {
        Intent startIntent = getIntent();
        if(startIntent == null) {
            Toast.makeText(
                    this,
                    "Algo salió mal al obtener los datos :(",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        UserModel userModel = getUserModelFromSources(startIntent.getExtras());

        if(getSupportActionBar() != null) {
            String fullname = TextUtils.isEmpty(userModel.getFullname()) ?
                    "Usuario" : userModel.getFullname();
            getSupportActionBar()
                    .setTitle(getString(R.string.welcome_user_title, fullname));
        }

        if(TextUtils.isEmpty(userModel.getEmail())) {
            Toast.makeText(
                    this,
                    R.string.cannot_get_email,
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    @NonNull
    private UserModel getUserModelFromSources(Bundle extras) {
        UserConfig userConfig = new UserConfig(getApplicationContext());
        final UserModel user = userConfig.getUser();
        if(user != null) {
            return user;
        }
        if(extras == null) {
            throw new InvalidParameterException("Extras");
        }
        return new UserModel(extras.getString(FULLNAME_KEY), extras.getString(EMAIL_KEY));
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

        loadPointsFragment(new ArrayList<PointModel>(mModelList));
    }

    private void loadPointsFragment(ArrayList<PointModel> points) {
        FragmentTransaction frgTran = getSupportFragmentManager().beginTransaction();
        frgTran.replace(R.id.points_ph, PointsFragment.newInstance(points));
        frgTran.commit();
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
        navigateToProfile(selectedItemModel);
    }

    private void navigateToProfile(PointModel point) {
        if(isTwoPaneLayout()) {
            loadDetailsFragment(point);
        } else {
            launchProfileActivity(point);
        }
    }

    private void launchProfileActivity(PointModel point) {
        Intent intent = new Intent(this, PointProfileActivity.class);
        intent.putExtra(PointProfileActivity.ARG_POINT, point);
        startActivity(intent);
    }

    private void loadDetailsFragment(PointModel point) {
        FragmentTransaction frgTran = getSupportFragmentManager().beginTransaction();
        frgTran.replace(R.id.point_profile_ph, PointProfileFragment.newInstance(point));
        frgTran.commit();
    }

    private void showMessageWithPoint(PointModel selectedItemModel) {
        Snackbar.make(rootView,
                String.format(Locale.getDefault(),
                        "Has seleccionado %s", selectedItemModel.getName()
                ),
                Snackbar.LENGTH_LONG
        ).show();
    }

    private boolean isTwoPaneLayout() {
        return  findViewById(R.id.point_profile_ph) != null;
    }
}