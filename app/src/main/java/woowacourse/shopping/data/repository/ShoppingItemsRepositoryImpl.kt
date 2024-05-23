package woowacourse.shopping.data.repository

import woowacourse.shopping.data.database.CartDatabase
import woowacourse.shopping.data.database.ProductDatabase
import woowacourse.shopping.data.dummy.DummyShoppingItems
import woowacourse.shopping.data.model.ProductEntity
import woowacourse.shopping.domain.model.ProductWithQuantity
import woowacourse.shopping.domain.repository.ShoppingItemsRepository

class ShoppingItemsRepositoryImpl(
    productDatabase: ProductDatabase,
    cartDatabase: CartDatabase,
) : ShoppingItemsRepository {
    private val productDao = productDatabase.productDao()
    private val cartDao = cartDatabase.cartDao()

    init {
        insertProducts(DummyShoppingItems.items)
    }

    override fun insertProducts(products: List<ProductEntity>) {
        threadAction {
            productDao.insertProducts(products)
        }
    }

    override fun productWithQuantityItem(id: Long): ProductWithQuantity? {
        var productItem: ProductWithQuantity? = null
        threadAction {
            val product = productDao.findWithProductId(id)
            val quantity = cartDao.getQuantityByProductId(id) ?: 1
            productItem = ProductWithQuantity(product = product, quantity = quantity)
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
            val products = productDao.findByPaged(offset = offset, limit = pageSize)
            productWithQuantities =
                products.map { product ->
                    val quantity = cartDao.getQuantityByProductId(product.id) ?: 0
                    ProductWithQuantity(product = product, quantity = quantity)
                }
        }

        return productWithQuantities
    }

    private fun threadAction(action: () -> Unit) {
        val thread = Thread(action)
        thread.start()
        thread.join()
    }
}
