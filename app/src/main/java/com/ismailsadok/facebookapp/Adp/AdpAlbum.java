package com.ismailsadok.facebookapp.Adp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ismailsadok.facebookapp.Album.AlbumImg;
import com.ismailsadok.facebookapp.Album.ModuleAlbums;
import com.ismailsadok.facebookapp.R;

import java.util.List;



public class AdpAlbum extends RecyclerView.Adapter<AdpAlbum.ViewHolder> {

    private Context context;
    private List<ModuleAlbums> albumsList;

    public AdpAlbum(Context context, List<ModuleAlbums> moduleAlbums){
        this.albumsList = moduleAlbums;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AdpAlbum.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_album, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
         ModuleAlbums albums = albumsList.get(position);
         holder.albumeName.setText(albums.getName());
    }

    @Override
    public int getItemCount() {
        return albumsList.size();
    }

    public void setList(List<ModuleAlbums> list){
        this.albumsList = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView albumeName;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            albumeName = (TextView) itemView.findViewById(R.id.albumeName);
        }

        @Override
        public void onClick(View v) {
            ModuleAlbums albums = albumsList.get(getAdapterPosition());
            Intent intent = new Intent(context, AlbumImg.class);
            intent.putExtra("ALBUM_ID", albums.getId());
            intent.putExtra("ALBUM_NAME", albums.getName());
            context.startActivity(intent);


        }
    }
}
