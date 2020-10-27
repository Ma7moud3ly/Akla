/**
 * recipe app (AKLA أكلة)
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-10-25
 */

package com.ma7moud3ly.akla.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ma7moud3ly.akla.api.Recipe
import com.ma7moud3ly.akla.api.SearchQuery
import com.ma7moud3ly.akla.data.RecipesRepository
import kotlinx.coroutines.flow.Flow

/**
 * this view model is attached to RecipesActivity to provide it with recipes fetched from the api
 */
class RecipeViewModel(
    private val repository: RecipesRepository
) : ViewModel() {

    /**
     * this method request the recipes from the repository
     * @param query the query decides whether to search or fetch data
     * @return kotlin Flow object contains the pagination object to the recipes
     */
    fun getRecipes(query: SearchQuery): Flow<PagingData<Recipe>>? {
        return repository.getRecipes(query).cachedIn(viewModelScope)
    }
}