package woowacourse.shopping.data.cart

import woowacourse.shopping.model.product.Product

interface CartRepository {
    fun getAll(): List<Product>

    fun add(product: Product)

    fun remove(product: Product)

    fun fetchProducts(
        offset: Int,
        limit: Int,
    ): List<Product>

    fun clear()
}
