package woowacourse.shopping.productdetail

import android.util.Log
import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.util.toUiModel

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val product: ProductUiModel,
    private val repository: ShoppingRepository,
) : ProductDetailContract.Presenter {

    private val latestViewedProduct: ProductUiModel? = repository.selectLatestViewedProduct()
        ?.toUiModel()

    init {
        view.setUpProductDetailView(
            product = product,
            navigateToLatestViewedProductView = ::loadLatestViewedProduct
        )
        latestViewedProduct?.let {
            view.setUpLatestViewedProductView(it)
        }
    }

    override fun addToShoppingCart() {
        repository.insertToShoppingCart(product.id)
        view.navigateToShoppingCartView()
    }

    override fun loadLatestViewedProduct() {
        Log.d("woogi", "loadLatestViewedProduct: $latestViewedProduct")
        latestViewedProduct?.let {
            view.navigateToProductDetailView(it)
        }
    }
}
