package woowacourse.shopping.domain

data class Products(
    val items: List<Product> = emptyList(),
    val hasNext: Boolean = false,
)
