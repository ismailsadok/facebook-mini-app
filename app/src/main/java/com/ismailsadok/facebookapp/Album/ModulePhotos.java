package com.ismailsadok.facebookapp.Album;

public class ModulePhotos {

    private String id;
    private ModuleImages images;

    public String getId() {
        return id;
    }

    public ModulePhotos setId(String id) {
        this.id = id;
        return this;
    }

    public ModuleImages getImages() {
        return images;
    }

    public ModulePhotos setImages(ModuleImages images) {
        this.images = images;
        return this;
    }
}
