package woowacourse.shopping.productdetail.dialog

import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toDomain
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.repository.CartRepository
import java.time.LocalDateTime

class CartProductDialogPresenter(
    private val view: CartProductDialogContract.View,
    productModel: ProductModel,
    private val cartRepository: CartRepository
) : CartProductDialogContract.Presenter {
    private var cartProduct: CartProduct

    init {
        cartProduct = CartProduct(LocalDateTime.now(), DEFAULT_CART_PRODUCT_AMOUNT, true, productModel.toDomain())
        view.setupCartProductAmount(cartProduct.amount)
    }

    companion object {
        private const val DEFAULT_CART_PRODUCT_AMOUNT = 1
    }
}
