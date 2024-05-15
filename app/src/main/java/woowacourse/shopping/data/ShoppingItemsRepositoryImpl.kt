package woowacourse.shopping.data

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingItemsRepository

class ShoppingItemsRepositoryImpl : ShoppingItemsRepository {
    private val items: List<Product> = DummyShoppingItems.items

    override fun getAllProducts(): List<Product> {
        return items
    }

    override fun findProductItem(id: Long): Product? {
        return items.firstOrNull { it.id == id }
    }
}
