package edu.dami.guiameapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import edu.dami.guiameapp.data.db.MainDbHelper;
import edu.dami.guiameapp.data.db.PointsDbSource;
import edu.dami.guiameapp.models.PointModel;

public class PointsRepository {

    private final IPointsSource mSource;
    private final IPointsSource mSeedSource;
    private final Context mContext;

    public PointsRepository(Context context) {
        this(context, new PointsDbSource(context), new PointsAssetSource(context));
    }

    public PointsRepository(Context context, IPointsSource source, IPointsSource seedSource) {
        mContext = context;
        mSource = source;
        mSeedSource = seedSource;
    }

    public List<PointModel> getAll() {
        return mSource.getAll(0);
    }

    public boolean seedSourceWithInitialData() {
        final UserConfig userConfig = new UserConfig(mContext);
        //si ya se han cargado los datos, no volver a hacerlo.
        //TODO: podria reemplazarse por una query COUNT de puntos a la BD
        if(userConfig.isPointsLoadedFromDb()) {
            return true;
        }

        List<PointModel> pointsToSeed = mSeedSource.getAll(0);
        //TODO: lo ideal es que este procesamiento se realice de forma asincrona para no congelar la UI y por
        //tanto no afectar la UX.
        MainDbHelper dbHelper = MainDbHelper.getInstance(mContext, pointsToSeed);
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        //se devuelve si esta abierta la conexion solo para efectos basicos de comprobacion
        //que la BD ha sido creada y abierto una conexion.
        final boolean isDbOpen = db.isOpen();
        //la cerramos para evitar fugas de memoria y efectos secundarios en el acceso a la BD.
        db.close();
        // por fines practicos, asumimos que al abrir la conexion a BD,
        // se cargaron los datos por defecto en ella.
        userConfig.setIsPointsLoadedFromDb(isDbOpen);

        return isDbOpen;
    }
}
