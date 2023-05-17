package woowacourse.shopping.error

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.io.PrintWriter
import java.io.StringWriter

class WoowaShoppingExceptionHandler(
    application: Application,
    private val exceptionHandler: Thread.UncaughtExceptionHandler,
) : Thread.UncaughtExceptionHandler {
    private var lastActivity: Activity? = null
    private var activityCount = 0

    init {
        application.registerActivityLifecycleCallbacks(
            object : SimpleActivityLifecycleCallbacks() {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    if (isSkipActivity(activity)) return
                    lastActivity = activity
                }

                override fun onActivityStarted(activity: Activity) {
                    if (isSkipActivity(activity)) return
                    activityCount++
                    lastActivity = activity
                }

                override fun onActivityStopped(activity: Activity) {
                    if (isSkipActivity(activity)) return
                    activityCount--
                    if (activityCount < 0) lastActivity = null
                }
            },
        )
    }

    private fun isSkipActivity(activity: Activity) = activity is ErrorActivity

    override fun uncaughtException(t: Thread, e: Throwable) {
        lastActivity?.run {
            val stringWriter = StringWriter()
            e.printStackTrace(PrintWriter(stringWriter))
            startErrorActivity(this, stringWriter.toString())
        }
        exceptionHandler.uncaughtException(t, e)
        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(-1)
    }

    private fun startErrorActivity(activity: Activity, errorText: String) = activity.run {
        val errorActivityIntent = ErrorActivity.getIntent(this, intent, errorText)
        startActivity(errorActivityIntent)
        finish()
    }
}
