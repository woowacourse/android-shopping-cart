package woowacourse.shopping.domain

interface ProductRepository {
    fun findAll(): List<Product>

    fun find(id: Int): Product
}
