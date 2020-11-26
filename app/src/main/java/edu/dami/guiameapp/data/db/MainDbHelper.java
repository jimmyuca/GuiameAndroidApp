package edu.dami.guiameapp.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Point;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import edu.dami.guiameapp.models.PointModel;

public class MainDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "guiame.db";
    public static final int DB_VERSION = 1;
    private static final String TAG = MainDbHelper.class.getName();

    private static MainDbHelper mInstance;
    private final List<PointModel> mInitialPoints;

    //implementacion super basica (no thread safe) de Singleton para evitar que se creen multiples
    //instancias de esta clase, lo que originaria multiples conexiones a la BD que podrian no controlarse
    //adecuadamente.
    public static MainDbHelper getInstance(
            @NonNull Context context,
            @Nullable List<PointModel> initialPoints) {
        if(mInstance == null) {
            mInstance = new MainDbHelper(context, initialPoints);
        }
        return mInstance;
    }

    public static MainDbHelper getInstance(
            @NonNull Context context) {
        return getInstance(context, null);
    }

    private MainDbHelper(@NonNull Context context, @Nullable List<PointModel> initialPoints) {
        //SQLiteOpenHelper al ser una clase abstracta, requiere implementar uno de sus constructores
        //optamos por usar el que requiere un Contexto, el nombre de la BD y la versión actual de la BD.
        //No necesitamos definir un "factory" porque no necesitamos personalizar el Cursor al procesar los datos de la BD.
        //https://developer.android.com/reference/android/database/sqlite/SQLiteDatabase.CursorFactory
        //https://developer.android.com/reference/android/database/Cursor
        super(context, DB_NAME, null, DB_VERSION);
        mInitialPoints = initialPoints;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Al crearse la BD, generar las tablas requeridas.
        createTables(db);
        seed(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Evento que ocurre cuando se actualiza la versión de la BD porque normalmente ocurrió
        //un cambio en su estructura. El upgrade se confirma al incrementar nuestro atributo DB_VERSION
        //Esto está OK para efectos de DEMO o cuando guardás datos despreciables y volátiles (ej: una caché temporal)
        //Si los datos de la BD son importantes y necesitan mantenerse con la actualización de la BD
        //requerirás una posible estrategia de migración de datos.
        //Asi que nunca se te ocurra hacer esto! es como el chasquido de Thanos.
        deleteTables(db);
        //una vez barremos con las tablas de la BD, volver a crearlas.
        createTables(db);
        seed(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // si volvemos de una versión superior de la BD a una menor,
        // de igual forma dropear todo y volver a crear las tablas.
        deleteTables(db);
        createTables(db);
        seed(db);
    }

    private void createTables(SQLiteDatabase db) {
        db.execSQL(PointDbContract.Queries.CREATE);
    }

    private void deleteTables(SQLiteDatabase db) {
        db.execSQL(PointDbContract.Queries.DELETE);
    }

    private void seed(SQLiteDatabase db) {
        seed(db, mInitialPoints);
    }

    private void seed(SQLiteDatabase db, List<PointModel> points) {
        PointDbSeeder pointSeeder = new PointDbSeeder(db, points);
        final boolean isPointSeeded = pointSeeder.seed();
        if(!isPointSeeded) {
            Log.e(TAG, "No ha sido posible cargar los puntos iniciales desde BD");
        }
        //TODO: podria tronarse adrede la app con una excepcion o emitir un broadcast.
    }
}
