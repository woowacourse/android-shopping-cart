package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.repsoitory.DummyData
import woowacourse.shopping.data.repsoitory.ProductRepositoryImpl
import woowacourse.shopping.data.repsoitory.ShoppingCartRepositoryImpl
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository

class ShoppingApplication : Application() {
    val shoppingCartRepository: ShoppingCartRepository by lazy { ShoppingCartRepositoryImpl(DummyData.ORDERS) }
    val productRepository: ProductRepository by lazy { ProductRepositoryImpl(DummyData.PRODUCT_LIST) }

    override fun onCreate() {
        super.onCreate()
    }
}
