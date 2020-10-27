/**
 * recipe app (AKLA أكلة)
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-10-25
 */
package com.ma7moud3ly.akla.observers

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField

/**
 * RecipeDetailsObserver  is a data binding observer handles some views changes in the RecipeDetailsActivity
 * @property show_ingredients to decide whether to show the ingredients section or not..
 * @property show_instructions to decide whether to show the instructions section or not..
 */
class RecipeDetailsObserver : BaseObservable() {
    var show_ingredients = ObservableField<Boolean>()
    var show_instructions = ObservableField<Boolean>()


    fun toggleIngredients(view: View) {
        show_ingredients.set(show_ingredients.get()?.not())
        show_instructions.set(false)
    }

    fun toggleInstructions(view: View) {
        show_instructions.set(show_instructions.get()?.not())
        show_ingredients.set(false)

    }

    init {
        this.show_ingredients.set(false)
        this.show_instructions.set(false)
    }
}
