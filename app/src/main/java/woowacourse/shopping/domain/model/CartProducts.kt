package woowacourse.shopping.domain.model

data class CartProducts(
    val products: List<CartProduct>,
    val maxPage: Int,
) {
    fun updateCartProductQuantity(
        productId: Int,
        quantity: Int,
    ): CartProducts {
        val updatedProducts =
            products.map { product ->
                if (product.product.id == productId) {
                    product.copy(quantity = quantity)
                } else {
                    product
                }
            }
        return copy(products = updatedProducts)
    }

    fun removeCartProduct(productId: Int): CartProducts {
        val updatedProducts = products.filter { it.product.id != productId }
        return copy(products = updatedProducts)
    }

    companion object {
        val EMPTY_CART_PRODUCTS = CartProducts(emptyList(), 0)
    }
}
