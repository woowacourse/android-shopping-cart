package woowacourse.shopping.domain.model

data class OrderList(
    val orders: List<Order>,
    val maxOffSet: Int,
)
