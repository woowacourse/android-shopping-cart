package woowacourse.shopping

import android.app.Application

class App : Application() {
    val container: AppContainer by lazy {
        AppContainer(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
    }
}
