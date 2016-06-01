package com.ensogo.movie.favorites;

import com.ensogo.movie.entities.Movie;

import java.util.List;

public interface IFavoritesInteractor
{
    void setFavorite(Movie movie);
    boolean isFavorite(String id);
    List<Movie> getFavorites();
    void unFavorite(String id);
}
