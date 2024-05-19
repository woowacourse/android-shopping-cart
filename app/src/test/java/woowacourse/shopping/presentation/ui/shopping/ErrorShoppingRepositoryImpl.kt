package woowacourse.shopping.presentation.ui.shopping

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingItemsRepository

class ErrorShoppingRepositoryImpl : ShoppingItemsRepository {
    override fun findProductItem(id: Long): Product? {
        throw IllegalArgumentException("Error")
    }

    override fun findProductsByPage(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        throw IllegalArgumentException("Error")
    }
}
