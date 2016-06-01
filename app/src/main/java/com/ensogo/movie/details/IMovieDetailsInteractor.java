package com.ensogo.movie.details;

import com.ensogo.movie.entities.Review;
import com.ensogo.movie.entities.Video;

import java.util.List;

import rx.Observable;

public interface IMovieDetailsInteractor
{
    Observable<List<Video>> getTrailers(String id);
    Observable<List<Review>> getReviews(String id);
}
