package com.raka.mockitotutorial.login

import com.raka.mockitotutorial.login.model.DataLoginResponse
import com.raka.mockitotutorial.login.model.LoginResponse
import io.reactivex.Single
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest

import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

import org.junit.Assert.assertThat
import org.mockito.Mockito.*
import org.hamcrest.CoreMatchers.`is`
/**
* Tutorial testing rxjava using Single, Observable and flow
 * still need "runblockingtest" from Coroutine to run on another thread
* https://www.ericdecanini.com/2019/09/16/unit-testing-android-with-rxjava-and-retrofit/
 *
*/
@RunWith(MockitoJUnitRunner::class)
class LoginRepositoryImplTest {
    @Rule
    @JvmField
    val testSchedulerRule = RxTrampolineScheluerRule()
    lateinit var SUT: LoginRepositoryImpl
    @Mock
    lateinit var apiService: ApiService
    private val PASSWORD = "password123"
    private val EMAIL = "rka.prasetya@gmail.com"
    @Before
    fun setup() {
        SUT = LoginRepositoryImpl(apiService)
    }
    @Test
    fun loginRepo_onSuccess_returnValidData(){
        runBlockingTest {
            //arrange
            `when`(apiService.login2(EMAIL,PASSWORD)).thenReturn(Single.just(getValidResponse()))
            //act
            val result = SUT.login2(EMAIL,PASSWORD).blockingGet()
            //assert
            assertThat(result.dataLoginResponse!!.username, `is`(EMAIL))
        }
    }
    @Test
    fun loginRepo_failure_returnEmptyData() {
        //Arrange
        runBlocking {
            `when`(apiService.login2(EMAIL, PASSWORD)).thenReturn(Single.just(getEmptyData()))
            //Act
            val result = SUT.login2(EMAIL, PASSWORD).blockingGet()
            //Assert
            assertThat(result.dataLoginResponse!!.username, `is`(""))
        }
    }
    @Test
    fun loginRepo_success_callServiceOnce() {
        //Arrange
        runBlocking {
            `when`(apiService.login2(EMAIL, PASSWORD)).thenReturn(Single.just(getEmptyData()))
            //Act
            SUT.login2(EMAIL, PASSWORD).blockingGet()
            //Assert
            verify(apiService).login2(EMAIL,PASSWORD)
        }
    }
    private fun getValidResponse() = LoginResponse(
        DataLoginResponse(
            "aa", "aa", "aa", "aa", "aa", "aa", "aa", "aa", "aa",
            "aa", "aa", "aa", "aa", "aa", "aa", "aa", "aa", "aa", EMAIL
        )
    )

    private fun getEmptyData() = LoginResponse(
        DataLoginResponse(
            "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", ""
        )
    )
}