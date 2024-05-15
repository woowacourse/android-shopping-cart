package woowacourse.shopping.data

import woowacourse.shopping.domain.Product

interface ShoppingRepository {
    fun products(amount: Int): List<Product>
}
