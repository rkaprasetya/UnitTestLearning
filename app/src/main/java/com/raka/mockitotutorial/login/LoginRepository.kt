package com.raka.mockitotutorial.login

import android.util.Log
import com.raka.mockitotutorial.login.model.LoginResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface LoginRepository{

    fun login2(   username: String,
                  password: String): Single<LoginResponse>
}
class LoginRepositoryImpl(private val apiService: ApiService):LoginRepository {

    override fun login2(username: String, password: String): Single<LoginResponse> {
        return apiService.login2(username,password).subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread())
    }
}