package woowacourse.shopping

import android.app.Application

class ShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initRepository()
    }

    private fun initRepository() {
        RepositoryProvider.initCartRepository(this)
    }
}
