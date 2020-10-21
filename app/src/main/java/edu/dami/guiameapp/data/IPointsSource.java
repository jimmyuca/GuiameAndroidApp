package edu.dami.guiameapp.data;

import java.util.List;

import edu.dami.guiameapp.models.PointModel;

public interface IPointsSource {
    public interface Categories {
        public static final String TECHNOLOGY = "tecnologia";
        public static final String BUILDING = "edificio";
        public static final String FOOD = "comida";
        public static final String PARKING = "parqueo";
    }

    List<PointModel> getAll(int count);
}
