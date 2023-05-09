package woowacourse.shopping.domain

interface ProductRepository {
    fun findAll(): List<Product>
}
