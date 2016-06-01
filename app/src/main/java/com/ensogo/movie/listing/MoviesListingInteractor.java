package com.ensogo.movie.listing;

import android.support.annotation.NonNull;

import com.ensogo.movie.constants.Api;
import com.ensogo.movie.entities.Movie;
import com.ensogo.movie.entities.SortType;
import com.ensogo.movie.favorites.FavoritesInteractor;
import com.ensogo.movie.favorites.IFavoritesInteractor;
import com.ensogo.movie.network.RequestGenerator;
import com.ensogo.movie.network.RequestHandler;
import com.ensogo.movie.sorting.SortingOptionStore;
import com.squareup.okhttp.Request;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import rx.Observable;
import rx.functions.Func0;

public class MoviesListingInteractor implements IMoviesListingInteractor
{
    private IFavoritesInteractor favoritesInteractor;

    public MoviesListingInteractor()
    {
        favoritesInteractor = new FavoritesInteractor();
    }

    @Override
    public Observable<List<Movie>> fetchMovies()
    {
        return Observable.defer(new Func0<Observable<List<Movie>>>()
        {
            @Override
            public Observable<List<Movie>> call()
            {
                try
                {
                    return Observable.just(get());
                } catch (Exception e)
                {
                    return Observable.error(e);
                }
            }

            private List<Movie> get() throws IOException, JSONException
            {
                SortingOptionStore sortingOptionStore = new SortingOptionStore();
                int selectedOption = sortingOptionStore.getSelectedOption();
                if (selectedOption == SortType.MOST_POPULAR.getValue())
                {
                    return fetch(Api.GET_POPULAR_MOVIES);
                } else if (selectedOption == SortType.HIGHEST_RATED.getValue())
                {
                    return fetch(Api.GET_HIGHEST_RATED_MOVIES);
                } else
                {
                    return favoritesInteractor.getFavorites();
                }
            }

            @NonNull
            private List<Movie> fetch(String url) throws IOException, JSONException
            {
                Request request = RequestGenerator.get(url);
                String response = RequestHandler.request(request);
                return MoviesListingParser.parse(response);
            }
        });
    }
}
