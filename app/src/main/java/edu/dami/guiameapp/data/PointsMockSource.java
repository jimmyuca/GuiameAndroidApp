package edu.dami.guiameapp.data;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import edu.dami.guiameapp.models.PointModel;

public class PointsMockSource implements IPointsSource {

    public List<PointModel> getAll(int count) {
        ArrayList<PointModel> models = new ArrayList<>();
        models.add(new PointModel(
                genId(),
                "Laboratorios J",
                "Universidad Centroamericana de Nicaragua. Recinto principal",
                Categories.TECHNOLOGY
        ));
        models.add(new PointModel(
                genId(),
                "Laboratorios H",
                "Universidad Centroamericana de Nicaragua. Recinto principal",
                Categories.TECHNOLOGY
        ));
        models.add(new PointModel(
                genId(),
                "Laboratorios E",
                "Universidad Centroamericana de Nicaragua. Recinto principal",
                "tecnología"
        ));
        models.add(new PointModel(
                genId(),
                "Laboratorios U",
                "Universidad Centroamericana de Nicaragua. Recinto principal",
                Categories.TECHNOLOGY
        ));
        models.add(new PointModel(
                genId(),
                "Laboratorios F",
                "Universidad Centroamericana de Nicaragua. Recinto principal",
                Categories.TECHNOLOGY
        ));

        models.add(new PointModel(
                genId(),
                "Facultad de CTyA",
                "Universidad Centroamericana de Nicaragua. Recinto principal",
                Categories.BUILDING
        ));
        models.add(new PointModel(
                genId(),
                "Facultad de Derecho",
                "Universidad Centroamericana de Nicaragua. Recinto principal",
                Categories.BUILDING
        ));
        models.add(new PointModel(
                genId(),
                "Facultad de Comunicación",
                "Universidad Centroamericana de Nicaragua. Recinto principal",
                Categories.BUILDING
        ));

        models.add(new PointModel(
                genId(),
                "Radio Universitaria",
                "Universidad Centroamericana de Nicaragua. Recinto principal. Costado Oeste, cerca de la Capilla",
                Categories.BUILDING
        ));
        models.add(new PointModel(
                genId(),
                "Biblioteca José Coronel Urtecho",
                "Universidad Centroamericana de Nicaragua. Recinto principal. Costado Oeste, frente al cafetín Central",
                Categories.BUILDING
        ));

        models.add(new PointModel(
                genId(),
                "Kiosko de Eskimo",
                "Universidad Centroamericana de Nicaragua. Recinto principal. Food Park Este",
                Categories.FOOD
        ));
        models.add(new PointModel(
                genId(),
                "Pupusas Salvadoreñas",
                "Universidad Centroamericana de Nicaragua. Recinto principal. Food Park Este",
                Categories.FOOD
        ));
        models.add(new PointModel(
                genId(),
                "King's Dog",
                "Universidad Centroamericana de Nicaragua. Recinto principal. Food Park Este",
                Categories.FOOD
        ));
        models.add(new PointModel(
                genId(),
                "Fritangas doña Mary",
                "Universidad Centroamericana de Nicaragua. Recinto principal. Food Park Este",
                Categories.FOOD
        ));
        models.add(new PointModel(
                genId(),
                "Queen's Dog",
                "Universidad Centroamericana de Nicaragua. Recinto principal. Food Park Este",
                Categories.FOOD
        ));

        models.add(new PointModel(
                genId(),
                "Parqueo Principal",
                "Universidad Centroamericana de Nicaragua. Recinto principal",
                Categories.PARKING
        ));
        models.add(new PointModel(
                genId(),
                "Parqueo Este",
                "Universidad Centroamericana de Nicaragua. Recinto principal",
                Categories.PARKING
        ));
        models.add(new PointModel(
                genId(),
                "Parqueo Oeste",
                "Universidad Centroamericana de Nicaragua. Recinto principal",
                Categories.PARKING
        ));

        Collections.shuffle(models);
        return models;
    }

    private String genId() {
        return java.util.UUID.randomUUID().toString();
    }

}
