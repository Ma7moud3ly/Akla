/**
 * recipe app (AKLA أكلة)
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-10-25
 */

package com.ma7moud3ly.akla.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ma7moud3ly.akla.BaseActivity
import com.ma7moud3ly.akla.databinding.ActivityAboutBinding
import com.ma7moud3ly.akla.models.AboutViewModel

class AboutActivity : BaseActivity() {
    private lateinit var binding: ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val model = AboutViewModel()
        model.appVersion(this)
        binding.about = model
    }

}