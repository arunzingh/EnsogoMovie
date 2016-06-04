package com.ensogo.movie.listing;


import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ensogo.movie.R;
import com.ensogo.movie.entities.Movie;
import com.ensogo.movie.sorting.SortingDialogFragment;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

public class MoviesListingFragment extends Fragment implements IMoviesListingView
{
    private RecyclerView.Adapter mAdapter;
    private List<Movie> mMovies;
    private IMoviesListingPresenter mMoviesPresenter;
    private Subscription mMoviesSubscription;
    private Subscription mFavoritesSubscription;
    private Callback mCallback;

    public MoviesListingFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        mCallback = (Callback) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mMovies = new ArrayList<>(20);
        mMoviesPresenter = new MoviesListingPresenter(this);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        initLayoutReferences(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        if (mMovies.isEmpty()) {
            mMoviesSubscription = mMoviesPresenter.displayMovies();
            mFavoritesSubscription = mMoviesPresenter.registerFavoriteChangeEvent();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_sort:
                displaySortingOptions();
        }

        return super.onOptionsItemSelected(item);
    }

    private void displaySortingOptions()
    {
        DialogFragment sortingDialogFragment = SortingDialogFragment.newInstance(mMoviesPresenter);
        sortingDialogFragment.show(getFragmentManager(), "Select Quantity");
    }

    private void initLayoutReferences(View rootView)
    {
        RecyclerView moviesListing = (RecyclerView) rootView.findViewById(R.id.movies_listing);
        moviesListing.setHasFixedSize(true);

        int columns;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            columns = 2;
        } else
        {
            columns = getResources().getInteger(R.integer.no_of_columns);
        }
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), columns);

        moviesListing.setLayoutManager(layoutManager);
        mAdapter = new MoviesListingAdapter(mMovies, this);
        moviesListing.setAdapter(mAdapter);
    }

    @Override
    public void showMovies(List<Movie> movies)
    {
        mMovies.clear();
        mMovies.addAll(movies);
        mAdapter.notifyDataSetChanged();
        mCallback.onMoviesLoaded(movies.get(0));
    }

    @Override
    public void loadingStarted()
    {
        Toast.makeText(getContext(), R.string.loading_movies, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadingFailed(String errorMessage)
    {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMovieClicked(Movie movie)
    {
        mCallback.onMovieClicked(movie);
    }

    @Override
    public void onDestroyView()
    {
        if (mMoviesSubscription != null && !mMoviesSubscription.isUnsubscribed())
        {
            mMoviesSubscription.unsubscribe();
        }
        if (mFavoritesSubscription != null && !mFavoritesSubscription.isUnsubscribed()) {
            mFavoritesSubscription.unsubscribe();
        }
        super.onDestroyView();
    }

    @Override
    public void onDetach()
    {
        mCallback = null;
        super.onDetach();
    }

    public interface Callback
    {
        void onMoviesLoaded(Movie movie);
        void onMovieClicked(Movie movie);
    }
}
