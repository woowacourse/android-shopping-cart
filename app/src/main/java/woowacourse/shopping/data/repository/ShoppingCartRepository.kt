package woowacourse.shopping.data.repository

import woowacourse.shopping.data.page.Page
import woowacourse.shopping.data.page.PageRequest
import woowacourse.shopping.domain.ShoppingCartItem

interface ShoppingCartRepository {
    fun findAll(pageRequest: PageRequest): Page<ShoppingCartItem>

    fun totalSize(): Int

    fun remove(item: ShoppingCartItem)

    fun save(item: ShoppingCartItem)
}
