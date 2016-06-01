package com.ensogo.movie.sorting;

import com.ensogo.movie.entities.SortType;

public interface ISortingDialogInteractor
{
    int getSelectedSortingOption();

    void setSortingOption(SortType sortType);
}
