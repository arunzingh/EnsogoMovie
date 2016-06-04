package com.ensogo.movie.sorting;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ensogo.movie.R;
import com.ensogo.movie.listing.IMoviesListingPresenter;
import com.ensogo.movie.listing.MoviesListingPresenter;

public class SortingDialogFragment extends DialogFragment implements ISortingDialogView, RadioGroup.OnCheckedChangeListener
{
    private RadioGroup mSortingOptionsGroup;
    private static IMoviesListingPresenter mMoviesListingPresenter;
    private ISortingDialogPresenter mSortingDialogPresenter;

    public static SortingDialogFragment newInstance(IMoviesListingPresenter moviesListingPresenter)
    {
        mMoviesListingPresenter = moviesListingPresenter;
        return new SortingDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.sorting_options, null);
        initViews(dialogView);

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(dialogView);
        dialog.setTitle(R.string.sort_by);
        dialog.show();
        return dialog;
    }

    private void initViews(View dialogView)
    {
        mSortingOptionsGroup = (RadioGroup) dialogView.findViewById(R.id.sorting_group);
        mSortingDialogPresenter = new SortingDialogPresenter(this);
        mSortingDialogPresenter.setLastSavedOption();
        mSortingOptionsGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void setPopularChecked()
    {
        RadioButton popular = (RadioButton) mSortingOptionsGroup.findViewById(R.id.most_popular);
        popular.setChecked(true);
    }

    @Override
    public void setHighestRatedChecked()
    {
        RadioButton highestRated = (RadioButton) mSortingOptionsGroup.findViewById(R.id.highest_rated);
        highestRated.setChecked(true);
    }

    @Override
    public void setFavoritesChecked()
    {
        RadioButton favorites = (RadioButton) mSortingOptionsGroup.findViewById(R.id.favorites);
        favorites.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId)
    {
        switch (checkedId)
        {
            case R.id.most_popular:
                mSortingDialogPresenter.onPopularMoviesSelected();
                mMoviesListingPresenter.displayMovies();
                break;

            case R.id.highest_rated:
                mSortingDialogPresenter.onHighestRatedMoviesSelected();
                mMoviesListingPresenter.displayMovies();
                break;

            case R.id.favorites:
                mSortingDialogPresenter.onFavoritesSelected();
                mMoviesListingPresenter.displayMovies();
                break;
        }
    }

    @Override
    public void dismissDialog()
    {
        dismiss();
    }

}
