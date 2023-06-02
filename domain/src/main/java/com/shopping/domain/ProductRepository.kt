package com.shopping.domain

interface ProductRepository {
    fun getUnitData(
        unitSize: Int,
        pageNumber: Int,
        onFailure: () -> Unit,
        onSuccess: (products: List<Product>) -> Unit
    )
}
