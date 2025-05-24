package woowacourse.shopping

import android.content.Context
import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.data.datasource.ProductsDataSource
import woowacourse.shopping.data.db.PetoMarketDatabase
import woowacourse.shopping.data.network.MockingServer
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.getValue

class AppContainer(
    context: Context,
) {
    val productRepository: ProductRepository by lazy { ProductRepositoryImpl(productDataSource) }

    val cartRepository: CartRepository by lazy { CartRepositoryImpl(cartDataSource) }

    private val db = PetoMarketDatabase.getInstance(context)

    private val cartDao = db.cartDao()

    private val cartDataSource = CartDataSource(cartDao)

    private val productService = MockingServer()

    private val productDataSource = ProductsDataSource(productService)
}
