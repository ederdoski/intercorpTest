package com.adrenastudies.intercorptest

import android.content.Context
import androidx.multidex.MultiDexApplication

class  App: MultiDexApplication() {

    companion object {

        lateinit var context: Context

        fun setAppContext(ctx: Context) {
            context = ctx
        }
    }

    override fun onCreate() {
        super.onCreate()
        setAppContext(applicationContext)
    }

}