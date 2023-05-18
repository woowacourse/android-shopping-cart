package woowacourse.shopping.productdetail

import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.model.ProductUiModel

class ProductDetailPresenter(
    val view: ProductDetailContract.View,
    override val product: ProductUiModel,
    private val repository: ShoppingRepository,
) : ProductDetailContract.Presenter {

    override fun addToShoppingCart() {
        repository.insertToShoppingCart(product.id, 0, true)
        view.navigateToShoppingCartView()
    }
}
