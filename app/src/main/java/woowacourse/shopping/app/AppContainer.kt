package woowacourse.shopping.app

import android.content.Context
import androidx.room.Room
import woowacourse.shopping.data.ProductFixture
import woowacourse.shopping.data.local.database.ShoppingDatabase
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.data.repository.RecentlyViewedProductRepositoryImpl
import woowacourse.shopping.domain.model.product.Products
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentlyViewedProductRepository

object AppContainer {
    private lateinit var database: ShoppingDatabase

    lateinit var cartRepository: CartRepository
        private set

    lateinit var recentlyViewedProductRepository: RecentlyViewedProductRepository
        private set

    fun initialize(context: Context) {
        database =
            Room
                .databaseBuilder(
                    context.applicationContext,
                    ShoppingDatabase::class.java,
                    "shopping-db",
                ).fallbackToDestructiveMigration(dropAllTables = true)
                .build()

        cartRepository =
            CartRepositoryImpl(
                cartDao = database.cartDao(),
            )

        recentlyViewedProductRepository =
            RecentlyViewedProductRepositoryImpl(
                dao = database.recentlyViewedProductDao(),
                productRepository = productRepository,
            )
    }

    val productRepository: ProductRepository =
        ProductRepositoryImpl(
            products = Products(ProductFixture.productList),
        )
}
