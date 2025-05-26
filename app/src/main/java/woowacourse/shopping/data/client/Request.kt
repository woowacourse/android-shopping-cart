package woowacourse.shopping.data.client

import okhttp3.HttpUrl

interface Request {
    val url: HttpUrl
    val method: HttpMethod
}
