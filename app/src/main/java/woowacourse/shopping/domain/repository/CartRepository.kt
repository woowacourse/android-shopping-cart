package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.product.Product

interface CartRepository {
    fun findProductById(id: Long): Product?
    fun insert(product: Product)
    fun deleteById(id: Long)
    fun getSize(): Int
    fun getPagedProduct(limit: Int, offset: Int): List<Product>
}
