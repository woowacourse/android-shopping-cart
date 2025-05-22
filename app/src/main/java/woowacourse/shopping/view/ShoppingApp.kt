package woowacourse.shopping.view

import android.app.Application
import woowacourse.shopping.di.AppContainer
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository

class ShoppingApp : Application() {
    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(applicationContext)
    }

    companion object {
        private lateinit var appContainer: AppContainer
        val cartRepository: CartRepository
            get() = appContainer.cartRepository

        val productRepository: ProductRepository
            get() = appContainer.productRepository
    }
}
