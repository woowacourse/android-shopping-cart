package woowacourse.shopping

import android.app.Application

class ShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        _instance = this
    }

    companion object {
        private var _instance: ShoppingApplication? = null
        val instance get() = _instance!!
    }
}
