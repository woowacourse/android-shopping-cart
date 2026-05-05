package woowacourse.shopping.repository

import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.Product

interface CartRepository {
    suspend fun showAll(): Cart

    suspend fun add(item: Product)

    suspend fun delete(item: Product)
}
