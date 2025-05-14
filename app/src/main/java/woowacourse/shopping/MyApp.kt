package woowacourse.shopping

import android.app.Application
import android.content.Context

class MyApp : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private lateinit var instance: MyApp
        val applicationContext: Context get() = instance.applicationContext
    }
}
