package woowacourse.shopping.domain

data class CartPageStatus(
    val isPrevEnabled: Boolean,
    val isNextEnabled: Boolean,
    val count: Int
)
