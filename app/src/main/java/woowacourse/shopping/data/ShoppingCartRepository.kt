package woowacourse.shopping.data

import woowacourse.shopping.domain.Product

interface ShoppingCartRepository {
    fun getAll(): List<Product>
}
