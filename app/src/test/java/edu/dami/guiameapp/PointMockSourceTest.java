package edu.dami.guiameapp;
import org.junit.Test;

import java.util.List;

import edu.dami.guiameapp.data.IPointsSource;
import edu.dami.guiameapp.data.PointsMockSource;
import edu.dami.guiameapp.models.PointModel;

import static org.junit.Assert.*;

public class PointMockSourceTest {

    @Test
    public void dado_obtenerPuntos_conUnContador_retornarListaFiltrada() {
        final int expectedCount = 5;
        IPointsSource pointsSource = new PointsMockSource();
        List<PointModel> pointsList = pointsSource.getAll(expectedCount);
        assertNotNull(pointsList);
        assertEquals(expectedCount, pointsList.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void dado_obtenerPuntos_conUnContadorNegativo_arrojarExcepcion() {
        IPointsSource pointsSource = new PointsMockSource();
        List<PointModel> pointsList = pointsSource.getAll(-1);
        assertNotNull(pointsList);
    }

    @Test
    public void dado_obtenerPuntos_conUnContadorCero_retornarListaCompleta() {
        IPointsSource pointsSource = new PointsMockSource();
        List<PointModel> pointsList = pointsSource.getAll(0);
        assertNotNull(pointsList);
        assertFalse(pointsList.isEmpty());
    }
}
