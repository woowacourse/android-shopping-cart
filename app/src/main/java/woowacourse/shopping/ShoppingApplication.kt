package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.di.RepositoryProvider

class ShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RepositoryProvider.init(this)
    }
}
