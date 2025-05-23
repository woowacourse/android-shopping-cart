package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.DummyShoppingCartRepository
import woowacourse.shopping.data.ShoppingCartRepository
import woowacourse.shopping.data.inventory.DummyProducts
import woowacourse.shopping.data.inventory.InventoryRepository
import woowacourse.shopping.data.inventory.InventoryRepositoryImpl
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
            }.join()
        }
    }
    val inventoryRepository: InventoryRepository by lazy { InventoryRepositoryImpl(productDatabase.productDao()) }
    val shoppingCartRepository: ShoppingCartRepository by lazy { DummyShoppingCartRepository() }
}
