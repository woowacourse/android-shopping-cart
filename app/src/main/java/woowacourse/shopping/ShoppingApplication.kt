package woowacourse.shopping

import android.app.Application
import okhttp3.OkHttpClient
import woowacourse.shopping.data.inventory.DummyProducts
import woowacourse.shopping.data.inventory.InventoryRepository
import woowacourse.shopping.data.inventory.InventoryRepositoryImpl
import woowacourse.shopping.data.inventory.InventoryRepositoryRemoteImpl
import woowacourse.shopping.data.inventory.MockInterceptor
import woowacourse.shopping.data.product.ProductDatabase
import woowacourse.shopping.data.recent.RecentProductDatabase
import woowacourse.shopping.data.recent.RecentProductRepository
import woowacourse.shopping.data.recent.RecentProductRepositoryImpl
import woowacourse.shopping.data.shoppingcart.ShoppingCartDatabase
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepository
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepositoryImpl
import kotlin.concurrent.thread

class ShoppingApplication : Application() {
    private val useLocal = false

    private val productDatabase: ProductDatabase by lazy {
        ProductDatabase.database(this).apply {
            thread {
                productDao().clear()
                productDao().insertAll(DummyProducts.products)
            }.join()
        }
    }
    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder().addInterceptor(MockInterceptor()).build()
    }

    val inventoryRepository: InventoryRepository by lazy {
        if (useLocal) {
            InventoryRepositoryImpl(productDatabase.productDao())
        } else {
            InventoryRepositoryRemoteImpl(client)
        }
    }

    private val shoppingCartDatabase: ShoppingCartDatabase by lazy {
        ShoppingCartDatabase.database(this)
    }
    val shoppingCartRepository: ShoppingCartRepository by lazy {
        ShoppingCartRepositoryImpl(
            shoppingCartDatabase.cartItemDao(),
        )
    }

    private val recentProductDatabase: RecentProductDatabase by lazy {
        RecentProductDatabase.database(
            this,
        )
    }
    val recentProductRepository: RecentProductRepository by lazy {
        RecentProductRepositoryImpl(
            recentProductDatabase.recentProductDao(),
        )
    }
}
