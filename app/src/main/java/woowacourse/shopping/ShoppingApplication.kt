package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.repository.cart.CartRepository
import woowacourse.shopping.data.repository.cart.CartRepositoryImpl
import woowacourse.shopping.data.repository.products.catalog.ProductRepository
import woowacourse.shopping.data.repository.products.catalog.ProductRepositoryImpl
import woowacourse.shopping.data.source.cart.CartDatabase
import woowacourse.shopping.data.source.cart.CartStorage
import woowacourse.shopping.data.source.cart.CartStorageImpl
import woowacourse.shopping.data.source.products.catalog.DummyProducts
import woowacourse.shopping.data.source.products.catalog.ProductStorage
import woowacourse.shopping.data.source.products.recentlyviewed.RecentlyViewedDatabase
import woowacourse.shopping.data.source.products.recentlyviewed.RecentlyViewedStorage
import woowacourse.shopping.data.source.products.recentlyviewed.RecentlyViewedStorageImpl

class ShoppingApplication : Application() {
    val cartDatabase: CartDatabase by lazy {
        CartDatabase.initialize(this)
    }
    val recentlyViewedDatabase: RecentlyViewedDatabase by lazy {
        RecentlyViewedDatabase.initialize(this)
    }
    val cartStorage: CartStorage by lazy {
        CartStorageImpl.initialize(cartDatabase.cartDao())
    }
    val productStorage: ProductStorage by lazy {
        DummyProducts
    }
    val recentlyViewedStorage: RecentlyViewedStorage by lazy {
        RecentlyViewedStorageImpl.initialize(recentlyViewedDatabase.recentlyViewedDao())
    }
    val productRepository: ProductRepository by lazy {
        ProductRepositoryImpl.initialize(
            storage = productStorage,
        )
    }
    val cartRepository: CartRepository by lazy {
        CartRepositoryImpl.initialize(
            cartStorage,
            productStorage,
        )
    }

    override fun onCreate() {
        super.onCreate()
    }
}
