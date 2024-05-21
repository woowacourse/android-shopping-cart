package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.db.ProductHistoryDatabase
import woowacourse.shopping.data.db.ShoppingCartDatabase
import woowacourse.shopping.data.repsoitory.local.ProductHistoryRepositoryImpl
import woowacourse.shopping.data.repsoitory.local.ShoppingCartRepositoryImpl
import woowacourse.shopping.data.repsoitory.remote.DummyData
import woowacourse.shopping.data.repsoitory.remote.ProductRepositoryImpl
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.local.ProductHistoryRepository
import woowacourse.shopping.domain.repository.local.ShoppingCartRepository

class ShoppingApplication : Application() {
    val shoppingCartRepository: ShoppingCartRepository by lazy {
        ShoppingCartRepositoryImpl(ShoppingCartDatabase.getDatabase(applicationContext).dao())
    }
    val productHistoryRepository: ProductHistoryRepository by lazy {
        ProductHistoryRepositoryImpl(ProductHistoryDatabase.getDatabase(applicationContext).dao())
    }

    val productRepository: ProductRepository by lazy { ProductRepositoryImpl(DummyData.PRODUCT_LIST) }

    override fun onCreate() {
        super.onCreate()
    }
}
