package com.adrenastudies.myapplication.interfaces

import android.util.Log
import java.util.*

abstract class HTTPResponse {

    private val debug = true

    open fun onSuccess(result: List<*>) {}

    open fun onSuccess(result: Any?) {}

    open fun onSuccess(result: String?) {}

    open fun onForbidden(result: Any?) {
        Log.e("onForbidden", "FORBIDDEN")
    }

    open fun onException(e: Exception?) {
        Log.e("onException", e.toString())
    }

    open fun onError(error: String?) {
        Log.e("onError", error)
    }

}