package woowacourse.shopping.remote

import woowacourse.shopping.data.model.ProductData

class ProductMockWebServer {
    companion object {
        const val PAGE_SIZE = 20

        val allProducts =
            List(60) { i ->
                ProductData(
                    i.toLong(),
                    "https://zrr.kr/aPwI",
                    "$i 번째 상품 이름",
                    i * 100,
                )
            } + ProductData.NULL

        const val BASE_PORT = 12345
        const val BASE_URL = "http://localhost:$BASE_PORT"

        const val GET_PRODUCT_PATH = "/product/"
        const val GET_PRODUCT = "$BASE_URL$GET_PRODUCT_PATH%d"

        const val GET_PAGING_PRODUCTS_PATH = "/products/paging/"
        const val GET_PAGING_PRODUCTS = "$BASE_URL$GET_PAGING_PRODUCTS_PATH%d"

        const val GET_TOTAL_COUNT_PATH = "/total-count/"
        const val GET_TOTAL_COUNT = "$BASE_URL$GET_TOTAL_COUNT_PATH"

        const val CONTENT_TYPE = "Content-Type"
        const val CONTENT_KEY = "application/json"
    }
}
