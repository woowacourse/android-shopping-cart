package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.repository.ProductsRepository
import woowacourse.shopping.data.repository.ProductsRepositoryImpl
import woowacourse.shopping.data.repository.ShoppingCartRepository
import woowacourse.shopping.data.repository.ShoppingCartRepositoryImpl

class ShoppingCartApplication : Application() {
    val productsRepository: ProductsRepository by lazy {
        ProductsRepositoryImpl()
    }
    val shoppingCartRepository: ShoppingCartRepository by lazy {
        ShoppingCartRepositoryImpl()
    }
}
