/**
 * recipe app (شربينيات Sherbinyat)
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-10-15
 */
package com.ma7moud3ly.akla.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.ma7moud3ly.akla.BaseActivity
import com.ma7moud3ly.akla.R
import com.ma7moud3ly.akla.databinding.ActivitySearchBinding
import com.ma7moud3ly.ustore.UPref


class SearchActivity : BaseActivity() {
    //data binding object attached to layout activity_search
    private lateinit var binding: ActivitySearchBinding
    private lateinit var pref: UPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //attach layout (activity_splash) to this activity with dataBinding
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initCuisines()
        initCategories()
        initToolbar()
        pref = UPref(this)
        binding.searchBtn.setOnClickListener { search() }
    }

    private fun initCuisines() {
        val cuisines = resources.getStringArray(R.array.cuisines)
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.item_list, cuisines
        )
        binding.cuisines.adapter = adapter
    }

    private fun initCategories() {
        val categories = resources.getStringArray(R.array.categories)
        val all = categories[0]
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.item_list, categories
        )
        binding.categories.adapter = adapter
    }

    private fun search() {
        val returnIntent = Intent()
        returnIntent.putExtra("name", binding.searchBox.text.toString())
        val category =
            if (binding.categories.selectedItemPosition == 0) "" else binding.categories.selectedItem.toString()
        returnIntent.putExtra("category", category)
        val cuisine =
            if (binding.cuisines.selectedItemPosition == 0) "" else binding.cuisines.selectedItem.toString()
        returnIntent.putExtra("cuisine", cuisine)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.appBar)
        binding.appBar.setNavigationOnClickListener { view ->
            if (binding.searchBox.text.isBlank() &&
                binding.categories.selectedItemPosition == 0 &&
                binding.cuisines.selectedItemPosition == 0
            ) {
                setResult(Activity.RESULT_CANCELED)
                finish()
            } else {
                binding.searchBox.text.clear()
                binding.categories.setSelection(0)
                binding.cuisines.setSelection(0)
            }
        }
        binding.searchBox.setOnTouchListener { v, event ->
            binding.searchBox.setFocusableInTouchMode(true)
            false
        }

        binding.searchBox.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId === EditorInfo.IME_ACTION_SEARCH) {
                search()
            }
            false
        }

    }

    override fun onStart() {
        super.onStart()
        binding.categories.setSelection(pref.get("category", 0))
        binding.cuisines.setSelection(pref.get("cuisine", 0))
        binding.searchBox.setText(pref.get("name", ""))
    }

    override fun onDestroy() {
        super.onDestroy()
        pref.put("name", binding.searchBox.text)
        pref.put("category", binding.categories.selectedItemPosition)
        pref.put("cuisine", binding.cuisines.selectedItemPosition)
    }

}