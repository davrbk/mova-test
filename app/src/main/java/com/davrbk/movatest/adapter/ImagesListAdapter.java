package com.davrbk.movatest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.davrbk.movatest.R;
import com.davrbk.movatest.model.Image;
import com.davrbk.movatest.presenter.MovaContract;
import com.squareup.picasso.Picasso;

public class ImagesListAdapter extends RecyclerView.Adapter<ImagesListAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private MovaContract.Presenter presenter;
    private Picasso picasso;

    public ImagesListAdapter(Context context, MovaContract.Presenter presenter) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.presenter = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.view_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Image image = presenter.onItem(position);
        Picasso.get().load(image.getDisplaySizes().get(0).getUri()).into(holder.thumb);
        holder.title.setText(image.getTitle());
    }

    @Override
    public int getItemCount() {
        return presenter.count();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView thumb;
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            thumb = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.name);
        }
    }
}
