package woowacourse.shopping.presentation

import android.app.Application
import woowacourse.shopping.remote.service.DefaultShoppingProductService

class ShoppingApplication : Application() {
    override fun onTerminate() {
        super.onTerminate()
        DefaultShoppingProductService.shutdown()
    }
}
