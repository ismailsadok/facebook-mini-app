package com.ismailsadok.facebookapp.Album;

import android.support.annotation.NonNull;


public class ModuleAlbums implements Comparable<ModuleAlbums> {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public ModuleAlbums setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ModuleAlbums setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public int compareTo(@NonNull ModuleAlbums o) {
        return this.name.compareTo(o.name);
    }
}
