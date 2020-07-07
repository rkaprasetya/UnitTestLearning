package com.raka.mockitotutorial.book

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
 * https://www.baeldung.com/kotlin-mockito
 */
@RunWith(MockitoJUnitRunner::class)
class LendBookManagerTest {
    lateinit var SUT: LendBookManager
    @Mock
    lateinit var bookService: BookService
    @Before
    fun setup() {
        SUT = LendBookManager(bookService)

    }
    @Test
    fun givenValidData_whenCheckout_callLendMethod(){
        //given
        `when`(bookService.inStock(100)).thenReturn(true)
        //when
        SUT.checkout(100,1)
        //assert
        verify(bookService).lend(100,1)
    }

    /**
     * Expected illegalStateException is used when we expect that the method in mocked class will give an exception as a result
     */
    @Test(expected = IllegalStateException::class)
    fun givenInvalidData_whenChecout_throwException(){
        //given
        `when`(bookService.inStock(100)).thenReturn(false)
        //when
        SUT.checkout(100,1)
    }

}