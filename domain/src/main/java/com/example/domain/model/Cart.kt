package com.example.domain.model

class Cart(private val repository: CartRepository) {

    fun add(product: Product) {
        repository.add(product)
    }

    fun remove(product: Product) {
        repository.remove(product)
    }

    fun getAll():List<Product>{
        return repository.getAll()
    }
}
