package woowacourse.shopping.presentation.productdetail

import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.presentation.model.ProductModel

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val cartRepository: CartRepository,
) : ProductDetailContract.Presenter {

    override fun putProductInCart(productModel: ProductModel) {
        cartRepository.addCartProductId(productModel.id)
        view.showCompleteMessage(productModel.name)
    }
}
