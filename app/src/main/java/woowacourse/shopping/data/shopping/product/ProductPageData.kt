package woowacourse.shopping.data.shopping.product

import woowacourse.shopping.domain.entity.Product

data class ProductPageData(
    val pageNumber: Int,
    val content: List<Product>,
    val totalPages: Int,
    val pageSize: Int,
    val totalElements: Int,
)
