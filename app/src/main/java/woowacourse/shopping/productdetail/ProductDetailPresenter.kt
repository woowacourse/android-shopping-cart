package woowacourse.shopping.productdetail

import woowacourse.shopping.database.ShoppingRepository

class ProductDetailPresenter(
    val view: ProductDetailContract.View,
    val product: ProductUiModel,
    val repository: ShoppingRepository
) : ProductDetailContract.Presenter {

    override fun addToShoppingCart() {
        repository.addToShoppingCart(product.id)
        view.navigateToShoppingCartView()
    }
}
