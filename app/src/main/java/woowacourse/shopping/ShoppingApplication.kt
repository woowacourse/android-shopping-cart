package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.inventory.DummyProducts
import woowacourse.shopping.data.inventory.InventoryRepository
import woowacourse.shopping.data.inventory.InventoryRepositoryImpl
import woowacourse.shopping.data.product.ProductDatabase
import woowacourse.shopping.data.shoppingcart.ShoppingCartDatabase
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepository
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepositoryImpl
import woowacourse.shopping.data.toEntity
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

    private val shoppingCartDatabase: ShoppingCartDatabase by lazy {
        ShoppingCartDatabase.database(this)
    }
    val shoppingCartRepository: ShoppingCartRepository by lazy { ShoppingCartRepositoryImpl(shoppingCartDatabase.cartItemDao()) }
}
