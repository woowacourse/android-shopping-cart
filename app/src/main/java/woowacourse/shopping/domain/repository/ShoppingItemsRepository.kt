package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product

interface ShoppingItemsRepository {
    fun findProductItem(id: Long): Product?
}
