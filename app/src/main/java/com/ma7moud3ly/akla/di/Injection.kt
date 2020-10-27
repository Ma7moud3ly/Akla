/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ma7moud3ly.akla.di

import androidx.lifecycle.ViewModelProvider
import com.ma7moud3ly.akla.App
import com.ma7moud3ly.akla.api.ApiService
import com.ma7moud3ly.akla.data.FavRepository
import com.ma7moud3ly.akla.data.RecipesRepository

/**
 * Class that handles object creation.
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */
object Injection {

    private fun provideRecipeRepository(): RecipesRepository {
        return RecipesRepository(ApiService.create())
    }
    private fun provideFavRepository(): FavRepository {
        return FavRepository(App.context)
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideRecipeFactory(): ViewModelProvider.Factory {
        return ViewModelFactory(provideRecipeRepository())
    }
    fun provideFavFactory(): ViewModelProvider.Factory {
        return ViewModelFactory(provideFavRepository())
    }
}
