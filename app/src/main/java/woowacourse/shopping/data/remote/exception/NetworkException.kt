package woowacourse.shopping.data.remote.exception

import java.io.IOException

class NetworkException(
    val code: Int,
    message: String,
) : IOException(message)
