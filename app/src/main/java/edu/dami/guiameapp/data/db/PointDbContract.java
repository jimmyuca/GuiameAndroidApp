package edu.dami.guiameapp.data.db;

import android.provider.BaseColumns;

public class PointDbContract {

    //declaramos el constructor como privado para evitar que esta clase sea instanciada
    //ya que su objetivo es simplemente la definición de metadatos de BD.
    private PointDbContract() {

    }

    //implementar BaseColumns permite implementar facilmente un campo por defecto que funciona
    //como llave primaria (_ID)
    public static class Struct implements BaseColumns {
        public static final String TABLE_NAME = "points";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        //TODO: podría reemplazarse con un ID que funcione como llave foránea, lo mantendremos así por practicidad.
        public static final String COLUMN_CATEGORY = "category_name";
        public static final String COLUMN_LAT = "lat";
        public static final String COLUMN_LNG = "lng";
    }

    public static class Queries {
        //Tipos de datos: https://www.sqlite.org/datatype3.html
        public static final String CREATE =
                "CREATE TABLE " + Struct.TABLE_NAME +
                        " (" + Struct._ID + " INTEGER PRIMARY KEY, " +
                        Struct.COLUMN_NAME + " TEXT, " +
                        Struct.COLUMN_DESCRIPTION + " TEXT, " +
                        Struct.COLUMN_CATEGORY + " TEXT, " +
                        Struct.COLUMN_LAT + " TEXT, " +
                        Struct.COLUMN_LNG + " TEXT" +
                        " )";

        public static final String DELETE =
                "DROP TABLE IF EXISTS " + Struct.TABLE_NAME;
    }
}
