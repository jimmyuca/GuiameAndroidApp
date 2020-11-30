package edu.dami.guiameapp;

import android.content.Context;

import androidx.annotation.DrawableRes;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import edu.dami.guiameapp.adapters.PointViewHelper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class PointViewHelperTest {

    @Test
    public void dado_obtenerRecursoPorCategoria_conUnNombreValido_retornarIdRecurso() {
        @DrawableRes int resourceId = PointViewHelper.getResIdByCategory("bannerapp1_c", getAppContext());
        assertEquals(resourceId, R.drawable.bannerapp1_c);
    }

    private Context getAppContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }
}
