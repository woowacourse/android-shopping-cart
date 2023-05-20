package woowacourse.shopping.view.productdetail

import woowacourse.shopping.R
import woowacourse.shopping.domain.CartRepository
import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.domain.RecentViewedRepository
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.model.toUiModel

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val cartRepository: CartRepository,
    private val recentViewedRepository: RecentViewedRepository,
    private val productRepository: ProductRepository,
) : ProductDetailContract.Presenter {

    private lateinit var productData: ProductModel
    private lateinit var recentViewedProductData: ProductModel

    override fun setProductData(productModel: ProductModel) {
        productData = productModel
    }

    override fun getProductData(): ProductModel {
        return productData
    }

    override fun getRecentViewedProductData(): ProductModel {
        val mostRecentId = recentViewedRepository.findMostRecent()
        recentViewedProductData = convertIdToModel(mostRecentId)
        return recentViewedProductData
    }

    override fun navigateRecentViewedDetail() {
        view.showProductRecentViewedDetail(recentViewedProductData)
    }

    override fun putInCart(product: ProductModel) {
        cartRepository.add(product.id, 1, true)
        view.startCartActivity()
    }

    override fun updateRecentViewedProducts() {
        recentViewedRepository.add(productData.id)
    }

    private fun convertIdToModel(id: Int): ProductModel {
        val product = productRepository.find(id)
        return product.toUiModel()
    }

    override fun handleNextStep(itemId: Int) {
        when (itemId) {
            R.id.close -> {
                view.handleBackButtonClicked()
            }
        }
    }
}
