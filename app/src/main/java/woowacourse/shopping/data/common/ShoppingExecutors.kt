package woowacourse.shopping.data.common

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

val ioExecutor: ExecutorService by lazy { Executors.newFixedThreadPool(64) }
