package woowacourse.shopping

import android.app.Application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import woowacourse.shopping.data.source.remote.mock.MockServer
import woowacourse.shopping.di.RepositoryProvider

class ShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            runBlocking(Dispatchers.IO) { MockServer.start() }
        }

        RepositoryProvider.init(this)
    }
}
