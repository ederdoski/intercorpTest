package com.adrenastudies.intercorptest.viewModels

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adrenastudies.intercorptest.models.LoginRequest
import com.adrenastudies.intercorptest.utils.Constants
import com.adrenastudies.intercorptest.utils.Preferences
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class LoginViewModel  : ViewModel() {

    private val loginObject: MutableLiveData<LoginRequest> by lazy {
        MutableLiveData<LoginRequest>()
    }

    fun getLoginObject(): LiveData<LoginRequest> {
        return loginObject
    }

    fun attempLogin(act:Activity, auth: FirebaseAuth, phone:String, callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(act)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun signInWithCredentials(act:Activity, auth: FirebaseAuth, credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener(act) { task ->
            if (task.isSuccessful) {
                Preferences.setIsLogged(Constants.TRUE)
                loginObject.value = LoginRequest(true, "")
            } else {
                Preferences.setIsLogged(Constants.FALSE)

                if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    loginObject.value =  LoginRequest(false, "Codigo de autenticación Invalido")
                }else{
                    loginObject.value =  LoginRequest(false, "Error Interno")
                }
            }
        }
    }

}