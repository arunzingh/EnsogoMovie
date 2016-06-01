package com.ensogo.movie.details;

import com.ensogo.movie.entities.Movie;
import com.ensogo.movie.entities.Review;
import com.ensogo.movie.entities.Video;

import java.util.List;

public interface IMovieDetailsView
{
    void showDetails(Movie movie);
    void showTrailers(List<Video> trailers);
    void showReviews(List<Review> reviews);
    void showFavorited();
    void showUnFavorited();
}
