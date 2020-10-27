/**
 * recipe app (AKLA أكلة)
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-10-25
 */
package com.ma7moud3ly.akla.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ma7moud3ly.akla.api.Recipe
import com.ma7moud3ly.akla.databinding.ItemRecipeBinding
import com.ma7moud3ly.akla.observers.RecipeObserver

/**
 * this adapter fills the recipe recyclerview in RecipeActivity
 */
class RecipeAdapter(private val observer: RecipeObserver) :
    PagingDataAdapter<Recipe, RecipeAdapter.ItemViewHolder>(DIFF_CALLBACK) {


    class ItemViewHolder(val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.getRoot())


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding: ItemRecipeBinding =
            ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let { recipe ->
            holder.binding.recipe = recipe
            holder.binding.observer = observer
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Recipe> =
            object : DiffUtil.ItemCallback<Recipe>() {
                override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                    return oldItem.name === newItem.name
                }

                override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                    return oldItem.equals(newItem)
                }
            }
    }

}