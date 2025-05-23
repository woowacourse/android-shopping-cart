package woowacourse.shopping.data.repository

import woowacourse.shopping.data.page.Page
import woowacourse.shopping.data.page.PageRequest
import woowacourse.shopping.domain.ShoppingCartItem

interface ShoppingCartRepository {
    suspend fun findAll(pageRequest: PageRequest): Page<ShoppingCartItem>

    suspend fun findAll(): List<ShoppingCartItem>

    suspend fun totalSize(): Int

    suspend fun remove(item: ShoppingCartItem)

    suspend fun save(item: ShoppingCartItem)

    suspend fun update(item: ShoppingCartItem)

    suspend fun totalQuantity(): Int
}
