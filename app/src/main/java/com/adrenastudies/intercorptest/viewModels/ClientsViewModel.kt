package com.adrenastudies.intercorptest.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adrenastudies.intercorptest.database.Configs
import com.adrenastudies.intercorptest.models.AddClientsRequest
import com.adrenastudies.intercorptest.database.User
import com.adrenastudies.intercorptest.fragments.HistoryClients
import com.adrenastudies.intercorptest.models.HistoryUserRequest
import com.adrenastudies.intercorptest.utils.Functions
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ClientsViewModel  : ViewModel() {

    private val clientObject: MutableLiveData<AddClientsRequest> by lazy {
        MutableLiveData<AddClientsRequest>()
    }

    private val listClientsObject: MutableLiveData<HistoryUserRequest> by lazy {
        MutableLiveData<HistoryUserRequest>()
    }

    fun getClientObject(): LiveData<AddClientsRequest> {
        return clientObject
    }

    fun getListClientObject(): LiveData<HistoryUserRequest> {
        return listClientsObject
    }

    fun attempRegisterClient(ctx:Context, user: User) {
        val database = Firebase.database(Configs.databaseURL)
        database.getReference(Configs.USER_TABLE).child(Functions.getRandomUUID()).setValue(user)

        if(Functions.isNetworkAvailable(ctx)){
            clientObject.value = AddClientsRequest(true, "")
        }else{
            clientObject.value = AddClientsRequest(false, "Actualmente no posees conexion a internet, no te preocupes cuando recuperes la conexión tu información se sincronizara satisfactoriamente")
        }

    }

    fun getUsers() {

        val aUsers : ArrayList<User?> = arrayListOf()

        val database = Firebase.database(Configs.databaseURL)

        database.getReference(Configs.USER_TABLE).get().addOnSuccessListener {
            for (messageSnapshot in it.children) {
                val user: User? = messageSnapshot.getValue(User::class.java)
                aUsers.add(user)
            }

            listClientsObject.value = HistoryUserRequest(true, "", aUsers)
        }.addOnFailureListener{
            listClientsObject.value = HistoryUserRequest(false, "Error Obteniendo Data", aUsers)
        }
    }
}