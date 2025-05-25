package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.repository.cart.CartRepository
import woowacourse.shopping.data.repository.cart.CartRepositoryImpl
import woowacourse.shopping.data.repository.products.ProductRepository
import woowacourse.shopping.data.repository.products.ProductRepositoryImpl
import woowacourse.shopping.data.source.cart.CartDatabase
import woowacourse.shopping.data.source.cart.CartStorage
import woowacourse.shopping.data.source.cart.CartStorageImpl
import woowacourse.shopping.data.source.products.catalog.DummyProducts
import woowacourse.shopping.data.source.products.catalog.ProductStorage

class ShoppingApplication : Application() {
    val cartDatabase: CartDatabase by lazy {
        CartDatabase.initialize(this)
    }
    val cartStorage: CartStorage by lazy {
        CartStorageImpl.initialize(cartDatabase.cartDao())
    }
    val productStorage: ProductStorage by lazy {
        DummyProducts
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
