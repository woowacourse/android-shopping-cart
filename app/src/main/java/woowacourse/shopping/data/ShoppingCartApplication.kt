package woowacourse.shopping.data

import android.app.Application
import woowacourse.shopping.data.cart.CartRepository

class ShoppingCartApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        CartRepository.initialize(this)
    }
}
