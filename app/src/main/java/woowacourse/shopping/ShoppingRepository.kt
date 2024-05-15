package woowacourse.shopping

import woowacourse.shopping.domain.Product

interface ShoppingRepository {
    fun products(): List<Product>
}
