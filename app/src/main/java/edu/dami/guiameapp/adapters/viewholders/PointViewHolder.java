package edu.dami.guiameapp.adapters.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import edu.dami.guiameapp.R;
import edu.dami.guiameapp.helpers.events.ItemTapListener;

public class PointViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tvName, tvDesc;
    public ImageView ivCategory;
    @Nullable
    private final ItemTapListener mTapListener;

    public PointViewHolder(@NonNull View itemView, @Nullable ItemTapListener tapListener) {
        super(itemView);

        mTapListener = tapListener;
        itemView.setOnClickListener(this);

        tvName = itemView.findViewById(R.id.tv_name);
        tvDesc = itemView.findViewById(R.id.tv_desc);
        ivCategory = itemView.findViewById(R.id.iv_category);
    }

    @Override
    public void onClick(View view) {
        if(mTapListener == null) return;
        mTapListener.onItemTap(view, getAdapterPosition());
    }
}
