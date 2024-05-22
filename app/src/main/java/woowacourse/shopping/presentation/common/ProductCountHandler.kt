package woowacourse.shopping.presentation.common

interface ProductCountHandler {
    fun addProductQuantity(
        productId: Long,
        position: Int,
    )

    fun minusProductQuantity(
        productId: Long,
        position: Int,
    )
}
