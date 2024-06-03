package woowacourse.shopping.domain.model

data class PagingOrder(
    val orderList: List<Order>,
    val first: Boolean,
    val last: Boolean,
)
