package woowacourse.shopping.productdetail

import model.Product
import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.util.toUiModel

class ProductDetailPresenter(
    val view: ProductDetailContract.View,
    override val product: ProductUiModel,
    private val repository: ShoppingRepository,
) : ProductDetailContract.Presenter {

    private var _count = 0
    override val count: Int
        get() = _count

    override fun setUpView() {
        val recentViewedProducts = repository.selectRecentViewedProducts()
        var lastViewedProduct: ProductUiModel? = null
        if (recentViewedProducts.isExist()) {
            lastViewedProduct =
                recentViewedProducts[LAST_VIEWED_PRODUCT].toUiModel()
        }

        view.setUpRecentViewedProduct(lastViewedProduct)
    }

    private fun List<Product>.isExist() = this.size >= 2

    override fun onClickShoppingCartBtn() {
        view.showCountProductView()
    }

    override fun changeCount(isAdd: Boolean) {
        val amount = if (isAdd) PLUS_AMOUNT else MINUS_AMOUNT
        _count += amount
    }

    override fun addToShoppingCart() {
        repository.insertToShoppingCart(product.id, count, true)
        view.navigateToShoppingCartView()
    }

    companion object {
        private const val LAST_VIEWED_PRODUCT = 1
        private const val PLUS_AMOUNT = 1
        private const val MINUS_AMOUNT = -1
    }
}
