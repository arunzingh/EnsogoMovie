package com.ensogo.movie.details;

import com.ensogo.movie.entities.Movie;

import rx.Subscription;

public interface IMovieDetailsPresenter
{
    void showDetails(Movie movie);
    Subscription showTrailers(Movie movie);
    Subscription showReviews(Movie movie);
    void showFavoriteButton(Movie movie);
    void onFavoriteClick(Movie movie);
}
