package com.raka.mockitotutorial.calculator

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

/**Mockito tutorial
 * https://blog.mindorks.com/using-mockito-in-android-unit-testing-as-a-pro
 */

@RunWith(MockitoJUnitRunner::class)
class CalculatorTest {
    lateinit var SUT: Calculator
    @Mock
    lateinit var operators:Operators
    @Before
    fun setup() {
        SUT = Calculator(operators)
    }

    @Test
    fun givenValidInput_whenAdd_shouldCallAddOperator(){
        //given
        val a = 10
        val b = 20
        //when
        SUT.addTwoNumbers(a,b)
        //then
        /**
         * "verify" means that you want to check if a certain method of a mock object has been called or not.
         */
        verify(operators).add(a,b)
    }

}