package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.datasource.DefaultCart
import woowacourse.shopping.data.datasource.DefaultProducts
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository

class ShoppingApplication : Application() {
    val productRepository: ProductRepository by lazy {
        ProductRepositoryImpl(DefaultProducts)
    }
    val cartRepository: CartRepository by lazy {
        CartRepositoryImpl(DefaultCart)
    }
}
