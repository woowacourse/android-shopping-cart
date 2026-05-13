package woowacourse.shopping.backend

import woowacourse.shopping.storage.room.shoppingItem.ShoppingItemDao
import woowacourse.shopping.storage.room.shoppingItem.ShoppingItemEntity

class ShoppingItemsRemoteSyncer(
    private val shoppingItemDao: ShoppingItemDao,
    private val productBackendDataSource: OkHttpProductBackendDataSource,
) {
    suspend fun sync() {
        val syncedItems =
            productBackendDataSource.fetchProducts().map { shoppingItem ->
                val preservedQuantity = shoppingItemDao.getQuantityOrNull(shoppingItem.getProductId()) ?: 0
                val product = shoppingItem.getProduct()
                ShoppingItemEntity(
                    productId = shoppingItem.getProductId(),
                    title = product.getTitle(),
                    price = product.getPrice(),
                    imageUrl = product.imageUrl,
                    quantity = preservedQuantity,
                )
            }

        if (syncedItems.isEmpty()) {
            shoppingItemDao.deleteAll()
            return
        }

        shoppingItemDao.insertAll(syncedItems)
        shoppingItemDao.deleteByProductIdsNotIn(syncedItems.map { entity -> entity.productId })
    }
}
