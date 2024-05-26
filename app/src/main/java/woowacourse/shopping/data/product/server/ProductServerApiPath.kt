package woowacourse.shopping.data.product.server

object ProductServerApiPath {
    const val BASE_PORT = 12345
    private const val BASE_URL = "http://localhost:$BASE_PORT"

    const val FIND_PRODUCT_PATH = "/product/find/"
    const val FIND_PRODUCT_URL = "$BASE_URL$FIND_PRODUCT_PATH%s"

    const val FIND_PRODUCT_PAGE_PATH = "/products/page/"
    const val FIND_PRODUCT_PAGE_URL = "$BASE_URL$FIND_PRODUCT_PAGE_PATH%d/%d"

    const val TOTAL_COUNT_PATH = "/products/count"
    const val TOTAL_COUNT_URL = "$BASE_URL$TOTAL_COUNT_PATH"

    const val CONTENT_TYPE = "Content-Type"
    const val CONTENT_KEY = "application/json"
}
