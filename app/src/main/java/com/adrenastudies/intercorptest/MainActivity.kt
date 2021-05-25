package com.adrenastudies.intercorptest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adrenastudies.intercorptest.utils.Dialogs

class MainActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        Dialogs(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Dialogs(this)
    }

}
