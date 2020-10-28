package edu.dami.guiameapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import edu.dami.guiameapp.R;
import edu.dami.guiameapp.models.PointModel;

import static edu.dami.guiameapp.helpers.events.PointProfileListener.CAMERA_REQUEST_ID;

/*
* Mas info sobre Fragments incluyendo codelab
* https://codelabs.developers.google.com/codelabs/advanced-android-training-fragment-communication?hl=en#6
* https://github.com/google-developer-training/android-advanced/tree/master/FragmentCommunicate
* */

public class PointProfileFragment extends Fragment {

    private static final String ARG_POINT = "point";
    private static final String TAG = PointProfileFragment.class.getName();

    private PointModel mPoint;

    private ImageView ivPhoto;

    public PointProfileFragment() {
        // Required empty public constructor
    }

    public static PointProfileFragment newInstance(PointModel point) {
        PointProfileFragment fragment = new PointProfileFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_POINT, point);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPoint = getArguments().getParcelable(ARG_POINT);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setup(view, mPoint);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult fired (from fragment)");
        if(requestCode == CAMERA_REQUEST_ID && resultCode == Activity.RESULT_OK && data != null) {
            showTakenPhoto(data.getExtras());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_point_profile, container, false);
    }

    private void setup(View view, PointModel point) {
        if(point == null) {
            Log.e(TAG, "point es null");
            return;
        }
        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvDesc = view.findViewById(R.id.tv_desc);

        tvName.setText(point.getName());
        tvDesc.setText(point.getDescription());

        ivPhoto = view.findViewById(R.id.iv_photo);
    }

    private void showTakenPhoto(@Nullable Bundle intentData) {
        if(intentData == null) {
            Log.e(TAG, "Bundle null");
            return;
        }
        Bitmap imageBitmap = (Bitmap) intentData.get("data");
        if(imageBitmap == null) {
            Log.e(TAG, "Foto null");
            return;
        }
        ivPhoto.setImageBitmap(imageBitmap);

        //Mas info: https://developer.android.com/training/camera/photobasics
    }

}