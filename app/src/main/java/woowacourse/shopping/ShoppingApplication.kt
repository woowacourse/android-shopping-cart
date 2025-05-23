package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.DummyShoppingCartRepository
import woowacourse.shopping.data.ShoppingCartRepository
import woowacourse.shopping.data.inventory.DummyProducts
import woowacourse.shopping.data.inventory.InventoryRepository
import woowacourse.shopping.data.inventory.InventoryRepositoryImpl
import woowacourse.shopping.data.product.ProductDatabase
import woowacourse.shopping.data.shoppingcart.ShoppingCartDatabase
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepository2
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepository2Impl
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
    private val shoppingCartDatabase: ShoppingCartDatabase by lazy {
        ShoppingCartDatabase.database(this)
    }

    val inventoryRepository: InventoryRepository by lazy { InventoryRepositoryImpl(productDatabase.productDao()) }
    val shoppingCartRepository2: ShoppingCartRepository2 by lazy { ShoppingCartRepository2Impl(shoppingCartDatabase.cartItemDao()) }

    val shoppingCartRepository: ShoppingCartRepository by lazy { DummyShoppingCartRepository() }
}
