package edu.dami.guiameapp.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PointModel {
    private String id;
    private String name;
    private String description;

    public PointModel(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static List<PointModel> build(int count) {
        ArrayList<PointModel> models = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            PointModel newModel = new PointModel(
                    String.valueOf(i),
                    String.format(Locale.getDefault(), "Punto %d", i),
                    String.format(Locale.getDefault(), "Descripción de punto %d", i)
                    );
            models.add(newModel);
        }
        return models;
    }
}
