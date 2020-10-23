package edu.dami.guiameapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.dami.guiameapp.R;
import edu.dami.guiameapp.models.PointModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PointProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PointProfileFragment extends Fragment {

    private static final String ARG_POINT = "point";
    private static final String TAG = PointProfileFragment.class.getName();

    private PointModel mPoint;

    public PointProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param point Modelo.
     * @return A new instance of fragment PointProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
    }

}