package woowacourse.shopping.remote

import woowacourse.shopping.data.model.ProductData

class ProductMockWebServer {
    companion object {
        const val PAGE_SIZE = 20

        val allProducts =
            List(60) { i ->
                ProductData(
                    i.toLong(),
                    "https://s3-alpha-sig.figma.com/img/05ef/e578/d81445480aff1872344a6b1b35323488?Expires=1717977600&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=jjZ9gCGElFMx8druqQBDkJzs4DH63phHkPxed4C9L3zVCoTV7XpxN58haKoLSFn3QIplsaREj2dUxlfCtym-x5edhFH078DeazrunG99WoKeYnuu2xmxDdSoJ7bckyLltypAUxYF0HQhRobKtSnIuWUQpHpu27lYSuTxsmWmmTrmg1waiPMnZHwaMgFU71Cb54OGn1SvK3Q1dasdhwsELC0wXdqb81wjFQ8fjjiYgfMJ4makKT3Ia6rAC1VF5dnRbHsL1FpGni3RrQ6nxMYjCzp7LVDaa5PCm8g9rGgEGm-AbMwXdenh7ZbZe3W2mbhfmve1V9RcHwSoXqAwD16zWQ__",
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
