package edu.dami.guiameapp.adapters;

import androidx.annotation.DrawableRes;

import edu.dami.guiameapp.R;
import edu.dami.guiameapp.data.IPointsSource;

public class PointViewHelper {

    @DrawableRes
    public static int getResIdByCategory(String category) {
        switch(category) {
            case IPointsSource.Categories.FOOD:
                return R.drawable.ic_baseline_fastfood_24;
            case IPointsSource.Categories.PARKING:
                return R.drawable.ic_baseline_directions_car_24;
            case IPointsSource.Categories.TECHNOLOGY:
                return R.drawable.ic_baseline_computer_24;
            case IPointsSource.Categories.BUILDING:
            default:
                return R.drawable.ic_baseline_business_24;
        }
    }

}
