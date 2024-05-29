package woowacourse.shopping

interface ViewModelQuantityActions {
    fun plusQuantity(productId: Long)

    fun minusQuantity(productId: Long)
}
