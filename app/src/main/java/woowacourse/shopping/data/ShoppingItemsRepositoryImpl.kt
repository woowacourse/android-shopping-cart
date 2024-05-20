package woowacourse.shopping.data

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingItemsRepository
import woowacourse.shopping.presentation.ui.shopping.ShoppingViewModel

class ShoppingItemsRepositoryImpl : ShoppingItemsRepository {
    private val products: List<Product> = DummyShoppingItems.items
    private var offset = 0

    override fun findProductsByPage(): List<Product> {
        val fromIndex = offset
        offset = Integer.min(offset + ShoppingViewModel.PAGE_SIZE, products.size)
        return products.subList(fromIndex, offset)
    }

    override fun findProductById(id: Long): Product {
        return products.first { it.id == id }
    }

    override fun canLoadMore(): Boolean {
        return !(products.size < ShoppingViewModel.PAGE_SIZE || offset == products.size)
    }
}
