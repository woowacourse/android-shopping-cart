package woowacourse.shopping

import android.app.Application
import androidx.room.Room
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.data.repository.RecentProductRepositoryImpl
import woowacourse.shopping.data.source.CartDataSourceImpl
import woowacourse.shopping.data.source.ProductDataSourceImpl
import woowacourse.shopping.data.source.RecentProductSourceImpl
import woowacourse.shopping.data.source.local.ShoppingDataBase
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository

class ShoppingApplication : Application() {
    private val database by lazy {
        Room
            .databaseBuilder(
                applicationContext,
                ShoppingDataBase::class.java,
                "shopping-db",
            ).build()
    }

    private val cartItemDao by lazy { database.cartItemDao() }
    private val recentProductDao by lazy { database.recentItemDao() }

    val productRepository: ProductRepository by lazy { ProductRepositoryImpl(ProductDataSourceImpl) }
    val cartRepository: CartRepository by lazy { CartRepositoryImpl(CartDataSourceImpl(cartItemDao)) }
    val recentProductRepository: RecentProductRepository by lazy {
        RecentProductRepositoryImpl(
            RecentProductSourceImpl(recentProductDao),
        )
    }
}
