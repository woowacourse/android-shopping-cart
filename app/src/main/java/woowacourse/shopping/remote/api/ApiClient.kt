package woowacourse.shopping.remote.api

object ApiClient {
    const val BASE_PORT = 12345
    private const val BASE_URL = "http://localhost:$BASE_PORT"

    const val GET_FIND_PRODUCT_PATH = "/product/find/"
    const val GET_FIND_PRODUCT = "$BASE_URL$GET_FIND_PRODUCT_PATH%d"

    const val GET_PAGING_PRODUCT_PATH = "/product/paging/"
    const val GET_PAGING_PRODUCT = "$BASE_URL$GET_PAGING_PRODUCT_PATH%d/%d"

    const val CONTENT_TYPE = "Content-Type"
    const val CONTENT_VALUE = "application/json"
}
