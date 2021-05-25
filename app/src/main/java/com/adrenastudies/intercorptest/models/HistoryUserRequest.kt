package com.adrenastudies.intercorptest.models

import com.adrenastudies.intercorptest.database.User

data class HistoryUserRequest(
    val success:Boolean,
    val message:String,
    val aUsers:ArrayList<User?>
)