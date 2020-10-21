package edu.dami.guiameapp.data;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.dami.guiameapp.models.PointModel;

public class PointsMockSource implements IPointsSource {

    public List<PointModel> getAll(int count) {
        Faker faker = new Faker(new Locale("es"));

        ArrayList<PointModel> models = new ArrayList<>();

        /*
        * https://github.com/DiUS/java-faker
        * https://java-faker.herokuapp.com/
        * */
        for (int i = 0; i < count; i++) {
            PointModel newModel = new PointModel(
                    faker.idNumber().valid(),
                    i % 2 == 0 ? faker.address().streetName() : faker.company().name(),
                    faker.address().fullAddress()
            );
            models.add(newModel);
        }
        return models;
    }

}
