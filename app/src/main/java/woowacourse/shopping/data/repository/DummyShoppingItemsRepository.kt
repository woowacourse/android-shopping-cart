package woowacourse.shopping.data.repository

import woowacourse.shopping.data.database.AppDatabase
import woowacourse.shopping.data.dummy.DummyShoppingItems
import woowacourse.shopping.data.model.ProductEntity
import woowacourse.shopping.domain.model.ProductWithQuantity
import woowacourse.shopping.domain.repository.ShoppingItemsRepository

class DummyShoppingItemsRepository(database: AppDatabase) : ShoppingItemsRepository {
    private val productDao = database.productDao()

    init {
        insertProducts(DummyShoppingItems.items)
    }

    override fun insertProducts(products: List<ProductEntity>) {
        threadAction {
            productDao.insertProducts(products)
        }
    }

    override fun productWithQuantityItem(productId: Long): ProductWithQuantity? {
        var productItem: ProductWithQuantity? = null
        threadAction {
            productItem = productDao.getProductWithQuantityById(productId)
        }
        return productItem
    }

    override fun findProductWithQuantityItemsByPage(
        page: Int,
        pageSize: Int,
    ): List<ProductWithQuantity> {
        var productWithQuantities = emptyList<ProductWithQuantity>()
        val offset = page * pageSize

        threadAction {
            productWithQuantities = productDao.getProductWithQuantityByPage(limit = pageSize, offset = offset)
        }

        return productWithQuantities
    }

    private fun threadAction(action: () -> Unit) {
        val thread = Thread(action)
        thread.start()
        thread.join()
    }
}
