package woowacourse.shopping.presentation.productdetail

import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.presentation.model.ProductModel

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val cartRepository: CartRepository,
    override var productModel: ProductModel,
) : ProductDetailContract.Presenter {

    override fun putProductInCart() {
        cartRepository.addCartProductId(productModel.id)
        view.showCompleteMessage(productModel.name)
    }
}
