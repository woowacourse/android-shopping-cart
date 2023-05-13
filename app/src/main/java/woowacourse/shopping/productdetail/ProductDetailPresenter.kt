package woowacourse.shopping.productdetail

import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.model.ProductUiModel

class ProductDetailPresenter(
    val view: ProductDetailContract.View,
    val product: ProductUiModel,
    val repository: ShoppingRepository,
) : ProductDetailContract.Presenter {

    override fun addToShoppingCart() {
        repository.insertToShoppingCart(product.id)
        view.navigateToShoppingCartView()
    }
}
