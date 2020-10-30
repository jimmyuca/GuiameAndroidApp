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
import androidx.core.content.ContextCompat;
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
    /*
     * PASO 1. Declarar las propiedades a utilizar
     * - LOC_PERMISSION_REQUEST_CODE: Codigo para identificar evento (onRequestPermissionsResult)
     *   de obtener ubicacion a traves de Play services
     * - LOCATION_PERMISSION: Corresponde al permiso que estamos usando en el Manifest
     * - mLastLocation: Objeto que representa ultima coordenada obtenida a traves
     *   de Play Services: mLocationClient.getLastLocation()
    */
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

    /*
    PASO 5. Este evento se dispara cuando el usuario ha terminado de interactuar con
    el dialogo del sistema para solicitar permisos.
    * */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        /*Si es el codigo que definimos en launchLocationPermissionSystemDialog, avancemos y tratemos
        * de obtener la ubicacion */
        Log.d(TAG, "onRequestPermissionsResult fired");
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

        /*
        PASO 2. Configurar referencia de boton y su evento
        */
        Button btnOpenMap = view.findViewById(R.id.btn_open_map);
        btnOpenMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPointInMap();
            }
        });

        /*
        PASO 3. Inicializar cliente de ubicacion provisto por Play Services
        */
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

    /*
    PASO 4. Verificar si tenemos permiso para obtener ubicacion del dispositivo
    En caso que no, solicitarla
    */
    private void tryProcessLocation() {
        /*
        PASO 4.1. La primera vez que cargamos la app lo mas seguro es que no tengamos permisos,
        asi que el flujo irá hacia el else.
        */
        if(hasLocationPermission(getContext())) {
            getLocation(getActivity());
        } else {
            requestLocationPermission(getActivity(), getContext());
        }
    }
    /*
    PASO 4.1. Determina si tenemos permiso de ubicacion
    */
    private boolean hasLocationPermission(Context context) {
        int permissionState = ContextCompat.checkSelfPermission(
                context,
                LOCATION_PERMISSION
        );
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    /*
    PASO 4.2 Solicitar permiso de Ubicación.
    */
    private void requestLocationPermission(@Nullable final Activity activity, @Nullable Context context) {
        if(activity == null || context == null) {
            Log.e(TAG, "Por alguna razon, el activity o  el context es null");
            //TODO: presentar algun mensaje en pantalla indicando el error
            return;
        }
        /*
        PASO 4.2.1 El sistema decide si es requerido presentar una UI
        para que le expliquemos al usuario porque estamos solicitando el permiso.
        Si tenemos que hacerlo, presentamos un Dialogo explicando.
        */
        if(shouldRequestLocationPermission(activity)) {
            new MaterialAlertDialogBuilder(context)
                    .setTitle(R.string.app_name)
                    .setMessage(R.string.location_request)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        /*
                        PASO 4.2.1-A Si el usuario acepta, lanzamos el dialogo del sistema para aceptar el permiso
                        */
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
            final int locPermission = ContextCompat.checkSelfPermission(context, LOCATION_PERMISSION);
            /*
            PASO 4.2.1-B Si el sistema determina que no es requerido presentar una UI para explicarle al usuario,
            probablemente sea por 2 razones:
            1. Es la 1ra vez que solicitamos este permiso y el sistema no lo determina como peligroso asi que "pasa cholo"
            2. Nos bateó y bloqueó la app denegando varias veces el permiso.
            cargamos de una vez el dialogo del sistema para aceptar el permiso.
            */
            launchLocationPermissionSystemDialog(activity);
        }
    }

    /*
    * Lanzar dialogo del sistema para aceptar (o rechazar) el permiso de ubicacion
    * Nota que pasamos como param LOC_PERMISSION_REQUEST_CODE porque al presentar este dialogo
    * saldremos temporalmente de nuestra app, cuando el usuario vuelva a nuestra app se disparara
    * el evento onRequestPermissionsResult y alli usaremos LOC_PERMISSION_REQUEST_CODE
    * */
    private void launchLocationPermissionSystemDialog(Activity activity) {
        requestPermissions(
                new String[]{LOCATION_PERMISSION},
                LOC_PERMISSION_REQUEST_CODE
        );
    }

    /*
    Determina si deberia presentarse una UI para explicarle el usuario porque requerimos el permiso de ubicacion
    * */
    private boolean shouldRequestLocationPermission(Activity activity) {
        return ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                LOCATION_PERMISSION
        );
    }

    /*
    PASO 6. Procesamos la respuesta del usuario al interactuar con el dialogo del sistema de permisos.
    Podria haber aceptado o denegado el permiso.
    * */
    private void onUserInteractedWithLocationPermissionSystemDialog(@NonNull String[] permissions,
                                                                    @NonNull int[] grantResults) {
        if (grantResults.length <= 0) {
            Log.e(TAG, "Ocurrió una acción que canceló el diálogo de permisos del sistema");
            return;
        }
        if(grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            showMessageOnSnack("Lo siento, no podemos cargar el punto en el mapa porque no has aceptado el permiso :(");
            return;
        }

        getLocation(getActivity());
    }

    /*
    * PASO 7. Por fin! podemos empezar a solicitar la ubicacion/coordenadas directamente
    * */
    @SuppressWarnings("MissingPermission")
    private void getLocation(@Nullable Activity activity) {
        if(activity == null) {
            Log.e(TAG, "Por alguna razon, el activity es null");
            return;
        }
        /*
        A traves del cliente de ubicacion nos suscribimos para obtener la ultima ubicacion
        obtenida del dispositivo en el sistema.
        Puede que el procesamiento de la coordenada sea exitoso o fallido.
        * */
        mLocationClient.getLastLocation().addOnCompleteListener(activity, new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                processRetrievedLocationTask(task);
            }
        });
    }

    /*
    PASO 8. Procesamos el resultado de la ubicacion obtenida desde el cliente de ubicacion
    * */
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

    /*
    * PASO 9. Lanzamos la app de google maps con una ruta precargada tomando como
    * partida la ubicacion obtenida y como meta la ubicacion del punto.
    * */
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