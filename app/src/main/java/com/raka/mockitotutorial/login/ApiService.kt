package com.raka.mockitotutorial.login

import com.raka.mockitotutorial.login.model.GitResponse
import com.raka.mockitotutorial.login.model.LoginResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("mobile-customer-api/authentication/auth?")
    fun login(@Query("email") email: String, @Query("password") password: String): Single<LoginResponse>

    @POST("mobile-customer-api/authentication/auth?")
    fun login2(@Query("email") email: String, @Query("password") password: String): Single<LoginResponse>

    @GET("search/repositories")
    fun getRepoRx(@Query("q") search: String = "trending", @Query("sort") sort: String = "stars"): Single<GitResponse>
}

class ApiServiceImpl():ApiService{
    override fun login(email: String, password: String): Single<LoginResponse> {
        return ApiClient.getClient().create(ApiService::class.java).login2(email,password).subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread())
    }

    override fun login2(username: String, password: String): Single<LoginResponse> {
        return ApiClient.getClient().create(ApiService::class.java).login2(username,password).subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread())
    }

    override fun getRepoRx(search: String, sort: String): Single<GitResponse> {
        return ApiClient.getClient().create(ApiService::class.java).getRepoRx().subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread())
    }


}
