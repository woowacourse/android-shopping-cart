package woowacourse.shopping.data.client

import okhttp3.HttpUrl

class RequestImpl(
    override val url: HttpUrl,
    override val method: HttpMethod,
) : Request
