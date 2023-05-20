package woowacourse.shopping.productdetail.dialog

import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toDomain
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.repository.CartRepository
import java.time.LocalDateTime

class CartProductDialogPresenter(
    private val view: CartProductDialogContract.View,
    productModel: ProductModel,
    private val cartRepository: CartRepository,
    cartProductAmount: Int
) : CartProductDialogContract.Presenter {
    private var cartProduct: CartProduct

    init {
        cartProduct = CartProduct(LocalDateTime.now(), cartProductAmount, true, productModel.toDomain())
        updateCartProductAmount()
    }

    override fun decreaseCartProductAmount() {
        if (cartProduct.amount > MINIMUM_CART_PRODUCT_AMOUNT) {
            cartProduct = cartProduct.decreaseAmount()
            updateCartProductAmount()
        }
    }

    override fun increaseCartProductAmount() {
        cartProduct = cartProduct.increaseAmount()
        updateCartProductAmount()
    }

    private fun updateCartProductAmount() {
        view.updateCartProductAmount(cartProduct.amount)
    }

    override fun addToCart() {
        val prevCartProduct = cartRepository.getCartProductByProduct(cartProduct.product)
        if (prevCartProduct == null) {
            cartRepository.addCartProduct(cartProduct)
        } else {
            updateCartProduct(prevCartProduct)
        }
        view.notifyAddToCartCompleted()
    }

    private fun updateCartProduct(prevCartProduct: CartProduct) {
        cartProduct = prevCartProduct.copy(amount = prevCartProduct.amount + cartProduct.amount)
        cartRepository.modifyCartProduct(cartProduct)
    }

    companion object {
        private const val MINIMUM_CART_PRODUCT_AMOUNT = 1
    }
}
