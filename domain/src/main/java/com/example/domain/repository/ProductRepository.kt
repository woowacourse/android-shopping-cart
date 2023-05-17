package com.example.domain.repository

import com.example.domain.Product

interface ProductRepository {

    fun getAll(): List<Product>

//    fun get(fromIndex: Int, ToIndex: Int): List<Product>
}
