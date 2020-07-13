package com.raka.mockitotutorial.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raka.mockitotutorial.login.model.LoginResponse
import io.reactivex.disposables.CompositeDisposable

class LoginViewModel(private val repository: LoginRepository):ViewModel() {
    var data: MutableLiveData<LoginResponse> = MutableLiveData()
    var nextActivity : MutableLiveData<Boolean> = MutableLiveData()
    var _toastMessage:MutableLiveData<String> = MutableLiveData()
    var toastMessage =  MutableLiveData<EventWrapper<String>>()
    val compositeDisposable = CompositeDisposable()
    var onLoading = MutableLiveData<Boolean>().apply { value = false }
    var username: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()

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
    fun onLoginClick() {
        if (username.value.isNullOrEmpty()) {
            toastMessage.value = EventWrapper("1")
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(username.value).matches()) {
            toastMessage.value = EventWrapper("2")
        } else if (password.value.isNullOrEmpty()) {
            toastMessage.value = EventWrapper("3")
        } else {
            onLoading.value = true
            onValidationCorrect2(username.value.toString().trim(),password.value.toString().trim())
        }
    }

}