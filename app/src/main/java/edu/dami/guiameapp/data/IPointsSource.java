package edu.dami.guiameapp.data;

import java.util.List;

import edu.dami.guiameapp.models.PointModel;

public interface IPointsSource {
    List<PointModel> getAll(int count);
}
