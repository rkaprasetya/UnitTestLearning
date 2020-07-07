package com.raka.mockitotutorial.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.raka.mockitotutorial.user.model.ApiUser
import com.raka.mockitotutorial.user.model.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
import org.junit.rules.TestRule

/**
 * https://blog.mindorks.com/unit-testing-viewmodel-with-kotlin-coroutines-and-livedata
 * Testing viewmodel, coroutine and livedata
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SingleNetworkCallViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule:TestRule = InstantTaskExecutorRule()
    @get:Rule
    val testCoroutineRule=TestCoroutineRule()
    @Mock
    private lateinit var apiHelper: ApiHelper
    @Mock
    private lateinit var dbHelper:DatabaseHelper
    @Mock
    private lateinit var apiUsersObserver:Observer<Resource<List<ApiUser>>>

    @Before
    fun setup() {
    }
    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess(){
        testCoroutineRule.runBlockingTest {
            //given
            val list = listOf(ApiUser(1,"name","email","avatar"))
            `when`(apiHelper.getUsers()).thenReturn(list)
            val SUT = SingleNetworkCallViewModel(apiHelper,dbHelper)
            SUT.getUsers().observeForever(apiUsersObserver)
            verify(apiHelper).getUsers()
            verify(apiUsersObserver).onChanged(Resource.success(list))
            SUT.getUsers().removeObserver(apiUsersObserver)
        }
    }
    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
          val errorMessage = "Error message"
            doThrow(RuntimeException(errorMessage))
                .`when`(apiHelper)
                .getUsers()
            val viewModel = SingleNetworkCallViewModel(apiHelper, dbHelper)
            viewModel.getUsers().observeForever(apiUsersObserver)
            verify(apiHelper).getUsers()
            verify(apiUsersObserver).onChanged(Resource.error(RuntimeException(errorMessage).toString(),null))
        viewModel.getUsers().removeObserver(apiUsersObserver)
        }
    }

}