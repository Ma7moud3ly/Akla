package com.ma7moud3ly.akla.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ma7moud3ly.akla.data.FavRepository
import com.ma7moud3ly.akla.data.RecipesRepository
import com.ma7moud3ly.akla.models.FavViewModel
import com.ma7moud3ly.akla.models.RecipeViewModel

/**
 * Factory for ViewModels
 */
class ViewModelFactory(
    private val repo: Any
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecipeViewModel(repo as RecipesRepository) as T
        } else if (modelClass.isAssignableFrom(FavViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavViewModel(repo as FavRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
