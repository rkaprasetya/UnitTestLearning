package com.raka.mockitotutorial.login

import android.util.Log
import com.raka.mockitotutorial.login.model.LoginResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface LoginRepository{
    fun login(
        username: String,
        password: String,
        onResult: (isSuccess: Boolean, response: LoginResponse?) -> Unit
    )
    fun login2(   username: String,
                  password: String): Single<LoginResponse>
}
class LoginRepositoryImpl(private val apiService: ApiService):LoginRepository {
    override fun login(
        username: String,
        password: String,
        onResult: (isSuccess: Boolean, response: LoginResponse?) -> Unit
    ) {
        val disposable = apiService.login(username, "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                onResult(true, response)
                Log.e("error","error $response")
            }, {
                onResult(false, null)
                Log.e("error","error $it")
            })
    }

    override fun login2(username: String, password: String): Single<LoginResponse> {
        return apiService.login2(username,password).subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread())
    }
}