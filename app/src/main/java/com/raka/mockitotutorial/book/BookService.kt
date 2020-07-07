package com.raka.mockitotutorial.book

interface BookService {
    fun inStock(bookId:Int):Boolean
    fun lend(bookId:Int,memberId:Int)
}