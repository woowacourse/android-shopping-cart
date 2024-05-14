package woowacourse.shopping.data.cart

import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Quantity

interface CartRepository {
    fun add(product: Product)

    fun delete(product: Product)

    fun findAll(): Map<Product, Quantity>
}
