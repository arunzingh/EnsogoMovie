package com.ensogo.movie.details;

import com.ensogo.movie.constants.Api;
import com.ensogo.movie.entities.Review;
import com.ensogo.movie.entities.Video;
import com.ensogo.movie.network.RequestGenerator;
import com.ensogo.movie.network.RequestHandler;
import com.squareup.okhttp.Request;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import rx.Observable;
import rx.functions.Func0;

public class MovieDetailsInteractor implements IMovieDetailsInteractor
{
    @Override
    public Observable<List<Video>> getTrailers(final String id)
    {
        return Observable.defer(new Func0<Observable<List<Video>>>()
        {
            @Override
            public Observable<List<Video>> call()
            {
                try
                {
                    return Observable.just(get(id));
                } catch (Exception e)
                {
                    return Observable.error(e);
                }
            }

            private List<Video> get(String id) throws IOException, JSONException
            {
                String url = String.format(Api.GET_TRAILERS, id);
                Request request = RequestGenerator.get(url);
                String body = RequestHandler.request(request);
                return MovieDetailsParser.parseTrailers(body);
            }
        });
    }

    @Override
    public Observable<List<Review>> getReviews(final String id)
    {
        return Observable.defer(new Func0<Observable<List<Review>>>()
        {
            @Override
            public Observable<List<Review>> call()
            {
                try
                {
                    return Observable.just(get(id));
                } catch (Exception e)
                {
                    return Observable.error(e);
                }
            }

            private List<Review> get(String id) throws IOException, JSONException
            {
                String url = String.format(Api.GET_REVIEWS, id);
                Request request = RequestGenerator.get(url);
                String body = RequestHandler.request(request);
                return MovieDetailsParser.parseReviews(body);
            }
        });
    }
}
