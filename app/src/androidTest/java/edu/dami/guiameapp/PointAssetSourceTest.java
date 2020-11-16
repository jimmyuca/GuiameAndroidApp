package edu.dami.guiameapp;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import edu.dami.guiameapp.data.IPointsSource;
import edu.dami.guiameapp.data.PointsAssetSource;
import edu.dami.guiameapp.data.PointsMockSource;
import edu.dami.guiameapp.models.PointModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class PointAssetSourceTest {

    @Test
    public void dado_obtenerPuntos_conUnContador_retornarListaFiltrada() {
        final int expectedCount = 5;
        IPointsSource pointsSource = new PointsAssetSource(getAppContext());
        List<PointModel> pointsList = pointsSource.getAll(expectedCount);
        assertNotNull(pointsList);
        assertEquals(expectedCount, pointsList.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void dado_obtenerPuntos_conUnContadorNegativo_arrojarExcepcion() {
        IPointsSource pointsSource = new PointsAssetSource(getAppContext());
        List<PointModel> pointsList = pointsSource.getAll(-1);
        assertNotNull(pointsList);
    }

    @Test
    public void dado_obtenerPuntos_conUnContadorCero_retornarListaCompleta() {
        IPointsSource pointsSource = new PointsAssetSource(getAppContext());
        List<PointModel> pointsList = pointsSource.getAll(0);
        assertNotNull(pointsList);
        assertFalse(pointsList.isEmpty());
    }

    private Context getAppContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }
}
