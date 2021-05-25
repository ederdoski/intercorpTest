package com.adrenastudies.myapplication.interfaces

import java.util.ArrayList

abstract class ApiCallback {

    open fun onSuccess(data: Any?) {}

    open fun onSuccess(data: String?) {}

    open fun onDenied(data: String?) {}

    open fun onSuccess(aData: ArrayList<*>) {}

    open fun onForbidden() {}

    open fun onForbidden(data: String?) {}

    open fun onError() {}

    open fun onError(data: String?) {}

}