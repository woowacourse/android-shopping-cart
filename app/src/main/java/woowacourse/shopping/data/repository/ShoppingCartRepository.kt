package woowacourse.shopping.data.repository

import woowacourse.shopping.data.page.Page
import woowacourse.shopping.data.page.PageRequest
import woowacourse.shopping.view.uimodel.ShoppingCartItemUiModel

interface ShoppingCartRepository {
    fun findAll(pageRequest: PageRequest): Page<ShoppingCartItemUiModel>

    fun findAll(): List<ShoppingCartItemUiModel>

    fun totalSize(): Int

    fun remove(item: ShoppingCartItemUiModel)

    fun save(item: ShoppingCartItemUiModel)

    fun update(item: ShoppingCartItemUiModel)

    fun totalQuantity(): Int
}
