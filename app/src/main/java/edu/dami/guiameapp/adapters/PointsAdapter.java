package edu.dami.guiameapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.dami.guiameapp.R;
import edu.dami.guiameapp.models.PointModel;

public class PointsAdapter extends RecyclerView.Adapter<PointsAdapter.ViewHolder> {

    List<PointModel> modelList;

    //pasamos parametros al adapter
    public PointsAdapter(List<PointModel> modelList) {
        this.modelList = modelList;
    }

    @NonNull
    @Override
    //inflamos cada item con su respectiva vista.
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_point, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PointModel currentModel = modelList.get(position);
        holder.tvName.setText(currentModel.getName());
        holder.tvDesc.setText(currentModel.getDescription());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName, tvDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvDesc = itemView.findViewById(R.id.tv_desc);
        }
    }
}
