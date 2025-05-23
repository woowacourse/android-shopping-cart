package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.DummyInventoryRepository
import woowacourse.shopping.data.DummyShoppingCartRepository
import woowacourse.shopping.data.InventoryRepository
import woowacourse.shopping.data.ShoppingCartRepository
import woowacourse.shopping.data.inventory.DummyProducts
import woowacourse.shopping.data.inventory.InventoryRepository2
import woowacourse.shopping.data.inventory.InventoryRepository2Impl
import woowacourse.shopping.data.product.ProductDatabase
import woowacourse.shopping.data.product.toEntity
import woowacourse.shopping.domain.Product
import kotlin.concurrent.thread

class ShoppingApplication : Application() {
    private val productDatabase: ProductDatabase by lazy {
        ProductDatabase.database(this).apply {
            thread {
                productDao().clear()
                productDao().insertAll(DummyProducts.products.map(Product::toEntity))
            }
        }
    }
    val inventoryRepository2: InventoryRepository2 by lazy { InventoryRepository2Impl(productDatabase.productDao()) }
    val inventoryRepository: InventoryRepository by lazy { DummyInventoryRepository() }
    val shoppingCartRepository: ShoppingCartRepository by lazy { DummyShoppingCartRepository() }
}
