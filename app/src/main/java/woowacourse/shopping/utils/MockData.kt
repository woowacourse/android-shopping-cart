package woowacourse.shopping.utils

import woowacourse.shopping.domain.Product

object MockData {
    fun getProductList(): List<Product> {
        return (1..5).map { index ->
            Product(
                id = index.toLong(),
                imageUrl = "",
                name = "Product $index",
                price = 10000,
            )
        }
    }
}
