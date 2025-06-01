package woowacourse.shopping

import android.content.Context
import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.data.datasource.HistoryDataSource
import woowacourse.shopping.data.datasource.ProductsDataSource
import woowacourse.shopping.data.db.PetoMarketDatabase
import woowacourse.shopping.data.network.MockingServer
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.HistoryRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.HistoryRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.view.loader.CartLoader
import woowacourse.shopping.view.loader.HistoryLoader
import woowacourse.shopping.view.loader.ProductWithCartLoader
import kotlin.getValue

class AppContainer(
    context: Context,
) {
    private val db = PetoMarketDatabase.getInstance(context)

    private val cartDao = db.cartDao()

    private val historyDao = db.historyDao()

    private val cartDataSource = CartDataSource(cartDao)

    private val productService = MockingServer()

    private val productDataSource = ProductsDataSource(productService)

    private val historyDataSource = HistoryDataSource(historyDao)

    val productRepository: ProductRepository by lazy { ProductRepositoryImpl(productDataSource) }

    val cartRepository: CartRepository by lazy { CartRepositoryImpl(cartDataSource) }

    val historyRepository: HistoryRepository by lazy { HistoryRepositoryImpl(historyDataSource) }

    val productWithCartLoader = ProductWithCartLoader(productRepository, cartRepository)

    val historyLoader = HistoryLoader(productRepository, historyRepository)

    val cartLoader = CartLoader(cartRepository, productRepository)
}
