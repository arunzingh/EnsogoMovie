package com.ensogo.movie.listing;

import com.ensogo.movie.entities.Movie;
import com.ensogo.movie.entities.SortType;
import com.ensogo.movie.favorites.FavoriteChangeEvent;
import com.ensogo.movie.favorites.FavoritesStore;
import com.ensogo.movie.sorting.SortingOptionStore;

import java.util.List;

import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class MoviesListingPresenter implements IMoviesListingPresenter
{
    private IMoviesListingView mMoviesView;
    private IMoviesListingInteractor mMoviesInteractor;
    private SortingOptionStore mSortingOptionStore;


    public MoviesListingPresenter(IMoviesListingView view)
    {
        mMoviesView = view;
        mMoviesInteractor = new MoviesListingInteractor();
        mSortingOptionStore = new SortingOptionStore();
    }

    @Override
    public Subscription displayMovies()
    {
        return mMoviesInteractor.fetchMovies().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0()
                {
                    @Override
                    public void call()
                    {
                        mMoviesView.loadingStarted();
                    }
                })
                .subscribe(new Subscriber<List<Movie>>()
                {
                    @Override
                    public void onCompleted()
                    {

                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        mMoviesView.loadingFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Movie> movies)
                    {
                        mMoviesView.showMovies(movies);
                    }
                });
    }

    public Subscription registerFavoriteChangeEvent() {
        return FavoritesStore.getFavoriteChangeEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FavoriteChangeEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(FavoriteChangeEvent favoriteChangeEvent) {
                        // Favorites have changed, hence update UI for favorite case
                        if (mSortingOptionStore.getSelectedOption() == SortType.FAVORITES.getValue()) {
                            displayMovies();
                        }
                    }
                });
    }
}
