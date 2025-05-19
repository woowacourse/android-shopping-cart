package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.cartRepository.CartDatabase
import woowacourse.shopping.data.cartRepository.CartRepository
import woowacourse.shopping.data.cartRepository.CartRepositoryImpl
import woowacourse.shopping.data.productsRepository.DummyProducts
import woowacourse.shopping.data.productsRepository.ProductRepository
import woowacourse.shopping.data.productsRepository.ProductRepositoryImpl

class ShoppingApplication : Application() {
    val cartDatabase: CartDatabase by lazy {
        CartDatabase.initialize(this)
    }
    val productRepository: ProductRepository by lazy {
        ProductRepositoryImpl.initialize(
            DummyProducts.value,
        )
    }
    val cartRepository: CartRepository by lazy { CartRepositoryImpl.initialize(cartDatabase) }

    override fun onCreate() {
        super.onCreate()
    }
}
