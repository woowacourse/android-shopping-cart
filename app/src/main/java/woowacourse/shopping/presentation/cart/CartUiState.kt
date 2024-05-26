package woowacourse.shopping.presentation.cart

data class CartUiState(
    val products: List<CartProductUi>,
    val currentPage: Int,
    val canLoadPrevPage: Boolean,
    val canLoadNextPage: Boolean,
) {
    fun increaseProductCount(
        productId: Long,
        amount: Int,
    ): CartUiState =
        copy(
            products =
                products.map {
                    if (it.product.id == productId) {
                        it.copy(count = it.count + amount)
                    } else {
                        it
                    }
                },
        )

    fun decreaseProductCount(
        productId: Long,
        amount: Int,
    ): CartUiState =
        copy(
            products =
                products.map {
                    if (it.product.id == productId) {
                        it.copy(count = it.count - amount)
                    } else {
                        it
                    }
                },
        )

    fun canDecreaseProductCount(
        productId: Long,
        countLimit: Int,
    ): Boolean {
        val product = findProduct(productId) ?: return false
        return product.count > countLimit
    }

    fun findProduct(productId: Long): CartProductUi? = products.find { it.product.id == productId }
}
