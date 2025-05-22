package woowacourse.shopping

import woowacourse.shopping.data.datasource.ProductDatasource
import woowacourse.shopping.data.network.MockingServer
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.data.storage.CartStorage
import woowacourse.shopping.data.storage.ProductStorage
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.getValue

class AppContainer {
    private val productStorage = ProductStorage
    private val cartStorage = CartStorage

    private val productService = MockingServer()

    private val productDatasource = ProductDatasource(productService)

    val productRepository: ProductRepository by lazy { ProductRepositoryImpl(productDatasource) }

    val cartRepository: CartRepository by lazy { CartRepositoryImpl(cartStorage) }
}
