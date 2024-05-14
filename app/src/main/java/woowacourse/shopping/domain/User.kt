package woowacourse.shopping.domain

data class User(
    val id: Long,
    val cartList: ShoppingCart,
)
