package edu.dami.guiameapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.dami.guiameapp.R;
import edu.dami.guiameapp.adapters.viewholders.PointViewHolder;
import edu.dami.guiameapp.helpers.events.ItemTapListener;
import edu.dami.guiameapp.models.PointModel;

public class PointsAdapter extends RecyclerView.Adapter<PointViewHolder> {

    @NonNull
    private final List<PointModel> mModelList;
    @Nullable
    private final ItemTapListener mTapListener;

    //pasamos parametros al adapter
    public PointsAdapter(@NonNull List<PointModel> modelList, @Nullable ItemTapListener tapListener) {
        mModelList = modelList;
        mTapListener = tapListener;
    }

    @NonNull
    @Override
    //inflamos cada item con su respectiva vista.
    public PointViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_point, parent, false);
        PointViewHolder viewHolder = new PointViewHolder(itemView, mTapListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PointViewHolder holder, int position) {
        PointModel currentModel = mModelList.get(position);
        holder.tvName.setText(currentModel.getName());
        holder.tvDesc.setText(currentModel.getDescription());
    }

    @Override
    public int getItemCount() {
        return mModelList.size();
    }

}
