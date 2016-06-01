package com.ensogo.movie.favorites;

import com.ensogo.movie.entities.Movie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FavoritesInteractor implements IFavoritesInteractor
{
    @Override
    public void setFavorite(Movie movie)
    {
        FavoritesStore favoritesStore = new FavoritesStore();
        favoritesStore.setFavorite(movie);
    }

    @Override
    public boolean isFavorite(String id)
    {
        FavoritesStore favoritesStore = new FavoritesStore();
        return favoritesStore.isFavorite(id);
    }

    @Override
    public List<Movie> getFavorites()
    {
        try
        {
            FavoritesStore favoritesStore = new FavoritesStore();
            return favoritesStore.getFavorites();
        } catch (IOException ignored)
        {
            return new ArrayList<>(0);
        }
    }

    @Override
    public void unFavorite(String id)
    {
        FavoritesStore favoritesStore = new FavoritesStore();
        favoritesStore.unfavorite(id);
    }
}
