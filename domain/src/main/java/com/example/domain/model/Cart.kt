package com.example.domain.model

class Cart(private val repository: CartRepository) {

    fun add(product: Product) {
        repository.add(product)
    }

    fun remove(id: Int) {
        repository.remove(id)
    }

    fun getAll(): List<Product> {
        return repository.getAll()
    }
}
