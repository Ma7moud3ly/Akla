/**
 * recipe app (AKLA أكلة)
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-10-25
 */

package com.ma7moud3ly.akla.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ma7moud3ly.akla.api.Recipe
import com.ma7moud3ly.akla.databinding.ItemRecipeBinding
import com.ma7moud3ly.akla.observers.RecipeObserver

/**
 * this adapter fills the favourite recipes recyclerview
 */
class FavAdapter(
    private var list: MutableList<Recipe?>?,
    private var observer: RecipeObserver
) :
    RecyclerView.Adapter<FavAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.getRoot())


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemRecipeBinding =
            ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val recipe = this.list?.get(position)
        holder.binding.recipe = recipe
        holder.binding.observer = this.observer
    }


    override fun getItemCount(): Int {
        return list!!.size
    }
}