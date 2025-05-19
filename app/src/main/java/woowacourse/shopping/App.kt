package woowacourse.shopping

import android.app.Application

class App : Application() {
    val container: AppContainer by lazy {
        AppContainer()
    }

    override fun onCreate() {
        super.onCreate()
    }
}
