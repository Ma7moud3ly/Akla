/**
 * recipe app (AKLA أكلة)
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-10-25
 */

package com.ma7moud3ly.akla.observers

import android.content.Intent
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ma7moud3ly.akla.R
import com.ma7moud3ly.akla.activities.*
import com.ma7moud3ly.akla.api.Recipe
import com.ma7moud3ly.akla.util.CONSTANTS
import com.squareup.picasso.Picasso

/**
 * RecipeObserver class is a data binding observer handles the gridlayout of the recycler view to decide to show single column or double
 * @param split can CONSTANTS.SINGLE or CONSTANTS.DOUBLE
 * @property split is observable object
 * @property state the state of data fetching (loading,loaded,retry)
 * @property isSearch decide which data viewed in the home screen, random recipes or search result
 */
class RecipeObserver(split: Int?) : BaseObservable() {
    var split = ObservableField<Int>()
    var state = ObservableField<Int>()
    var isSearch = ObservableField<Boolean>()

    /**
     * this method is invoked from the (activity_recipe) layout with data binding
     * splitRecycler toggles split by single column or double column
     */
    fun splitRecycler(v: View) {
        val btn: CheckBox = v as CheckBox
        if (btn.isChecked)
            this.split.set(CONSTANTS.DOUBLE)
        else
            this.split.set(CONSTANTS.SINGLE)
    }


    /**
     * this method is invoked from the (activity_recipe) layout by data binding when user click on the recipe card item.
     * openRecipeDetails method shows the selected recipe in the (RecipeDetailsActivity) screen
     * @param recipe Recipe object is passed  from the view (activity_recipe) to be passed in the (RecipeDetailsActivity)
     */
    fun openRecipeDetails(view: View, recipe: Recipe) {
        val context = view.context
        val intent: Intent = Intent(context, RecipeDetailsActivity::class.java)
        intent.putExtra("recipe", recipe.toString())
        context.startActivity(intent)
    }

    /**
     * this method is invoked from the (activity_recipe) layout by data binding when user click on fav button.
     * openFavRecipes shows starts (FavActivity)
     */
    fun openFavRecipes(view: View) {
        val context = view.context
        val intent: Intent = Intent(context, FavActivity::class.java)
        intent.putExtra("split", this.split.get())
        context.startActivity(intent)
    }


    /**
     * this method is invoked from the (activity_recipe) layout by data binding when user click on menu button.
     * openAbout shows starts (AboutActivity)
     */
    fun openAbout(view: View) {
        val context = view.context
        val intent: Intent = Intent(context, AboutActivity::class.java)
        context.startActivity(intent)
    }


    init {
        this.split.set(split)
        this.isSearch.set(false)
        this.state.set(CONSTANTS.LOADING)
    }
}

/**
 * custom data binding xml attribute to change the GridLayoutManager of the RecyclerView tp split the row in single or two cols
 * */
@BindingAdapter("myLayoutManager")
fun myLayoutManager(view: RecyclerView, split: Int) {
    val buffer_size: Int = if (split == CONSTANTS.SINGLE) 1 else 2
    val gridLayoutManager =
        GridLayoutManager(
            view.context,
            buffer_size,
            GridLayoutManager.VERTICAL,
            false
        )

    view.layoutManager = gridLayoutManager
}

/**
 * custom data binding xml attribute to load the images and thumbnails to an ImageView wih Picasso library
 * */
@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, src: String?) {
    Picasso.get().load(CONSTANTS.IMAGES_URL + src).placeholder(R.drawable.loading_animator)
        .error(R.drawable.logo).into(imageView)
}