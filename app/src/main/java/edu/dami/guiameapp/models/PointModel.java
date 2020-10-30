package edu.dami.guiameapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PointModel implements Parcelable {
    private String id;
    private String name;
    private String description;
    private String category;
    private double lat;
    private double lng;

    public PointModel(String id, String name, String description, String category, double lat, double lng) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.lat = lat;
        this.lng = lng;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    /*
    IMPLEMENTACIONES PARA PARCELABLE
    https://developer.android.com/reference/android/os/Parcelable
    "Parcelable crushes Serializable in terms of speed..."
    Generado con http://www.parcelabler.com/
    Alternativas:
    https://github.com/johncarl81/parceler
    http://parceler.org/
    En Kotlin:
    https://kotlinlang.org/docs/reference/compiler-plugins.html#parcelable-implementations-generator
    */

    protected PointModel(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        category = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(category);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
    }

    public static final Parcelable.Creator<PointModel> CREATOR = new Parcelable.Creator<PointModel>() {
        @Override
        public PointModel createFromParcel(Parcel in) {
            return new PointModel(in);
        }

        @Override
        public PointModel[] newArray(int size) {
            return new PointModel[size];
        }
    };
}
