package edu.dami.guiameapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.dami.guiameapp.R;
import edu.dami.guiameapp.adapters.PointsAdapter;
import edu.dami.guiameapp.helpers.events.ItemTapListener;
import edu.dami.guiameapp.models.PointModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PointsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PointsFragment extends Fragment {

    private static final String ARG_LIST = "points_list";

    private PointsAdapter pointsAdapter;
    private ArrayList<PointModel> mPointsList;
    private ItemTapListener itemTapListener;

    public PointsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param points Lista de puntos.
     * @return A new instance of fragment PointsFragment.
     */
    public static PointsFragment newInstance(ArrayList<PointModel> points) {
        PointsFragment fragment = new PointsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_LIST, points);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPointsList = getArguments().getParcelableArrayList(ARG_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_points, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setup(view);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(!(context instanceof ItemTapListener)) {
            throw new ClassCastException("Actividad padre no implementa ItemTapListener");
        }

        itemTapListener = (ItemTapListener)context;
    }

    private void setup(@NonNull View view) {
        setupPointListView(view);
    }

    private void setupPointListView(View view) {
        RecyclerView rvPoints = view.findViewById(R.id.rv_points);
        pointsAdapter = new PointsAdapter(mPointsList, itemTapListener);
        rvPoints.setAdapter(pointsAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvPoints.setLayoutManager(layoutManager);
        rvPoints.setHasFixedSize(true);
    }

}