package woowacourse.shopping.di

import android.content.Context
import com.google.gson.Gson
import okhttp3.OkHttpClient
import woowacourse.shopping.data.database.ShoppingDatabase.Companion.getDatabase
import woowacourse.shopping.data.datasource.CartLocalDataSource
import woowacourse.shopping.data.datasource.CartLocalDataSourceImpl
import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.datasource.ProductDataSourceImpl
import woowacourse.shopping.data.datasource.RecentProductLocalDataSource
import woowacourse.shopping.data.datasource.RecentProductLocalDataSourceImpl
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.data.repository.RecentProductRepositoryImpl
import woowacourse.shopping.data.server.ProductServer
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository

class AppContainer(applicationContext: Context) {
    private val client = OkHttpClient()
    private val gson = Gson()
    private val database = getDatabase(applicationContext)
    val productServer = ProductServer(gson)
    private val productDataSource: ProductDataSource by lazy {
        ProductDataSourceImpl(
            productServer,
            client,
            gson,
        )
    }
    private val cartLocalDataSource: CartLocalDataSource by lazy { CartLocalDataSourceImpl(database.cartDao()) }
    private val recentProductLocalDataSource: RecentProductLocalDataSource by lazy {
        RecentProductLocalDataSourceImpl(
            database.recentProductDao(),
        )
    }

    val productRepository: ProductRepository = ProductRepositoryImpl(productDataSource)

    val cartRepository: CartRepository = CartRepositoryImpl(cartLocalDataSource, productRepository)

    val recentProductRepository: RecentProductRepository by lazy {
        RecentProductRepositoryImpl(
            recentProductLocalDataSource,
            productRepository,
        )
    }
}
