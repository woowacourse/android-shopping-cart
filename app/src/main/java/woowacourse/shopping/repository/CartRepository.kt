package woowacourse.shopping.repository

import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.Product

interface CartRepository {
    fun add(item: Product)

    fun delete(item: Product)

    fun showAll(): Cart
}
