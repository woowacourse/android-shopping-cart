package woowacourse.shopping.data.model

data class CartEntity(
    val cartId: Long,
    val productId: Long,
    val count: Int,
    val checked: Boolean
) {
    constructor(cartId: Long, productId: Long, count: Int, checked: Int) : this(
        cartId, productId, count, checked == CHECK
    )

    companion object {
        const val CHECK = 1
        const val NON_CHECK = 0
    }
}
