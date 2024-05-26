package woowacourse.shopping.presentation.common

interface ProductCountHandler {
    fun plusProductQuantity(
        productId: Long,
        position: Int,
    )

    fun minusProductQuantity(
        productId: Long,
        position: Int,
    )
}
