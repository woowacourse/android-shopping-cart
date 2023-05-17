package woowacourse.shopping.error

import android.app.Application

class WoowaShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setCrashHandler()
    }

    private fun setCrashHandler() {
        val exceptionHandler = Thread.getDefaultUncaughtExceptionHandler() ?: return
        Thread.setDefaultUncaughtExceptionHandler(
            WoowaShoppingExceptionHandler(this, exceptionHandler),
        )
    }
}
