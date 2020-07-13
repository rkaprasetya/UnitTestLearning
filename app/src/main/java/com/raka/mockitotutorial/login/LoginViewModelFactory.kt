package com.raka.mockitotutorial.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LoginViewModelFactory(private val apiHelper: ApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(LoginRepositoryImpl(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}