package woowacourse.shopping.domain

import woowacourse.shopping.domain.product.Product

data class CartResult(
    val products: List<Product>,
    val hasNextPage: Boolean,
)
