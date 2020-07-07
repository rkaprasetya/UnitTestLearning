package com.raka.mockitotutorial.greeter

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

/**
 * https://dzone.com/articles/how-to-use-mockito-in-android
 */
@RunWith(MockitoJUnitRunner::class)
class GreeterTest {
    lateinit var SUT: Greeter
    @Mock
    lateinit var user: User
    @Before
    fun setup() {
        SUT = Greeter(user)
    }
    @Test
    fun givenFullname_correctFullName_ReturnCorrectFullName(){
        //given
        /**
         *  when() is used to configure simple return behavior for a mock or spy object.
         *  thenReturn is used when we want to return a specific value when calling a method on a mock object.
         */
        `when`(user.fullName()).thenReturn("Raka Prasetya")
        //when
        val result = SUT.getEnglishGreeter()
        //then
        assertEquals("Hello Raka Prasetya",result)
    }
}