package woowacourse.shopping.domain.cartsystem

data class CartPageStatus(
    val isPrevEnabled: Boolean,
    val isNextEnabled: Boolean,
    val count: Int
)
