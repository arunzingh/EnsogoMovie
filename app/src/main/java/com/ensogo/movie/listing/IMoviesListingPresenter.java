package com.ensogo.movie.listing;

import rx.Subscription;

public interface IMoviesListingPresenter
{
    Subscription displayMovies();
    Subscription registerFavoriteChangeEvent();
}
