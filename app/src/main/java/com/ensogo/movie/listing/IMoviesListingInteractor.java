package com.ensogo.movie.listing;

import com.ensogo.movie.entities.Movie;

import java.util.List;

import rx.Observable;

public interface IMoviesListingInteractor
{
    Observable<List<Movie>> fetchMovies();
}
