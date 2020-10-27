/**
 * recipe app (AKLA أكلة)
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-10-25
 */

package com.ma7moud3ly.akla.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ma7moud3ly.akla.api.Recipe
import com.ma7moud3ly.akla.data.FavRepository
import com.ma7moud3ly.akla.data.sotrage.FavRecipe
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch


/**
 * this view model is attached to FavActivity to provide it with the stored recipes in the database
 */
class FavViewModel(
    private val repo: FavRepository
) : ViewModel() {

    /**
     * this method request all stored recipes from the database
     * @return kotlin Flow object contains the recipes list
     */
    fun getAll(): Flow<MutableList<Recipe?>?>? = flow {
        val recipes = mutableListOf<Recipe?>()
        repo?.getAll()?.forEach {
            val recipe: Recipe? = Recipe.deserialize(it.recipe)
            recipes.add(recipe)
        }
        emit(recipes)
    }

    /**
     * this method as the database whether the recipe is stored or not
     * @param str the serialized recipe as json
     * @return emits a boolean true or false
     */
    fun isFaved(str: String): Flow<Boolean> = flow {
        emit(repo.exists(str))
    }

    private var toggleJob: Job? = null
    /**
     * this method executes ROOM query to store or remove the recipe from fromm database
     * @param recipe the recipe object to set/unset favourite
     */
    fun toggleFav(recipe: Recipe) {
        val str = recipe.toString()
        toggleJob?.cancel()
        toggleJob = viewModelScope.launch {
            if (repo.exists(recipe.toString())) {
                repo.delete(FavRecipe(str))
            } else {
                repo.insert(FavRecipe(str))
            }
        }
    }


}