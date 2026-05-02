package woowacourse.shopping.features.productList

import woowacourse.shopping.domain.product.model.Product

data class ProductListState(
    val products: List<Product> = emptyList(),
    val currentProductCount: Int = 0,
    val totalProductCount: Int = 0,
)
