package woowacourse.shopping.productdetail

import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.util.toUiModel

class ProductDetailPresenter(
    val view: ProductDetailContract.View,
    override val product: ProductUiModel,
    private val repository: ShoppingRepository,
) : ProductDetailContract.Presenter {

    override fun setUpView() {
        val recentViewedProducts = repository.selectRecentViewedProducts()
        var lastViewedProduct: ProductUiModel? = null
        if (recentViewedProducts.size >= 2) {
            lastViewedProduct =
                recentViewedProducts[LAST_VIEWED_PRODUCT].toUiModel()
        }

        view.setUpRecentViewedProduct(lastViewedProduct)
    }

    override fun addToShoppingCart() {
        repository.insertToShoppingCart(product.id, 1, true)
        view.navigateToShoppingCartView()
    }

    companion object {
        private const val LAST_VIEWED_PRODUCT = 1
    }
}
