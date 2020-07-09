package com.raka.mockitotutorial.login

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raka.mockitotutorial.login.model.LoginResponse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class LoginViewModel(private val repository: LoginRepository):ViewModel() {
    var data: MutableLiveData<LoginResponse> = MutableLiveData()
    var nextActivity : MutableLiveData<Boolean> = MutableLiveData()
    var _toastMessage:MutableLiveData<String> = MutableLiveData()
    val compositeDisposable = CompositeDisposable()
    var onLoading = MutableLiveData<Boolean>().apply { value = false }

    fun onValidationCorrect2(username:String,password:String) {
        val disposable = repository.login2(username, password).subscribe({
            data.value = it
            onLoading.value = false
            nextActivity.value = true
            _toastMessage.value = "Login berhasil"
        }, {
            onLoading.value = false
            nextActivity.value = true
            _toastMessage.value = "Login gagal"
        })
        compositeDisposable.add(compositeDisposable)
    }
}