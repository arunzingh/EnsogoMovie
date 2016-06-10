package com.ensogo.movie.details;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ensogo.movie.R;
import com.ensogo.movie.constants.Constants;
import com.ensogo.movie.entities.Movie;

public class MovieDetailsActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        if (savedInstanceState == null)
        {
            Bundle extras = getIntent().getExtras();
            if (extras != null && extras.containsKey(Constants.MOVIE))
            {
                Movie movie = extras.getParcelable(Constants.MOVIE);
                if (movie != null)
                {
                    MovieDetailsFragment movieDetailsFragment = MovieDetailsFragment.getInstance(movie);
                    getSupportFragmentManager().beginTransaction().add(R.id.movie_details_container, movieDetailsFragment).commit();
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        MovieDetailsFragment fragment = (MovieDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.movie_details_container);
        if (fragment != null
                && fragment.getFavoriteReasonDialog() != null
                && fragment.getFavoriteReasonDialog().isShowing()){
                fragment.getFavoriteReasonDialog().dismiss();
        } else {
            super.onBackPressed();
        }
    }
}
