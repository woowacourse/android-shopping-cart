package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.repository.ShoppingRepositoryProvider

class ShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ShoppingRepositoryProvider.initialize(this)
    }
}
