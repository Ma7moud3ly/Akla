/**
 * recipe app (AKLA أكلة)
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-10-25
 */
package com.ma7moud3ly.akla.activities

import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ma7moud3ly.akla.BaseActivity
import com.ma7moud3ly.akla.R
import com.ma7moud3ly.akla.adapters.IngredientsAdapter
import com.ma7moud3ly.akla.adapters.InstructionsAdapter
import com.ma7moud3ly.akla.api.Ingredient
import com.ma7moud3ly.akla.api.Recipe
import com.ma7moud3ly.akla.databinding.ActivityRecipeDetailsBinding
import com.ma7moud3ly.akla.di.Injection
import com.ma7moud3ly.akla.models.FavViewModel
import com.ma7moud3ly.akla.observers.RecipeDetailsObserver
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class RecipeDetailsActivity : BaseActivity() {
    private lateinit var binding: ActivityRecipeDetailsBinding
    private lateinit var model: FavViewModel
    private lateinit var recipe: Recipe
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        //attach layout (activity_recipe_details) to this activity with dataBinding
        binding = ActivityRecipeDetailsBinding.inflate(layoutInflater)
        //init data binding observable to observe some change in the ui..
        binding.observer = RecipeDetailsObserver()
        setContentView(binding.root)

        //attach view model class (FavViewModel) to this activity
        model = ViewModelProvider(
            this,
            Injection.provideFavFactory()
        ).get(FavViewModel::class.java)

        //receive the recipe as serialized  string from intent extras
        if (intent.hasExtra("recipe")) {
            //deserialize the json string to Recipe object.
            recipe = Recipe.deserialize(intent.getStringExtra("recipe"))!!
            //send this object to the view with data binding
            binding.recipe = recipe
            //init the recyclerview shows the recipe ingredients and instructions
            initIngredients(binding.ingredientsRecycler, recipe?.ingredients)
            initInstructions(binding.instructionsRecycler, recipe?.instructions)
        }


        //make the status bar transparent and overlap the layout inside it
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.transBlack2))


        //check if the viewed recipe was added in favourite by user or not.
        checkFav()
        //listen to fav button to set or unset the recipe in favourite
        binding.favToggle.setOnClickListener { model.toggleFav(recipe) }

        //justify the text in description text view.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.recipeDescription.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }

        //listen to the scrollview change in the view
        // to show or hide the page title header depending  on scroll position
        binding.scrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (Math.abs(scrollY - oldScrollY) > 10) {
                val pos = binding.recipeDescription.y + binding.recipeDescription.height
                binding.header.visibility = (
                        if (scrollY > pos) View.GONE
                        else View.VISIBLE
                        )
            }
        }
    }

    /**
     * check if the recipe is added in favourite database
     */
    private fun checkFav() {
        job?.cancel()
        //use kotlin coroutine to listen to thr database response
        job = lifecycleScope.launch {
            model.isFaved(recipe.toString())?.collectLatest {
                binding.favToggle.isChecked = it
            }
        }
    }

    //init the recyclerview shows the recipe ingredients..
    private fun initIngredients(recycler: RecyclerView, list: List<Ingredient>?) {
        recycler.setHasFixedSize(true)
        val gridLayoutManager =
            GridLayoutManager(applicationContext, 1, GridLayoutManager.VERTICAL, false)
        recycler.layoutManager = gridLayoutManager
        val adapter = IngredientsAdapter(list)
        recycler.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    //init the recyclerview shows the recipe instructions..
    private fun initInstructions(recycler: RecyclerView, list: List<String>?) {
        recycler.setHasFixedSize(true)
        val gridLayoutManager =
            GridLayoutManager(applicationContext, 1, GridLayoutManager.VERTICAL, false)
        recycler.layoutManager = gridLayoutManager
        val adapter = InstructionsAdapter(list)
        recycler.adapter = adapter
        adapter.notifyDataSetChanged()
    }

}