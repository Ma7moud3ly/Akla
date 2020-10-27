/**
 * recipe app (AKLA أكلة)
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-10-25
 */
package com.ma7moud3ly.akla.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ma7moud3ly.akla.R
import com.ma7moud3ly.akla.api.Ingredient

/**
 * this adapter fills the ingredients  recyclerview in RecipeDetailsActivity
 */
class IngredientsAdapter(private var list: List<Ingredient>?) :
    RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val stepText: TextView = view.findViewById(R.id.step_text)
        private val stepNumber: TextView = view.findViewById(R.id.step_number)

        fun bind(text: String?, number: String?) {
            this.stepText.text = text?.trim()
            this.stepNumber.text = number
        }

        companion object {
            fun create(parent: ViewGroup): MyViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_recipe_step, parent, false)
                return MyViewHolder(view)
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder.create(parent)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val ingredient = list?.get(position)
        if (ingredient != null)
            holder.bind(ingredient.name + " : " + ingredient.quantity, "" + (position + 1))
    }

    override fun getItemCount(): Int {
        return list!!.size
    }
}