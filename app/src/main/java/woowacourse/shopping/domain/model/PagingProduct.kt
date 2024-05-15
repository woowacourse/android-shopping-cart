package woowacourse.shopping.domain.model

data class PagingProduct(
    val currentPage: Int,
    val productList: List<Product>,
    val last: Boolean,
)
