package com.raka.mockitotutorial.github

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.reactivex.Observable
import org.junit.Assert.*

import org.junit.*
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

import org.hamcrest.CoreMatchers.*
import org.junit.Assert.assertThat
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito.*
import org.mockito.Mockito.verify
import org.hamcrest.CoreMatchers.`is`
import retrofit2.Response

/**
 * https://www.codexpedia.com/android/unit-test-retrofit-2-rxjava-2-and-livedata-in-android/
 * unit test using RxJava
 */
@RunWith(MockitoJUnitRunner::class)
class GithubActivitiyViewModelTest {
    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()
    // Test rule for making the RxJava to run synchronously in unit test
    companion object{
        @ClassRule
        @JvmField
        val schedulers= RxImmediateSchedulerRule()
    }
    @Mock
    lateinit var githubApi: GithubApi
    @Mock
    lateinit var observer:Observer<GithubAccount>
    lateinit var SUT: GithubActivitiyViewModel

    @Before
    fun setup() {
        SUT = GithubActivitiyViewModel(githubApi)
    }
    @Test
    fun givenAccountName_whenFetch_returnValidData(){
        //given
        val githubAccountName = "google"
        val githubAccount = GithubAccount(githubAccountName)
        `when`(githubApi!!.getGithubAccountObservable(githubAccountName))
            .thenReturn(Observable.just(Response.success(githubAccount)))
        //when
        SUT.githubAccount.observeForever(observer)
        SUT.fetchGithubAccountInfo(githubAccountName)
        //then
        assertEquals(githubAccountName,SUT.githubAccount.value!!.login)
    }

}