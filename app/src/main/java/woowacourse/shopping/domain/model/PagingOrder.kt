package woowacourse.shopping.domain.model

class PagingOrder(
    val currentPage: Int,
    val orderList: List<Order>,
    val last: Boolean,
)
