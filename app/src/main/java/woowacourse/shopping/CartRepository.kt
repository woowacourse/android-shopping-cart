package woowacourse.shopping

import woowacourse.shopping.domain.Product

interface CartRepository {
    fun addItem(product: Product)
    fun removeItem(product: Product)
}
