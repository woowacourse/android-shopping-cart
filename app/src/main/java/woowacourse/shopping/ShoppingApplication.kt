package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.repository.cart.CartRepository
import woowacourse.shopping.data.repository.cart.CartRepositoryImpl
import woowacourse.shopping.data.repository.products.ProductRepository
import woowacourse.shopping.data.repository.products.ProductRepositoryImpl
import woowacourse.shopping.data.source.cart.CartDatabase
import woowacourse.shopping.data.source.cart.CartStorageImpl
import woowacourse.shopping.data.source.products.DummyProducts

class ShoppingApplication : Application() {
    val cartDatabase: CartDatabase by lazy {
        CartDatabase.initialize(this)
    }
    val cartStorageImpl: CartStorageImpl by lazy {
        CartStorageImpl.initialize(cartDatabase.cartDao())
    }
    val productRepository: ProductRepository by lazy {
        ProductRepositoryImpl.initialize(
            storage = DummyProducts,
        )
    }
    val cartRepository: CartRepository by lazy { CartRepositoryImpl.initialize(cartStorageImpl) }

    override fun onCreate() {
        super.onCreate()
    }
}
