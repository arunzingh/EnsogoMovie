package com.ensogo.movie.details;

import com.ensogo.movie.entities.Movie;
import com.ensogo.movie.entities.Review;
import com.ensogo.movie.entities.Video;
import com.ensogo.movie.favorites.FavoritesInteractor;
import com.ensogo.movie.favorites.IFavoritesInteractor;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MovieDetailsPresenter implements IMovieDetailsPresenter
{
    private IMovieDetailsView mMovieDetailsView;
    private IMovieDetailsInteractor mMovieDetailsInteractor;
    private IFavoritesInteractor mFavoritesInteractor;

    public MovieDetailsPresenter(IMovieDetailsView movieDetailsView)
    {
        mMovieDetailsView = movieDetailsView;
        mMovieDetailsInteractor = new MovieDetailsInteractor();
        mFavoritesInteractor = new FavoritesInteractor();
    }

    @Override
    public void showDetails(Movie movie)
    {
        mMovieDetailsView.showDetails(movie);
    }

    @Override
    public Subscription showTrailers(Movie movie)
    {
        return mMovieDetailsInteractor.getTrailers(movie.getId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Video>>()
                {
                    @Override
                    public void onCompleted()
                    {

                    }

                    @Override
                    public void onError(Throwable e)
                    {

                    }

                    @Override
                    public void onNext(List<Video> videos)
                    {
                        mMovieDetailsView.showTrailers(videos);
                    }
                });
    }

    @Override
    public Subscription showReviews(Movie movie)
    {
        return mMovieDetailsInteractor.getReviews(movie.getId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Review>>()
                {
                    @Override
                    public void onCompleted()
                    {

                    }

                    @Override
                    public void onError(Throwable e)
                    {

                    }

                    @Override
                    public void onNext(List<Review> reviews)
                    {
                        mMovieDetailsView.showReviews(reviews);
                    }
                });
    }

    @Override
    public void showFavoriteButton(Movie movie)
    {
        boolean isFavorite = mFavoritesInteractor.isFavorite(movie.getId());
        if(isFavorite)
        {
            mMovieDetailsView.showFavorited();
        } else
        {
            mMovieDetailsView.showUnFavorited();
        }
    }

    @Override
    public void onFavoriteClick(Movie movie)
    {
        boolean isFavorite = mFavoritesInteractor.isFavorite(movie.getId());
        if(isFavorite)
        {
            mFavoritesInteractor.unFavorite(movie.getId());
            mMovieDetailsView.showUnFavorited();
        } else
        {
            mFavoritesInteractor.setFavorite(movie);
            mMovieDetailsView.showFavorited();
        }
    }
}
