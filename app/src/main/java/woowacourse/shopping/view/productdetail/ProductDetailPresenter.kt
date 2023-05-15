package woowacourse.shopping.view.productdetail

import woowacourse.shopping.R
import woowacourse.shopping.domain.CartRepository
import woowacourse.shopping.domain.RecentViewedRepository
import woowacourse.shopping.model.ProductModel

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val cartRepository: CartRepository,
    private val recentViewedRepository: RecentViewedRepository,
) : ProductDetailContract.Presenter {

    private lateinit var productData: ProductModel

    override fun setProductData(productModel: ProductModel) {
        productData = productModel
    }

    override fun getProductData(): ProductModel {
        return productData
    }

    override fun putInCart(product: ProductModel) {
        cartRepository.add(product.id, 1)
        view.startCartActivity()
    }

    override fun updateRecentViewedProducts() {
        recentViewedRepository.add(productData.id)
    }

    override fun handleNextStep(itemId: Int) {
        when (itemId) {
            R.id.close -> {
                view.handleBackButtonClicked()
            }
        }
    }
}
