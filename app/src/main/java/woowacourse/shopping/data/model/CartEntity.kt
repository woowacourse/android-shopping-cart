package woowacourse.shopping.data.model

data class CartEntity(
    val cartId: Long,
    val productId: Long,
    val count: Int,
    val checked: Boolean
) {
    constructor(cartId: Long, productId: Long, count: Int, checked: Int) : this(
        cartId, productId, count, checked == 1
    )
}
