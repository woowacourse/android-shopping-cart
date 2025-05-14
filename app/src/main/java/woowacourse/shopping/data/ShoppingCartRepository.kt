package woowacourse.shopping.data

import woowacourse.shopping.domain.Product

interface ShoppingCartRepository {
    fun insertAll(vararg product: Product)

    fun getAll(): List<Product>

    fun delete(productId: Long)
}
