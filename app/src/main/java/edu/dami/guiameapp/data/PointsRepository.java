package edu.dami.guiameapp.data;

import java.util.List;

import edu.dami.guiameapp.models.PointModel;

public class PointsRepository {

    private final IPointsSource mSource;

    public PointsRepository() {
        mSource = new PointsMockSource();
    }

    public PointsRepository(IPointsSource source) {
        mSource = source;
    }

    public List<PointModel> getAll() {
        return mSource.getAll(50);
    }
}
