package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.DummyShoppingCartRepository
import woowacourse.shopping.data.ShoppingCartRepository
import woowacourse.shopping.data.inventory.DummyProducts
import woowacourse.shopping.data.inventory.InventoryRepository
import woowacourse.shopping.data.inventory.InventoryRepositoryImpl
import woowacourse.shopping.data.product.ProductDatabase
import woowacourse.shopping.data.product.toEntity
import woowacourse.shopping.data.shoppingcart.ShoppingCartDatabase
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
    private val shoppingCartDatabase: ShoppingCartDatabase by lazy {
        ShoppingCartDatabase.database(this)
    }

    val inventoryRepository: InventoryRepository by lazy { InventoryRepositoryImpl(productDatabase.productDao()) }
    val shoppingCartRepository2: InventoryRepository by lazy { InventoryRepositoryImpl(shoppingCartDatabase.productDao()) }
    val shoppingCartRepository: ShoppingCartRepository by lazy { DummyShoppingCartRepository() }
}
