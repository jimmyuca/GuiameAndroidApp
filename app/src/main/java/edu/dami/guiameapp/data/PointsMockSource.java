package edu.dami.guiameapp.data;

import androidx.annotation.NonNull;

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
                Categories.TECHNOLOGY,
                12.126439,
                -86.270075
        ));
        models.add(new PointModel(
                genId(),
                "Laboratorios H",
                "Universidad Centroamericana de Nicaragua. Recinto principal",
                Categories.TECHNOLOGY,
                12.126440,
                -86.270075
        ));
        models.add(new PointModel(
                genId(),
                "Laboratorios E",
                "Universidad Centroamericana de Nicaragua. Recinto principal",
                "tecnología",
                12.126441,
                -86.270075
        ));
        models.add(new PointModel(
                genId(),
                "Laboratorios U",
                "Universidad Centroamericana de Nicaragua. Recinto principal",
                Categories.TECHNOLOGY,
                12.126445,
                -86.270075
        ));
        models.add(new PointModel(
                genId(),
                "Laboratorios F",
                "Universidad Centroamericana de Nicaragua. Recinto principal",
                Categories.TECHNOLOGY,
                12.126450,
                -86.270075
        ));

        models.add(new PointModel(
                genId(),
                "Facultad de CTyA",
                "Universidad Centroamericana de Nicaragua. Recinto principal",
                Categories.BUILDING,
                12.126455,
                -86.270075
        ));
        models.add(new PointModel(
                genId(),
                "Facultad de Derecho",
                "Universidad Centroamericana de Nicaragua. Recinto principal",
                Categories.BUILDING,
                12.126460,
                -86.270075
        ));
        models.add(new PointModel(
                genId(),
                "Facultad de Comunicación",
                "Universidad Centroamericana de Nicaragua. Recinto principal",
                Categories.BUILDING,
                12.126465,
                -86.270075
        ));

        models.add(new PointModel(
                genId(),
                "Radio Universitaria",
                "Universidad Centroamericana de Nicaragua. Recinto principal. Costado Oeste, cerca de la Capilla",
                Categories.BUILDING,
                12.126466,
                -86.270075
        ));
        models.add(new PointModel(
                genId(),
                "Biblioteca José Coronel Urtecho",
                "Universidad Centroamericana de Nicaragua. Recinto principal. Costado Oeste, frente al cafetín Central",
                Categories.BUILDING,
                12.126470,
                -86.270075
        ));

        models.add(new PointModel(
                genId(),
                "Kiosko de Eskimo",
                "Universidad Centroamericana de Nicaragua. Recinto principal. Food Park Este",
                Categories.FOOD,
                12.126475,
                -86.270075
        ));
        models.add(new PointModel(
                genId(),
                "Pupusas Salvadoreñas",
                "Universidad Centroamericana de Nicaragua. Recinto principal. Food Park Este",
                Categories.FOOD,
                12.126480,
                -86.270075
        ));
        models.add(new PointModel(
                genId(),
                "King's Dog",
                "Universidad Centroamericana de Nicaragua. Recinto principal. Food Park Este",
                Categories.FOOD,
                12.126485,
                -86.270075
        ));
        models.add(new PointModel(
                genId(),
                "Fritangas doña Mary",
                "Metrocentro Managua. Food Park Este",
                Categories.FOOD,
                12.1297637,
                -86.2634925
        ));
        models.add(new PointModel(
                genId(),
                "Queen's Dog",
                "Universidad Centroamericana de Nicaragua. Recinto principal. Food Park Este",
                Categories.FOOD,
                12.126487,
                -86.270075
        ));

        models.add(new PointModel(
                genId(),
                "Parqueo Principal",
                "Universidad Centroamericana de Nicaragua. Recinto principal",
                Categories.PARKING,
                12.126488,
                -86.270075
        ));
        models.add(new PointModel(
                genId(),
                "Parqueo Este",
                "Universidad Centroamericana de Nicaragua. Recinto principal",
                Categories.PARKING,
                12.126489,
                -86.270075
        ));
        models.add(new PointModel(
                genId(),
                "Parqueo Oeste",
                "Universidad Centroamericana de Nicaragua. Recinto principal",
                Categories.PARKING,
                12.126490,
                -86.270075
        ));

        Collections.shuffle(models);
        return filterByCount(models, count);
    }

    private List<PointModel> filterByCount(@NonNull List<PointModel> originalList, int count) {
        if(count < 0) throw new IllegalArgumentException("Parametro count inválido");
        if(count == 0) return originalList;
        if(count >= originalList.size()) return originalList;
        return originalList.subList(0, count);
    }

    private String genId() {
        return java.util.UUID.randomUUID().toString();
    }

}
