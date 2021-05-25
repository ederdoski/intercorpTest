package com.adrenastudies.intercorptest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.adrenastudies.intercorptest.R
import com.adrenastudies.intercorptest.databinding.FragmentLoginBinding
import com.adrenastudies.intercorptest.models.LoginRequest
import com.adrenastudies.intercorptest.utils.Dialogs
import com.adrenastudies.intercorptest.viewModels.LoginViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.yarolegovich.lovelydialog.LovelyStandardDialog
import com.yarolegovich.lovelydialog.LovelyTextInputDialog

class Login : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var model: LoginViewModel

    private lateinit var dCode: LovelyTextInputDialog
    private lateinit var dAlert: LovelyStandardDialog
    private lateinit var dLoading: androidx.appcompat.app.AlertDialog

    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginObserver: Observer<LoginRequest>

    private lateinit var verificationID: String
    private lateinit var authCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private fun initViewModel() {
        auth = FirebaseAuth.getInstance()
        model = ViewModelProvider(this).get(LoginViewModel::class.java)

        loginObserver = Observer {
            dLoading.dismiss()

            if(it.success){
                Navigation.findNavController(binding.root).navigate(R.id.action_login_to_home)
            }else{
                dAlert = Dialogs.basicDialog(R.string.txt_ups, it.message).setPositiveButton(R.string.btn_ok) { dAlert.dismiss() }
                dAlert.show()
            }
        }

        model.getLoginObject().observe(viewLifecycleOwner, loginObserver)
    }

    private fun loading() {
        dLoading = Dialogs.loading(context)
        dLoading.show()
    }

    private fun btnListener() {

        binding.txtLoginsProblem.setOnClickListener {
            dCode = Dialogs.dialogActivationCode(requireContext())
            .setConfirmButton(android.R.string.ok) {
                loading()
                val credential = PhoneAuthProvider.getCredential(verificationID, it.toString())
                model.signInWithCredentials(requireActivity(), auth, credential)
                dCode.dismiss()
            }
            .setNegativeButton(R.string.btn_cancel) {
                dCode.dismiss()
            }
            .setInputFilter(R.string.txt_error_input_dialog_token_short) {
                it.toString().trim().isNotEmpty()
            }
            dCode.show()
        }

        binding.btnlogin.setOnClickListener {
            var phone = binding.edtPhone.text.toString().trim()

            if(phone.isNotEmpty()) {
                loading()
                phone = binding.codePicker.selectedCountryCodeWithPlus + phone
                model.attempLogin(requireActivity(), auth, phone, authCallbacks)
            }else{
                dAlert = Dialogs.basicDialog(R.string.txt_ups, R.string.txt_error_login).setPositiveButton(R.string.btn_ok) {dAlert.dismiss()}
                dAlert.show()
            }
        }
    }

    private fun initPhoneAuthCallbacks() {
        authCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                loading()
                model.signInWithCredentials(requireActivity(), auth, credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                dLoading.hide()
                if (e is FirebaseAuthInvalidCredentialsException) {
                    dAlert = Dialogs.basicDialog(R.string.txt_ups, R.string.txt_error_invalid_request).setPositiveButton(R.string.btn_ok) {dAlert.dismiss()}
                    dAlert.show()
                } else if (e is FirebaseTooManyRequestsException) {
                    dAlert = Dialogs.basicDialog(R.string.txt_ups, R.string.txt_error_sms_quote).setPositiveButton(R.string.btn_ok) {dAlert.dismiss()}
                    dAlert.show()
                }
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                dLoading.dismiss()
                verificationID = verificationId
                dAlert = Dialogs.basicDialog(R.string.txt_atention, R.string.btn_token_send).setPositiveButton(R.string.btn_ok) {dAlert.dismiss()}
                dAlert.show()
                binding.txtWait.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        btnListener()
        initPhoneAuthCallbacks()
    }

}