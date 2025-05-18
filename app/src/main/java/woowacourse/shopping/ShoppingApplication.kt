package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.DummyProductsRepository
import woowacourse.shopping.data.ProductsRepository

class ShoppingApplication : Application() {
    val productsRepository: ProductsRepository by lazy { DummyProductsRepository() }
}
