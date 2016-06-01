package com.ensogo.movie.sorting;

import com.ensogo.movie.entities.SortType;

public class SortingDialogInteractor implements ISortingDialogInteractor
{
    private SortingOptionStore mSortingOptionStore;

    public SortingDialogInteractor()
    {
        mSortingOptionStore = new SortingOptionStore();
    }

    @Override
    public int getSelectedSortingOption()
    {
        return mSortingOptionStore.getSelectedOption();
    }

    @Override
    public void setSortingOption(SortType sortType)
    {
        mSortingOptionStore.setSelectedOption(sortType);
    }
}
