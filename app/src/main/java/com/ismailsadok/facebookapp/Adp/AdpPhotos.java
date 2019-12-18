package com.ismailsadok.facebookapp.Adp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ismailsadok.facebookapp.Album.ModulePhotos;
import com.ismailsadok.facebookapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;



public class AdpPhotos extends RecyclerView.Adapter<AdpPhotos.ViewHolder> {

    private List<ModulePhotos> photosList;
    private Context context;

    public AdpPhotos(Context c, List<ModulePhotos> photoses){
        this.photosList = photoses;
        this.context = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AdpPhotos.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_photo, parent, false));

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ModulePhotos photos = photosList.get(position);

        Picasso.with(context).load(photos.getImages().getSource()).into(holder.photoView);

    }

    @Override
    public int getItemCount() {
        return photosList.size();
    }

    public void setList(List<ModulePhotos> photos){
        this.photosList = photos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView photoView;
        View overLay;


        public ViewHolder(View itemView) {
            super(itemView);

            photoView = (ImageView) itemView.findViewById(R.id.photoView);
            overLay = (View) itemView.findViewById(R.id.overLay);


        }
    }
}
