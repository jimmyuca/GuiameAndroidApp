package edu.dami.guiameapp.data;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.security.InvalidParameterException;
import java.util.List;

import edu.dami.guiameapp.helpers.FileHelper;
import edu.dami.guiameapp.models.PointModel;

public class PointsAssetSource implements IPointsSource {
    private final Gson parser;
    private final Context mContext;
    private static final String POINTS_FILE_NAME = "points.json";

    public PointsAssetSource(@NonNull Context context) {
        mContext = context;
        parser = new Gson();
    }

    @Override
    public List<PointModel> getAll(int count) {
        String json = FileHelper.getJsonFromAssets(mContext, POINTS_FILE_NAME);
        ListResult listResult = parser.fromJson(json, ListResult.class);
        if(listResult == null) return null;
        return filterByCount(listResult.list, count);
    }

    private List<PointModel> filterByCount(@NonNull List<PointModel> originalList, int count) {
        if(count < 0) throw new InvalidParameterException("Parametro count inválido");
        if(count == 0) return originalList;
        if(count >= originalList.size()) return originalList;
        return originalList.subList(0, count);
    }

    static class ListResult {
        @SerializedName("data")
        List<PointModel> list;

        public ListResult(List<PointModel> list) {
            this.list = list;
        }
    }
}
