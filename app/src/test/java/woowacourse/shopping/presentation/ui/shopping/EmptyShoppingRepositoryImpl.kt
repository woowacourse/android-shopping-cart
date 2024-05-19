package woowacourse.shopping.presentation.ui.shopping

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingItemsRepository

class EmptyShoppingRepositoryImpl : ShoppingItemsRepository {
    override fun findProductItem(id: Long): Product? {
        return null
    }

    override fun findProductsByPage(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        return emptyList()
    }
}
