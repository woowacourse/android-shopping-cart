package woowacourse.shopping

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import woowacourse.shopping.data.source.remote.mock.MockServer
import woowacourse.shopping.di.RepositoryProvider

class ShoppingApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            MockServer.start(applicationScope)
        }

        RepositoryProvider.init(this)
    }
}
