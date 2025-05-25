package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.di.RepositoryInitializer

class ShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        _instance = this
        RepositoryInitializer().init()
    }

    companion object {
        private var _instance: ShoppingApplication? = null
        val instance get() = _instance!!
    }
}
