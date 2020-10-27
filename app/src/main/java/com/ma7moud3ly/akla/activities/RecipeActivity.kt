/**
 * recipe app (AKLA أكلة)
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-10-25
 */
package com.ma7moud3ly.akla.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.ma7moud3ly.akla.BaseActivity
import com.ma7moud3ly.akla.R
import com.ma7moud3ly.akla.adapters.RecipeAdapter
import com.ma7moud3ly.akla.api.SearchQuery
import com.ma7moud3ly.akla.databinding.ActivityRecipeBinding
import com.ma7moud3ly.akla.di.Injection
import com.ma7moud3ly.akla.models.RecipeViewModel
import com.ma7moud3ly.akla.observers.RecipeObserver
import com.ma7moud3ly.akla.util.CONSTANTS
import com.ma7moud3ly.akla.util.CheckInternet
import com.ma7moud3ly.ustore.UPref
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class RecipeActivity : BaseActivity() {
    private lateinit var binding: ActivityRecipeBinding
    private lateinit var model: RecipeViewModel
    private lateinit var observer: RecipeObserver
    private lateinit var pref: UPref
    private lateinit var adapter: RecipeAdapter
    private var searchJob: Job? = null

    companion object {
        val SEARCH_ACTIVITY_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //attach view model class (RecipeViewModel) to this activity
        model = ViewModelProvider(
            this,
            Injection.provideRecipeFactory()
        ).get(RecipeViewModel::class.java)
        //create instant of the data binding observer activity
        //and init this observer to split recyclerview in one column for items (single split)
        observer = RecipeObserver(CONSTANTS.SINGLE)
        //attach layout (activity_recipe) to this activity with dataBinding
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //pass observer and model objects to the view with data binding
        binding.observer = observer
        binding.model = model
        //UPref class is a third party handles shared preferences
        pref = UPref(this)
        //restore the saved stettings in shared preferences
        getSettings()

        //start search activity
        binding.header.btnSearch.setOnClickListener { showSearchActivity() }
        binding.header.searchBox.setOnClickListener { showSearchActivity() }
        //clear search result
        binding.header.btnClearSearch.setOnClickListener { clearSearch() }
        //refresh recipes list
        binding.refresh.setOnClickListener { refresh() }
        binding.bubbleRefreshBtn.setOnClickListener { refresh() }

        //init recipes recyclerview
        initRecipeRecycler()
        //fetch recipes viewed in the home page
        //recipes are fetched with pagination
        // firstPage is a random number from 1 - 34 (number of available pages)
        fetchRecipes(SearchQuery(isSearch = false, firstPage = (1..34).random()))

    }

    /**
     * initialize the recyclerview to show the recipes
     */
    private fun initRecipeRecycler() {
        binding.recycler.setHasFixedSize(true)
        val gridLayoutManager =
            GridLayoutManager(applicationContext, 1, GridLayoutManager.VERTICAL, false)
        binding.recycler.layoutManager = gridLayoutManager
       //attach the recyclerview with the adapter RecipeItemAdapter to fetch recipes with pagination
        adapter = RecipeAdapter(observer)
        binding.recycler.adapter = adapter
        //a kotlin coroutine listens for the pagination state..
        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadStates ->
                observer.state.set(
                    if (loadStates.refresh is LoadState.Loading) CONSTANTS.LOADING
                    else if (loadStates.refresh is LoadState.Error) CONSTANTS.RETRY
                    else if (loadStates.refresh is LoadState.NotLoading) CONSTANTS.LOADED
                    else CONSTANTS.RETRY
                )
            }
        }
    }

    /**
     * fetch recipes from the apo
     * @param query : an object specifies query type and data size to fetch
     */
    private fun fetchRecipes(query: SearchQuery) {
        //check for internet availability
        if (!CheckInternet.isConnected(this)) {
            observer.state.set(CONSTANTS.RETRY)
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
            return
        }
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        //init a kotlin coroutine listens for the coming data from the api
        searchJob = lifecycleScope.launch {
            model.getRecipes(query)?.collectLatest {
                observer.state.set(CONSTANTS.LOADED)
                try {
                    adapter.submitData(it)
                } catch (e: Exception) {
                }
            }

        }
    }

    /**
     * refresh the recyclerview
     */
    private fun refresh() {
        clearSearch();
        fetchRecipes(SearchQuery(isSearch = false, firstPage = (1..34).random()))
    }

    /**
     *clear search results and fetch random data in home page
     */
    private fun clearSearch() {
        binding.header.searchBox.text = getString(R.string.search_title)
        observer.isSearch.set(false)
        fetchRecipes(SearchQuery(isSearch = false))
    }

    /**
     *start search activity
     */
    private fun showSearchActivity() {
        val intent = Intent(this, SearchActivity::class.java)
        startActivityForResult(intent, SEARCH_ACTIVITY_CODE)
    }

    //store settings on leaving activity
    override fun onStop() {
        super.onStop()
        setSettings()
    }

    /**
     * load settings from the shared prefs
     * */
    fun getSettings() {
        observer.split.set(pref.get("split", CONSTANTS.SINGLE))
    }

    /**
     * store settings in the shared prefs
     * */
    fun setSettings() {
        pref.put("split", observer.split.get())
    }


    /**
     * listen to the results from search activity to fetch recipes in the home page depending on them
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SEARCH_ACTIVITY_CODE && resultCode == Activity.RESULT_OK) {
            //create search query object by name,category or cuisine of recipe
            val query = SearchQuery()
            query.isSearch = true
            query.name = data?.getStringExtra("name") ?: null
            query.category = data?.getStringExtra("category") ?: null
            query.cuisine = data?.getStringExtra("cuisine") ?: null
            val searchTitle = (
                    if (!query.name.isNullOrBlank()) query.name
                    else if (!query.cuisine.isNullOrBlank()) query.cuisine
                    else if (!query.category.isNullOrBlank()) query.category
                    else getString(R.string.search_title)
                    )
            binding.header.searchBox.text = searchTitle
            observer.isSearch.set(true)

            fetchRecipes(query)
        }
    }

}