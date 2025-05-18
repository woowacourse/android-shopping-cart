package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.cartRepository.CartRepositoryImpl
import woowacourse.shopping.data.productsRepository.ProductRepositoryImpl

class ShoppingApplication : Application() {
    val productRepository: ProductRepositoryImpl by lazy { ProductRepositoryImpl.initialize() }
    val cartRepository: CartRepositoryImpl by lazy { CartRepositoryImpl.initialize(this) }

    override fun onCreate() {
        super.onCreate()
    }
}
