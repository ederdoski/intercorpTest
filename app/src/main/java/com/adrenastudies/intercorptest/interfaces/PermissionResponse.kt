package com.adrenastudies.moneelyte.interfaces

abstract class PermissionResponse {

    open fun onSuccess() {}

    open fun onRefuse() {}

}