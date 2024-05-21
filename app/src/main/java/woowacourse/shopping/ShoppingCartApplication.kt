package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.cart.DummyCartRepository
import woowacourse.shopping.data.product.DummyProductRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.data.product.dummyProducts

class ShoppingCartApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ProductRepository.setInstance(DummyProductRepository.getInstance(dummyProducts))
        CartRepository.setInstance(DummyCartRepository.getInstance())
    }
}
