package woowacourse.shopping.domain

interface ProductRepository {
    fun getProducts(): List<Product>
}
