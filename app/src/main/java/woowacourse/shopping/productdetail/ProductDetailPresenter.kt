package woowacourse.shopping.productdetail

import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.model.ProductUiModel

class ProductDetailPresenter(
    val view: ProductDetailContract.View,
    val product: ProductUiModel,
    val repository: ShoppingRepository
) : ProductDetailContract.Presenter {

    init {
        view.setUpProductDetailView(product)
    }

    override fun addToShoppingCart() {
        repository.insertToShoppingCart(product.id)
        view.navigateToShoppingCartView()
    }
}
