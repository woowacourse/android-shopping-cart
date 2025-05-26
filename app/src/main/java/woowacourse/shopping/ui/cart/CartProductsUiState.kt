package woowacourse.shopping.ui.cart

import woowacourse.shopping.domain.cart.CartProduct

data class CartProductsUiState(
    val cartProducts: List<CartProduct> = emptyList(),
) {
    fun loadPage(newCartProducts: List<CartProduct>): CartProductsUiState {
        return CartProductsUiState(newCartProducts)
    }

    fun increaseQuantity(productId: Long): CartProductsUiState {
        val updateCartProducts = cartProducts.map { cartProduct ->
            if (cartProduct.id == productId) cartProduct.copy(_quantity = cartProduct.quantity + 1)
            else cartProduct
        }
        return CartProductsUiState(updateCartProducts)
    }

    fun decreaseQuantity(productId: Long): CartProductsUiState {
        val updateCartProducts = cartProducts.map { cartProduct ->
            if (cartProduct.id == productId && cartProduct.quantity > 1) cartProduct.copy(_quantity = cartProduct.quantity - 1)
            else cartProduct
        }
        return CartProductsUiState(updateCartProducts)
    }

}