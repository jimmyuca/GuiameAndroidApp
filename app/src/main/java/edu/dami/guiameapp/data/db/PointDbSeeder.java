package edu.dami.guiameapp.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Locale;

import edu.dami.guiameapp.models.PointModel;

public class PointDbSeeder {

    private static final String TAG = PointDbSeeder.class.getName();
    private final List<PointModel> mPoints;
    private final SQLiteDatabase mDb;

    public PointDbSeeder(@NonNull SQLiteDatabase db, @NonNull List<PointModel> points) {
        mDb = db;
        mPoints = points;
    }

    public boolean seed() {
        try {
            for (PointModel point :
                    mPoints) {
                ContentValues valuesToInsert = buildRowContentFromModel(point);
                long newId = mDb.insert(PointDbContract.Struct.TABLE_NAME, null, valuesToInsert);
                
                //Porque si, decido que en caso de error llorar un poco en silencio y no tronar el flujo.
                if(newId == -1) {
                    Log.e(TAG, String.format(
                            Locale.getDefault(),
                            "No se pudo insertar el registro para el punto %s",
                            point.getName()
                    ));
                }
            }
            return true;
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
            return false;
        }
    }

    private ContentValues buildRowContentFromModel(PointModel point) {
        ContentValues values = new ContentValues();
        values.put(PointDbContract.Struct.COLUMN_NAME, point.getName());
        values.put(PointDbContract.Struct.COLUMN_DESCRIPTION, point.getDescription());
        values.put(PointDbContract.Struct.COLUMN_CATEGORY, point.getCategory());
        values.put(PointDbContract.Struct.COLUMN_LAT, String.valueOf(point.getLat()));
        values.put(PointDbContract.Struct.COLUMN_LNG, String.valueOf(point.getLng()));
        return values;
    }

}
