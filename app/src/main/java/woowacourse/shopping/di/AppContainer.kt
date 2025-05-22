package woowacourse.shopping.di

import android.content.Context
import woowacourse.shopping.data.database.ShoppingDatabase.Companion.getDatabase
import woowacourse.shopping.data.datasource.CartLocalDataSource
import woowacourse.shopping.data.datasource.CartLocalDataSourceImpl
import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.datasource.ProductDataSourceImpl
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository

class AppContainer(applicationContext: Context) {
    private val database = getDatabase(applicationContext)
    private val productDataSource: ProductDataSource by lazy { ProductDataSourceImpl() }
    private val cartLocalDataSource: CartLocalDataSource by lazy { CartLocalDataSourceImpl(database.cartDao()) }

    val cartRepository: CartRepository = CartRepositoryImpl(cartLocalDataSource)

    val productRepository: ProductRepository = ProductRepositoryImpl(productDataSource)
}
