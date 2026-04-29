package woowacourse.shopping.ui.productList

import woowacourse.shopping.domain.product.Product

data class ProductListState(
    val products: List<Product> = emptyList(),
    val currentProductCount: Int = 0,
    val totalProductCount: Int = 0,
)
