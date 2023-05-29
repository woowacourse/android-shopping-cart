package woowacourse.shopping.ui.productdetail.dialog

import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.ui.model.ProductModel
import woowacourse.shopping.ui.model.ShoppingProductModel
import woowacourse.shopping.ui.model.mapper.ProductMapper.toDomain
import woowacourse.shopping.ui.model.mapper.ProductMapper.toView
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
        cartRepository.addCartProduct(cartProduct)
        view.notifyAddToCartCompleted()

        val addedCartProduct = cartRepository.getCartProductByProduct(cartProduct.product)!!
        view.notifyProductChanged(
            product = ShoppingProductModel(addedCartProduct.product.toView(), addedCartProduct.amount),
            amountDifference = cartProduct.amount
        )
    }

    companion object {
        private const val MINIMUM_CART_PRODUCT_AMOUNT = 1
    }
}
