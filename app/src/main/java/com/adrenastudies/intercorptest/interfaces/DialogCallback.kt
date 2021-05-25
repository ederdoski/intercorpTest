package com.adrenastudies.moneelyte.interfaces

abstract class DialogCallback {

    open fun onClick() {}

    open fun onSave(data: String?, data2: String?) {}

    open fun onSave(data: String?) {}

    open fun onSave() {}

    open fun onSuccess() {}

    open fun onCancel() {}

}