package woowacourse.shopping.repository

import woowacourse.shopping.model.products.Product

interface ProductRepository {
    fun getAllProducts(): List<Product>

    fun getSize(): Int

    fun getSinglePage(
        start: Int,
        end: Int,
    ): List<Product>
}
