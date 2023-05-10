package com.shopping.domain

interface ProductCatalogue {
    val size: Int

    fun addAll(products: List<Product>): ProductCatalogue
    fun contains(product: Product): Boolean
    fun add(product: Product): ProductCatalogue
    fun remove(product: Product): ProductCatalogue
    fun removeFirst(): ProductCatalogue
}
