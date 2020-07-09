package com.raka.mockitotutorial.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.raka.mockitotutorial.login.model.DataLoginResponse
import com.raka.mockitotutorial.login.model.LoginResponse
import io.reactivex.Single
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.*
import org.junit.Assert.assertEquals
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {
    lateinit var viewModel: LoginViewModel
    @get:Rule
    val rule = InstantTaskExecutorRule()
    @Mock
    lateinit var observer: Observer<LoginResponse>
    @Mock
    lateinit var loadingObserver: Observer<Boolean>
    @Mock
    lateinit var repository: LoginRepository
    @Mock
    lateinit var toastObserver: Observer<String>
    private val PASSWORD = "password123"
    private val EMAIL = "rka.prasetya@gmail.com"

    @Before
    fun setup() {
        viewModel = LoginViewModel(repository)
        viewModel.data.observeForever(observer)
        viewModel.onLoading.observeForever(loadingObserver)
        viewModel._toastMessage.observeForever(toastObserver)
    }

    @Test
    fun onValidationCorrect2_success_returnCorrectData() {
        //Arrange
        val expectedData = getValidResponse()
        `when`(repository.login2(EMAIL,PASSWORD)).thenReturn(Single.just(getValidResponse()))
        //Act
        viewModel.onValidationCorrect2(EMAIL,PASSWORD)
        //Assert
        val captor = ArgumentCaptor.forClass(LoginResponse::class.java)
        captor.run {
            verify(observer,times(1)).onChanged(capture())
            assertEquals(expectedData,value)
        }
    }

    @Test
    fun onValidationCorrect2_success_dismissLoading() {
        //Arrange
        `when`(repository.login2(EMAIL,PASSWORD)).thenReturn(Single.just(getValidResponse()))
        //Act
        viewModel.onValidationCorrect2(EMAIL,PASSWORD)
        //Assert
        val captor = ArgumentCaptor.forClass(Boolean::class.java)
        captor.run {
            verify(loadingObserver, times(2)).onChanged(capture())
            assertEquals(false,value)
        }
    }
    @Test
    fun onValidationCorrect2_success_returnCorrectMessage() {
        //Arrange
        `when`(repository.login2(EMAIL,PASSWORD)).thenReturn(Single.just(getValidResponse()))
        //Act
        viewModel.onValidationCorrect2(EMAIL,PASSWORD)
        //Assert
        val captor = ArgumentCaptor.forClass(String()::class.java)
        captor.run {
            verify(toastObserver, times(1)).onChanged(capture())
            assertEquals("Login berhasil",value)
        }
    }
    @Test
    fun onValidationCorrect2_failure_observerNoInteraction() {
        //Arrange
        `when`(repository.login2(EMAIL,PASSWORD)).thenReturn(Single.error(Throwable()))
        //Act
        viewModel.onValidationCorrect2(EMAIL,PASSWORD)
        //Assert
        verifyNoMoreInteractions(observer)
    }
    @Test
    fun onValidationCorrect2_failure_returnFailureMessage() {
        //Arrange
        `when`(repository.login2(EMAIL,PASSWORD)).thenReturn(Single.error(Throwable()))
        //Act
        viewModel.onValidationCorrect2(EMAIL,PASSWORD)
        //Assert
        val captor = ArgumentCaptor.forClass(String()::class.java)
        captor.run {
            verify(toastObserver, times(1)).onChanged(capture())
            assertEquals("Login gagal",value)
        }
    }
    @After
    fun after(){
        viewModel.data.removeObserver(observer)
        viewModel.onLoading.removeObserver(loadingObserver)
        viewModel._toastMessage.removeObserver(toastObserver)
    }

    private fun getValidResponse() = LoginResponse(
        DataLoginResponse("role","contract","dealerId","dealerName","tokenType","type","userId","responseCode","accessToken",
            "refreshToken","nik","desc","dob","scope","custId","forceChangePass","expiresIbn","email","email")
    )

    private fun getEmptyData() = LoginResponse(
        DataLoginResponse(
            "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", ""
        )
    )
}