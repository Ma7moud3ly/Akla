/**
 * recipe app (AKLA أكلة)
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-10-25
 */
package com.ma7moud3ly.akla.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ma7moud3ly.akla.api.ApiService
import com.ma7moud3ly.akla.api.PAGE_SIZE
import com.ma7moud3ly.akla.api.Recipe
import com.ma7moud3ly.akla.api.SearchQuery
import kotlinx.coroutines.flow.Flow

/**
 * Repository class that works with local and remote data sources.
 */
class RecipesRepository(private val service: ApiService) {

    fun getRecipes(query: SearchQuery): Flow<PagingData<Recipe>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { RecipesPagingSource(service,query) }
        ).flow
    }

}
