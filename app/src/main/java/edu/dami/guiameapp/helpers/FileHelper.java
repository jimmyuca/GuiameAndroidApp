package edu.dami.guiameapp.helpers;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FileHelper {

    //ref: https://bezkoder.com/java-android-read-json-file-assets-gson/
    public static String getJsonFromAssets(Context context, String fileName) {
        try {
            String jsonString;
            InputStream is = context.getAssets().open(fileName);

            //obtener cantidad de bytes estimados de archivo
            int size = is.available();
            //crear un arreglo de bytes donde temporalmente alojaremos el contenido del archivo
            byte[] buffer = new byte[size];
            //transferir el contenido al arreglo creado
            is.read(buffer);
            //cerrar el stream para optimizar recursos y evitar efectos secundarios.
            is.close();

            //StandardCharsets.UTF_8 está disponible de a4.4 en adelante, crear fallback (else)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                jsonString = new String(buffer, StandardCharsets.UTF_8);
            } else {
                jsonString = new String(buffer, "UTF-8");
            }

            return jsonString;
        } catch (IOException e) {
            Log.e("FileHelper",
                    "Ocurrió un error al procesar el archivo. " + e.getMessage()
            );
            return null;
        }
    }
}
