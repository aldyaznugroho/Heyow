package com.skripsigg.heyow.models.test;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aldyaz on 11/20/2016.
 */

public class GenreResponse {
    @SerializedName("genres")
    @Expose
    private List<Genre> genres = new ArrayList<Genre>();

    /**
     *
     * @return
     * The genres
     */
    public List<Genre> getGenres() {
        return genres;
    }

    /**
     *
     * @param genres
     * The genres
     */
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
