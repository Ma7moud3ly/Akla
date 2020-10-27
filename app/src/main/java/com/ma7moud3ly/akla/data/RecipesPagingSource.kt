/**
 * recipe app (AKLA أكلة)
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-10-25
 */
package com.ma7moud3ly.akla.data

import androidx.paging.PagingSource
import com.ma7moud3ly.akla.api.ApiService
import com.ma7moud3ly.akla.api.Recipe
import com.ma7moud3ly.akla.api.SearchQuery
import java.lang.Exception

// fetches the recipes from the server with pagination
class RecipesPagingSource(
    private val service: ApiService,
    private val query: SearchQuery
) : PagingSource<Int, Recipe>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        val position = params.key ?: query.firstPage
        var recipes: List<Recipe>? = listOf()
        val response = if (!query.isSearch) service.getRecipes(
            position,
            query.size
        ) else service.searchRecipes(
            page = position,
            size = query.size,
            category = query.category,
            name = query.name,
            cuisine = query.cuisine
        )

        recipes = response?.recipes

        try {
            return LoadResult.Page(
                data = recipes!!,
                prevKey = if (position == query.firstPage) null else position - 1,
                nextKey = if (recipes.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }


}
