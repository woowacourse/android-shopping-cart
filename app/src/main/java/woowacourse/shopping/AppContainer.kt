package woowacourse.shopping

import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.data.storage.CartStorage
import woowacourse.shopping.data.storage.ProductStorage
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.getValue

class AppContainer {
    private val productStorage = ProductStorage()
    private val cartStorage = CartStorage

    val productRepository: ProductRepository by lazy { ProductRepositoryImpl(productStorage) }
    val cartRepository: CartRepository by lazy { CartRepositoryImpl(cartStorage) }
}
