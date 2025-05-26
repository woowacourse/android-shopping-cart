package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.data.repository.ProductRepositoryImpl

class ShoppingCartApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CartRepository.initialize(this, ProductRepositoryImpl())
    }
}
