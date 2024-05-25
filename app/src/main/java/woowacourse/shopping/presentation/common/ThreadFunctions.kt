package woowacourse.shopping.presentation.common

import java.util.concurrent.FutureTask
import kotlin.concurrent.thread

fun <T> runOnOtherThreadAndReturn(function: () -> T): T {
    val task = FutureTask { function() }
    Thread(task).start()
    return task.get()
}

fun runOnOtherThread(function: () -> Unit) {
    thread { function() }.join()
}
