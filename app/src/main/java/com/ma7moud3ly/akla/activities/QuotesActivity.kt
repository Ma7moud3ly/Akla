/**
 * quotes screen on recipe app (شربينيات Sherbinyat)
 *
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-10-15
 */
package com.ma7moud3ly.akla.activities

import android.content.res.TypedArray
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ma7moud3ly.akla.R
import com.ma7moud3ly.akla.databinding.ActivityQuoteBinding
import com.ma7moud3ly.akla.api.ChefQuote


class QuotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //attach layout (activity_quote) to this activity with dataBinding
        binding = ActivityQuoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //load random chef quote and image
        getChefQuotes()
    }

    //this method shows a random quote by well known chef
    //quote is an object of class ChefQuote that has 3 instance variables
    //name:the name of chef
    //quote:string of quote
    //img:drawable for the chef image
    private lateinit var chefs: ArrayList<ChefQuote>
    private fun getChefQuotes() {
        chefs = ArrayList()
        //loads chef names,quotes,images form resources (res/values/arrays)
        val imgs: TypedArray = resources.obtainTypedArray(R.array.chef_imgs)
        val quotes = resources.getStringArray(R.array.chef_quotes)
        val names = resources.getStringArray(R.array.chef_names)
        //add variables to ChefQuote ArrayList
        for (i in 0..names.size - 1) {
            val chef = ChefQuote(
                names[i],
                quotes[i],
                imgs.getDrawable(i)
            )
            chefs.add(chef)
        }
        //choose random quote to show on splash screen
        val rand = (0..chefs.size - 1).random()
        //send the chef object to the view with data binding
        binding.chef = chefs.get(rand)
    }
}