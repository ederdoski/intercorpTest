package com.adrenastudies.intercorptest.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.adrenastudies.intercorptest.R
import com.adrenastudies.intercorptest.models.AddClientsRequest
import com.adrenastudies.intercorptest.database.User
import com.adrenastudies.intercorptest.databinding.FragmentAddClientsBinding
import com.adrenastudies.intercorptest.utils.Constants
import com.adrenastudies.intercorptest.utils.Dialogs
import com.adrenastudies.intercorptest.utils.Functions
import com.adrenastudies.intercorptest.utils.Menu
import com.adrenastudies.intercorptest.viewModels.ClientsViewModel
import com.yarolegovich.lovelydialog.LovelyStandardDialog
import java.text.SimpleDateFormat
import java.util.*


class AddClients : Fragment() {

    private lateinit var dAlert: LovelyStandardDialog
    private lateinit var dLoading: androidx.appcompat.app.AlertDialog

    private lateinit var model: ClientsViewModel
    private lateinit var binding: FragmentAddClientsBinding
    private lateinit var clientsObserver: Observer<AddClientsRequest>

    private val selectedBirthday = Calendar.getInstance()

    private var dateFrom:String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    private var datePickerListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        selectedBirthday.set(Calendar.YEAR, year)
        selectedBirthday.set(Calendar.MONTH, month)
        selectedBirthday.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        dateFrom = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedBirthday.time)
        binding.edtBirthdate.setText(dateFrom)
    }

    private fun initViewModel() {
        model = ViewModelProvider(this).get(ClientsViewModel::class.java)

        clientsObserver = Observer {
            dLoading.dismiss()

            if(it.success) {
                dAlert = Dialogs.basicDialog(R.string.txt_success, R.string.txt_client_saved).setPositiveButton(R.string.btn_ok) { dAlert.dismiss() }
                dAlert.show()
            }else{
                dAlert = Dialogs.basicDialog(R.string.txt_atention, it.message).setPositiveButton(R.string.btn_ok) { dAlert.dismiss() }
                dAlert.show()
            }

            cleanFields()
        }

        model.getClientObject().observe(viewLifecycleOwner, clientsObserver)
    }

    private fun cleanFields() {
        binding.edtName.setText("")
        binding.edtLastName.setText("")
        binding.edtAge.setText("")
        binding.edtBirthdate.setText("")
    }

    private fun btnListener() {
        binding.btnRegisterClient.setOnClickListener {
            checkDataClient()
        }

        binding.edtBirthdate.setOnClickListener{
            val mDate = DatePickerDialog(requireContext(), datePickerListener, selectedBirthday.get(Calendar.YEAR), selectedBirthday.get(Calendar.MONTH), selectedBirthday.get(Calendar.DAY_OF_MONTH))
            mDate.show()
        }

        binding.header.btnMenu.setOnClickListener {
            Menu.open()
        }
    }

    private fun checkDataClient() {
        var isError = false
        val name:String     = binding.edtName.text.toString()
        val lastName:String = binding.edtLastName.text.toString()
        val age:String      = binding.edtAge.text.toString()
        val birthdate:String = binding.edtBirthdate.text.toString()

        if(name.isEmpty() || lastName.isEmpty() || age.isEmpty()) {
            isError = true
        }

        if(isError){
            dAlert = Dialogs.basicDialog(R.string.txt_ups, R.string.txt_error_empty_fields).setPositiveButton(R.string.btn_accept) { dAlert.dismiss() }
            dAlert.show()
        }else{
            dLoading = Dialogs.loading(context)
            dLoading.show()
            model.attempRegisterClient(requireContext(), User(Functions.getRandomUUID(), name, lastName, age, birthdate))
        }
    }

    override fun onResume() {
        super.onResume()
        Menu(activity, binding.nodeDrawer.listDrawer, binding.drawerLayout, Constants.MENU_ADD_CLIENT)
        Menu.setMenu()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_clients, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        btnListener()
    }
}