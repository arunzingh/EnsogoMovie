package com.ensogo.movie.favorites;

import com.ensogo.movie.entities.Movie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FavoritesInteractor implements IFavoritesInteractor
{
    private FavoritesStore mFavoritesStore;

    public FavoritesInteractor() {
        mFavoritesStore = new FavoritesStore();
    }

    @Override
    public void setFavorite(Movie movie)
    {
        mFavoritesStore.setFavorite(movie);
    }

    @Override
    public boolean isFavorite(String id)
    {
        return mFavoritesStore.isFavorite(id);
    }

    @Override
    public List<Movie> getFavorites()
    {
        try
        {
            return mFavoritesStore.getFavorites();
        } catch (IOException ignored)
        {
            return new ArrayList<>(0);
        }
    }

    @Override
    public void unFavorite(String id)
    {
        mFavoritesStore.unfavorite(id);
    }
}
