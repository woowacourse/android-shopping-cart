package woowacourse.shopping.data.cart

import woowacourse.shopping.data.product.ProductDataSource
import woowacourse.shopping.model.CartProduct
import woowacourse.shopping.model.Product
import woowacourse.shopping.presentation.mapper.toPresentation
import woowacourse.shopping.presentation.model.CartProductModel
import woowacourse.shopping.presentation.model.UnCheckableCartProductModel

class CartRepositoryImpl(
    private val cartDataSource: CartDataSource,
    private val productDataSource: ProductDataSource,
) : CartRepository {

    override fun findProductById(productId: Long): Product {
        return productDataSource.findProductById(productId) ?: Product.defaultProduct
    }

    override fun getCartProducts(): List<CartProduct> {
        val cartItems = cartDataSource.getAllCartItems()
        return cartItems.map {
            CartProduct(
                it.id,
                productDataSource.findProductById(it.productId)!!,
                quantity = it.quantity,
                isChecked = true,
            )
        }
    }

    override fun getCartProduct(productId: Long): CartProduct {
        val cartItem = cartDataSource.getCartItem(productId)
        return CartProduct(
            cartItem.id,
            productDataSource.findProductById(cartItem.productId)!!,
            cartItem.quantity,
            true,
        )
    }

    override fun deleteCartProduct(productId: Long) {
        val cartItem = cartDataSource.getCartItem(productId)
        cartDataSource.deleteCartItem(cartItem.id)
    }

    override fun insertCartProduct(productId: Long, count: Int) {
        cartDataSource.insertCartItem(productId)
        if (count > 1) {
            val cartItem = cartDataSource.getCartItem(productId)
            cartDataSource.updateCartItem(cartItem.id, count)
        }
    }

    override fun updateCartProductCount(cartId: Long, count: Int) {
        if (count <= 0) {
            cartDataSource.deleteCartItem(cartId)
            return
        }
        cartDataSource.updateCartItem(cartId, count)
    }

    override fun getProductsByRange(startIndex: Int, size: Int): List<CartProductModel> {
        val products = productDataSource.getProductsWithRange(startIndex, size)
        return products.map {
            val cartItem = cartDataSource.getCartItem(it.id)
            UnCheckableCartProductModel(cartItem.id, it.toPresentation(), cartItem.quantity)
        }
    }
}
