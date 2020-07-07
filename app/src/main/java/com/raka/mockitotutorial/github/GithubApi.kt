package com.raka.mockitotutorial.github

import io.reactivex.Observable
import retrofit2.Response

interface GithubApi {
    fun getGithubAccountObservable(username:String): Observable<Response<GithubAccount>>
}