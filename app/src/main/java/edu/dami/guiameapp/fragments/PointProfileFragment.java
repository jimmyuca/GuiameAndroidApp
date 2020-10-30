package edu.dami.guiameapp.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

import edu.dami.guiameapp.R;
import edu.dami.guiameapp.models.PointModel;

import static edu.dami.guiameapp.helpers.events.PointProfileListener.CAMERA_REQUEST_ID;

/*
* Mas info sobre Fragments incluyendo codelab
* https://codelabs.developers.google.com/codelabs/advanced-android-training-fragment-communication?hl=en#6
* https://github.com/google-developer-training/android-advanced/tree/master/FragmentCommunicate
* */

public class PointProfileFragment extends Fragment {

    private static final String ARG_POINT = "point";
    private static final String TAG = PointProfileFragment.class.getName();
    private static final int LOC_PERMISSION_REQUEST_CODE = 300;
    //TODO: si se usa FINE en lugar de COARSE en el manifest, cambiar aqui tambien.
    private static final String LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION;

    private PointModel mPoint;
    private FusedLocationProviderClient mLocationClient;
    protected Location mLastLocation;

    private ViewGroup lyRoot;
    private ImageView ivPhoto;

    public PointProfileFragment() {
        // Required empty public constructor
    }

    public static PointProfileFragment newInstance(PointModel point) {
        PointProfileFragment fragment = new PointProfileFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_POINT, point);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPoint = getArguments().getParcelable(ARG_POINT);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setup(view, mPoint);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult fired (from fragment)");
        if(requestCode == CAMERA_REQUEST_ID && resultCode == Activity.RESULT_OK && data != null) {
            showTakenPhoto(data.getExtras());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_point_profile, container, false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if(requestCode == LOC_PERMISSION_REQUEST_CODE) {
            onUserInteractedWithLocationPermissionSystemDialog(permissions, grantResults);
        }
    }

    private void setup(View view, PointModel point) {
        if(point == null) {
            Log.e(TAG, "point es null");
            return;
        }
        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvDesc = view.findViewById(R.id.tv_desc);

        tvName.setText(point.getName());
        tvDesc.setText(point.getDescription());

        lyRoot = view.findViewById(R.id.ly_root);
        ivPhoto = view.findViewById(R.id.iv_photo);

        Button btnOpenMap = view.findViewById(R.id.btn_open_map);
        btnOpenMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPointInMap();
            }
        });

        setupLocationClient(getContext());
    }

    private void setupLocationClient(Context context) {
        mLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    private void showTakenPhoto(@Nullable Bundle intentData) {
        if(intentData == null) {
            Log.e(TAG, "Bundle null");
            return;
        }
        Bitmap imageBitmap = (Bitmap) intentData.get("data");
        if(imageBitmap == null) {
            Log.e(TAG, "Foto null");
            return;
        }
        ivPhoto.setImageBitmap(imageBitmap);
        //Mas info: https://developer.android.com/training/camera/photobasics
    }

    private void viewPointInMap() {
        tryProcessLocation();
    }

    private void tryProcessLocation() {
        if(hasLocationPermission(getContext())) {
            getLocation(getActivity());
        } else {
            requestLocationPermission(getActivity(), getContext());
        }
    }

    private boolean hasLocationPermission(Context context) {
        int permissionState = ContextCompat.checkSelfPermission(
                context,
                LOCATION_PERMISSION
        );
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission(@Nullable final Activity activity, @Nullable Context context) {
        if(activity == null || context == null) {
            Log.e(TAG, "Por alguna razon, el activity o  el context es null");
            //TODO: presentar algun mensaje en pantalla indicando el error
            return;
        }
        if(shouldRequestLocationPermission(activity)) {
            new MaterialAlertDialogBuilder(context)
                    .setTitle(R.string.app_name)
                    .setMessage(R.string.location_request)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            launchLocationPermissionSystemDialog(activity);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
            .show();
        } else {
            launchLocationPermissionSystemDialog(activity);
        }
    }

    private void launchLocationPermissionSystemDialog(Activity activity) {
        requestPermissions(
                new String[]{LOCATION_PERMISSION},
                LOC_PERMISSION_REQUEST_CODE
        );
    }

    private boolean shouldRequestLocationPermission(Activity activity) {
        return ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                LOCATION_PERMISSION
        );
    }

    private void onUserInteractedWithLocationPermissionSystemDialog(@NonNull String[] permissions,
                                                                    @NonNull int[] grantResults) {
        if (grantResults.length <= 0) {
            Log.e(TAG, "Ocurrió una acción que canceló el diálogo de permisos del sistema");
            return;
        }
        if(grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            showMessageOnSnack("Lo siento, no podemos cargar el punto en el mapa porque no has aceptado el permiso :(");
        }

        getLocation(getActivity());
    }

    @SuppressWarnings("MissingPermission")
    private void getLocation(@Nullable Activity activity) {
        if(activity == null) {
            Log.e(TAG, "Por alguna razon, el activity es null");
            return;
        }
        mLocationClient.getLastLocation().addOnCompleteListener(activity, new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                processRetrievedLocationTask(task);
            }
        });
    }

    private void processRetrievedLocationTask(@NonNull Task<Location> locationTask) {
        if(!locationTask.isSuccessful() || locationTask.getResult() == null) {
            Log.e(TAG, "Error al obtener coordenada", locationTask.getException());
            showMessageOnSnack("Disculpa, algo salió mal al obtener la ubicación");
            return;
        }

        mLastLocation = locationTask.getResult();
        Log.d(TAG, String.format(Locale.getDefault(), "Ubicación obtenida exitosamente. Lat: %f, Lng: %f",
                mLastLocation.getLatitude(),
                mLastLocation.getLongitude()
        ));

        launchMapDirectionWithPointCoords(mLastLocation, mPoint);
    }

    private void launchMapDirectionWithPointCoords(@Nullable Location userLocation,
                                                   @Nullable PointModel point) {
        if(userLocation == null || point == null) {
            Log.e(TAG, "Parametros invalidos");
            return;
        }
        //TODO: locale podria afectar el formato de los valores y usar coma en lugar de punto provocando error
        String mapsUrl = String.format(
                Locale.ROOT,
                "http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f",
                userLocation.getLatitude(), userLocation.getLongitude(),
                point.getLat(), point.getLng()
        );
        Log.d(TAG, "URL de maps a cargar: " + mapsUrl);
        try {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(mapsUrl));
            startActivity(intent);
        } catch (Exception ex) {
            Log.e(TAG, "Algo salio mal al cargar las coordenadas en el mapa");
        }
    }

    private void showMessageOnSnack(String message) {
        Snackbar.make(lyRoot, message, BaseTransientBottomBar.LENGTH_LONG).show();
    }
}