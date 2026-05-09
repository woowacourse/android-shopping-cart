package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.data.source.CartDataSourceImpl
import woowacourse.shopping.data.source.ProductDataSourceImpl
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository

class ShoppingApplication : Application() {
    val productRepository: ProductRepository by lazy { ProductRepositoryImpl(ProductDataSourceImpl) }
    val cartRepository: CartRepository by lazy { CartRepositoryImpl(CartDataSourceImpl) }
}
