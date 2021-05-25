package com.adrenastudies.intercorptest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrenastudies.intercorptest.R
import com.adrenastudies.intercorptest.adapters.UsersAdapter
import com.adrenastudies.intercorptest.database.User
import com.adrenastudies.intercorptest.databinding.FragmentHistoryClientsBinding
import com.adrenastudies.intercorptest.models.AddClientsRequest
import com.adrenastudies.intercorptest.models.HistoryUserRequest
import com.adrenastudies.intercorptest.utils.Constants
import com.adrenastudies.intercorptest.utils.Dialogs
import com.adrenastudies.intercorptest.utils.Menu
import com.adrenastudies.intercorptest.viewModels.ClientsViewModel
import com.faltenreich.skeletonlayout.applySkeleton
import com.yarolegovich.lovelydialog.LovelyStandardDialog


class HistoryClients : Fragment() {

    private lateinit var dAlert: LovelyStandardDialog

    private lateinit var model: ClientsViewModel

    private lateinit var binding: FragmentHistoryClientsBinding
    private lateinit var clientsObserver: Observer<HistoryUserRequest>

    private val adapter = UsersAdapter()

    private fun initViewModel() {
        model = ViewModelProvider(this).get(ClientsViewModel::class.java)

        clientsObserver = Observer {
            if(it.success){
                setListUsers(it.aUsers)
            }else{
                dAlert = Dialogs.basicDialog(R.string.txt_ups, it.message).setPositiveButton(R.string.btn_ok) { dAlert.dismiss() }
                dAlert.show()
            }
        }

        model.getListClientObject().observe(viewLifecycleOwner, clientsObserver)
    }

    private fun btnListener() {
        binding.header.btnMenu.setOnClickListener {
            Menu.open()
        }
    }

    private fun setListUsers(aData:ArrayList<User?>) {
        adapter.UsersAdapter(requireContext(), aData)
        val verticalLayout = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvClients.layoutManager = verticalLayout
        binding.rvClients.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        Menu(activity, binding.nodeDrawer.listDrawer, binding.drawerLayout, Constants.MENU_HISTORY_CLIENT)
        Menu.setMenu()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history_clients, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        btnListener()
        model.getUsers()
    }
}