package woowacourse.shopping.presentation.productdetail

import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.repository.CartRepository

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val cartRepository: CartRepository,
    override var productModel: ProductModel,
) : ProductDetailContract.Presenter {

    override fun putProductInCart() {
        cartRepository.putProductInCart(productModel.id)
        view.showCompleteMessage(productModel.name)
    }
}
