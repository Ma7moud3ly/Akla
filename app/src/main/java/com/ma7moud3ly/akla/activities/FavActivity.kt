/**
 * recipe app (AKLA أكلة)
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-10-25
 */
package com.ma7moud3ly.akla.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.ma7moud3ly.akla.BaseActivity
import com.ma7moud3ly.akla.adapters.FavAdapter
import com.ma7moud3ly.akla.api.Recipe
import com.ma7moud3ly.akla.databinding.ActivityFavBinding
import com.ma7moud3ly.akla.di.Injection
import com.ma7moud3ly.akla.models.FavViewModel
import com.ma7moud3ly.akla.observers.RecipeObserver
import com.ma7moud3ly.akla.util.CONSTANTS
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class FavActivity : BaseActivity() {
    private lateinit var binding: ActivityFavBinding
    private lateinit var model: FavViewModel
    private lateinit var adapter: FavAdapter
    private lateinit var observer:RecipeObserver
    private var recipes: MutableList<Recipe?>? = mutableListOf()
    private var recipesJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //attach layout (activity_fav) to this activity with dataBinding
        binding = ActivityFavBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val split = intent?.getIntExtra("split", CONSTANTS.SINGLE)
        //create instant of the data binding observer activity
        //and init this observer to split recyclerview in one column ot two..
        observer=RecipeObserver(split)
        binding.observer=observer

        //attach view model class (FavViewModel) to this activity
        model = ViewModelProvider(
            this,
            Injection.provideFavFactory()
        ).get(FavViewModel::class.java)


        //init the recyclerview
        initRecipeRecycler()
    }

    override fun onResume() {
        super.onResume()
        //retrieve the saved recipes from the database
        getRecipes()
    }

    //init the recyclerview
    private fun initRecipeRecycler() {
        binding.recycler.setHasFixedSize(true)
        val gridLayoutManager =
            GridLayoutManager(applicationContext, 1, GridLayoutManager.VERTICAL, false)
        binding.recycler.layoutManager = gridLayoutManager
        adapter = FavAdapter(recipes, observer)
        binding.recycler.adapter = adapter
    }

    //retrieve the saved recipes from the local database
    private fun getRecipes() {
        recipesJob?.cancel()
        //init a kotlin coroutine listens for the coming data from the database to show in the recyclerview
        recipesJob = lifecycleScope.launch {
            model.getAll()?.collectLatest {
                recipes?.clear()
                if (it != null) recipes?.addAll(it)
                recipes?.reverse()
                adapter.notifyDataSetChanged()

            }
        }
    }

}