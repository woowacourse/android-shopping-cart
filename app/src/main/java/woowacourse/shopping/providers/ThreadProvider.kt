package woowacourse.shopping.providers

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object ThreadProvider {
    private val executor: ExecutorService = Executors.newFixedThreadPool(10)

    fun execute(block: () -> Unit) {
        executor.execute(block)
    }

    fun terminate() {
        executor.shutdown()
    }
}
