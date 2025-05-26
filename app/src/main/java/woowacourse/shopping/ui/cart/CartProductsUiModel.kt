package woowacourse.shopping.ui.cart

import woowacourse.shopping.domain.cart.CartProduct

data class CartProductsUiModel(
    val cartProducts: List<CartProduct> = emptyList(),
) {
    fun loadPage(newCartProducts: List<CartProduct>): CartProductsUiModel {
        return CartProductsUiModel(newCartProducts)
    }

    fun increaseQuantity(productId: Long): CartProductsUiModel {
        val updateCartProducts = cartProducts.map { cartProduct ->
            if (cartProduct.product.id == productId){
                cartProduct.copy(_quantity = cartProduct.quantity + 1)
            }
            else cartProduct
        }
        return CartProductsUiModel(updateCartProducts)
    }

    fun decreaseQuantity(productId: Long): CartProductsUiModel {
        val updateCartProducts = cartProducts.map { cartProduct ->
            if (cartProduct.product.id == productId && cartProduct.quantity > 1) cartProduct.copy(_quantity = cartProduct.quantity - 1)
            else cartProduct
        }
        return CartProductsUiModel(updateCartProducts)
    }

}