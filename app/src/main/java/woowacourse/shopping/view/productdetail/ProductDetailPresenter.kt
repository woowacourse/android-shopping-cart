package woowacourse.shopping.view.productdetail

import woowacourse.shopping.data.CartDbRepository
import woowacourse.shopping.model.ProductModel

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val cartDbRepository: CartDbRepository,
) : ProductDetailContract.Presenter {
    override fun putInCart(product: ProductModel) {
        cartDbRepository.add(product.id, 1)
        view.startCartActivity()
    }
}
