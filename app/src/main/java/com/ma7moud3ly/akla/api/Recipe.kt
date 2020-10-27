/**
 * recipe app (AKLA أكلة)
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-10-25
 */

package com.ma7moud3ly.akla.api

import com.google.gson.Gson
import java.lang.Exception

data class Recipe(
    var name: String?,
    var images: List<String>?,
    var description: String?,
    var totalTime: Int?,
    var prepTime: Int?,
    var yield: String?,
    var category: String?,
    var cuisine: String?,
    var nutrition: Nutrition?,
    var ingredients: List<Ingredient>?,
    var instructions: List<String>??
) {
    override fun toString(): String {
        return Gson().toJson(this).toString()
    }

    companion object {
        fun serialize(recipe: Recipe?): String? {
            return Gson().toJson(recipe)
        }

        fun deserialize(str: String?): Recipe? {
            try {
                return Gson().fromJson(str, Recipe::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
                return Recipe(null,null,null,null,null,null,null,null,null,null,null)
            }
        }
    }

}

data class Nutrition(
    var servingSize: String?,
    var calories: String?
)
data class Ingredient(
    var name: String?,
    var quantity: String?
)
