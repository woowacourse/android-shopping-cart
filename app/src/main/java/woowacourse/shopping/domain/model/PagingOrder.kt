package woowacourse.shopping.domain.model

data class PagingOrder(
    val currentPage: Int,
    val orderList: List<Order>,
    val last: Boolean,
)
