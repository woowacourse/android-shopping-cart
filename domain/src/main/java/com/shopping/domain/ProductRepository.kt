package com.shopping.domain

interface ProductRepository {
    fun getUnitData(unitSize: Int, pageNumber: Int): List<Product>
}
