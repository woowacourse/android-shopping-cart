package woowacourse.shopping.data.entity

typealias DataCartEntity = CartEntity

class CartEntity(
    val id: Int,
    val productId: Int,
    val count: Int,
    val checked: Int,
)
