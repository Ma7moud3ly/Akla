/**
 * recipe app (AKLA أكلة)
 * @author
 * Mahmoud Aly
 * @version 1.0
 * @since   2020-10-25
 */

package com.ma7moud3ly.akla

import android.app.Application
import android.content.Context
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this

        //enable images caching in local storage for Picasso
        val builder = Picasso.Builder(this)
        builder.downloader(OkHttp3Downloader(this, Long.MAX_VALUE))
        val built = builder.build()
        built.setIndicatorsEnabled(true)
        built.isLoggingEnabled = true
        Picasso.setSingletonInstance(built)
    }

    //create global static object for the application context
    companion object {
        lateinit var context: Context
    }
}