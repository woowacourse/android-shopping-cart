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
        view.updateCartProductAmount(cartProduct.amount)
    }

    override fun decreaseCartProductAmount() {
        if (cartProduct.amount > MINIMUM_CART_PRODUCT_AMOUNT) {
            cartProduct = cartProduct.decreaseAmount()
            view.updateCartProductAmount(cartProduct.amount)
        }
    }

    companion object {
        private const val MINIMUM_CART_PRODUCT_AMOUNT = 1
    }
}
