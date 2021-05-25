package com.adrenastudies.intercorptest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.adrenastudies.intercorptest.R
import com.adrenastudies.intercorptest.utils.Constants
import com.adrenastudies.intercorptest.utils.Preferences
import java.util.*


class Splash : Fragment() {

    private fun goToNextFragment(view: View) {
        val timer = Timer()
        val task: TimerTask = object : TimerTask() {
            override fun run() {
                activity?.runOnUiThread {
                    if(Preferences.isUserLogged() == Constants.FALSE){
                        Navigation.findNavController(view).navigate(R.id.action_splash_to_login)
                    }else{
                        Navigation.findNavController(view).navigate(R.id.action_splash_to_home)
                    }
                }
            }
        }
        timer.schedule(task, 2000)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)
        goToNextFragment(view)
        return view
    }
}