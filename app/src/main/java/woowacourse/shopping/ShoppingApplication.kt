package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.source.remote.mock.MockServer
import woowacourse.shopping.di.RepositoryProvider

class ShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MockServer.start()
        RepositoryProvider.init(this)
    }
}
