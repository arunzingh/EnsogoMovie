package com.ensogo.movie.sorting;

import android.content.SharedPreferences;

import com.ensogo.movie.BaseApplication;
import com.ensogo.movie.entities.SortType;

public class SortingOptionStore
{
    private SharedPreferences pref;
    private static final int PRIVATE_MODE = 0;
    private static final String SELECTED_OPTION = "selectedOption";
    private static final String PREF_NAME = "SortingOptionStore";

    public SortingOptionStore()
    {
        pref = BaseApplication.getAppContext().getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    }

    public void setSelectedOption(SortType sortType)
    {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(SELECTED_OPTION, sortType.getValue());
        editor.apply();
    }

    public int getSelectedOption()
    {
        return pref.getInt(SELECTED_OPTION, 0);
    }
}
