package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.di.NetworkModule
import woowacourse.shopping.data.repository.ComplexProductRepository
import woowacourse.shopping.data.repository.LocalCartRepository
import woowacourse.shopping.data.repository.LocalHistoryRepository
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.HistoryRepository
import woowacourse.shopping.domain.repository.ProductRepository

class ShoppingApp : Application() {
    private val database: ShoppingDatabase by lazy { ShoppingDatabase.getInstance(this) }

    private val gson by lazy { NetworkModule.provideGson() }
    private val mockInterceptor by lazy { NetworkModule.provideMockInterceptor(database.productDao()) }
    private val okHttpClient by lazy { NetworkModule.provideOkHttpClient(mockInterceptor) }
    private val retrofit by lazy { NetworkModule.provideRetrofit(okHttpClient, gson) }
    private val productService by lazy { NetworkModule.provideProductService(retrofit) }

    val productRepository: ProductRepository by lazy { ComplexProductRepository(database.productDao(), productService) }
    val cartRepository: CartRepository by lazy { LocalCartRepository(database.cartDao()) }
    val historyRepository: HistoryRepository by lazy { LocalHistoryRepository(database.historyDao()) }
}
