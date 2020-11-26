package edu.dami.guiameapp.data.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.dami.guiameapp.data.IPointsSource;
import edu.dami.guiameapp.models.PointModel;

public class PointsDbSource implements IPointsSource {

    private static final String TAG = PointsDbSource.class.getName();
    private final Context mContext;

    public PointsDbSource(@NonNull Context context) {
        mContext = context;
    }

    @Override
    public List<PointModel> getAll(int count) {
        if(count < 0)
            throw new IllegalArgumentException("count");
        if(count > 0)
            throw new UnsupportedOperationException("Por ahora el método no soporta límites");

        //definimos los campos que consultaremos de la tabla en BD.
        String[] projection = {
                PointDbContract.Struct._ID,
                PointDbContract.Struct.COLUMN_NAME,
                PointDbContract.Struct.COLUMN_DESCRIPTION,
                PointDbContract.Struct.COLUMN_CATEGORY,
                PointDbContract.Struct.COLUMN_LAT,
                PointDbContract.Struct.COLUMN_LNG,
        };

        //Ordenar por nombre de punto ASC (a,b,c...)
        String sortOrder = PointDbContract.Struct.COLUMN_NAME + " ASC";

        //Crea o abre la conexion a BD y nos devuelve una instancia de ella.
        MainDbHelper mDbHelper = MainDbHelper.getInstance(mContext);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        //De forma simple, un cursor representa cada fila de los resultados generados tras la consulta.
        Cursor rowCursor = db.query(
                PointDbContract.Struct.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        List<PointModel> pointsFromDb = new ArrayList<>();
        //en un ciclo recorremos cada fila de resultados usando el metodo moveToNext
        //el cual nos mueve a la siguiente fila por cada iteracion realizada.
        while (rowCursor.moveToNext()) {
            PointModel point = toPointFromCursor(rowCursor);
            if(point != null) {
                pointsFromDb.add(point);
            }
        }

        rowCursor.close();
        return pointsFromDb;
    }

    @Nullable
    private PointModel toPointFromCursor(@NonNull Cursor cursor) {
        try {
            long id = cursor.getLong(
                    cursor.getColumnIndexOrThrow(PointDbContract.Struct._ID)
            );
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(PointDbContract.Struct.COLUMN_NAME)
            );
            String description = cursor.getString(
                    cursor.getColumnIndexOrThrow(PointDbContract.Struct.COLUMN_DESCRIPTION)
            );
            String category = cursor.getString(
                    cursor.getColumnIndexOrThrow(PointDbContract.Struct.COLUMN_CATEGORY)
            );
            String lat = cursor.getString(
                    cursor.getColumnIndexOrThrow(PointDbContract.Struct.COLUMN_LAT)
            );
            String lng = cursor.getString(
                    cursor.getColumnIndexOrThrow(PointDbContract.Struct.COLUMN_LNG)
            );

            return new PointModel(
                    String.valueOf(id),
                    name,
                    description,
                    category,
                    lat != null ? Double.parseDouble(lat) : 0f,
                    lng != null ? Double.parseDouble(lng) : 0f
            );
        } catch (IllegalArgumentException ex) {
            Log.e(TAG, String.format(
                    Locale.getDefault(),
                    "Ocurrió un error al obtener desde BD el punto. Mensaje: %s", ex.getMessage()
            ));
            return null;
        }
    }
}
