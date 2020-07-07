package com.raka.mockitotutorial.github

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class GithubActivitiyViewModel(private val githubApi: GithubApi):ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    var githubAccount = MutableLiveData<GithubAccount>()

    internal fun fetchGithubAccountInfo(username:String){
        val disposable = githubApi.getGithubAccountObservable(username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object :DisposableObserver<Response<GithubAccount>>(){
                override fun onComplete() {
                }

                override fun onNext(t: Response<GithubAccount>) {
                    githubAccount.value = t.body()
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}