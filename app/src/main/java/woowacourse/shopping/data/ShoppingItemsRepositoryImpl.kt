package woowacourse.shopping.data

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductsRepository
import woowacourse.shopping.domain.repository.ShoppingItemsRepository

class ShoppingItemsRepositoryImpl(
    productsRepository: ProductsRepository = DummyShoppingItems,
) : ShoppingItemsRepository {
    private val items: List<Product> = productsRepository.items

    override fun fetchProductsSize(): Int {
        return items.size
    }

    override fun fetchProductsWithIndex(
        start: Int,
        end: Int,
    ): List<Product> {
        return items.subList(start, end)
    }

    override fun getAllProducts(): List<Product> {
        return items
    }

    override fun findProductItem(id: Long): Product? {
        return items.firstOrNull { it.id == id }
    }
}
