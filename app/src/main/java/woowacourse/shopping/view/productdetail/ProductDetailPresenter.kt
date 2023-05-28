package woowacourse.shopping.view.productdetail

import woowacourse.shopping.R
import woowacourse.shopping.data.server.ProductService
import woowacourse.shopping.domain.CartProductRepository
import woowacourse.shopping.domain.RecentViewedRepository
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.model.toUiModel

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val cartProductRepository: CartProductRepository,
    private val recentViewedRepository: RecentViewedRepository,
    private val productRepository: ProductService,
) : ProductDetailContract.Presenter {

    private lateinit var productData: ProductModel
    private lateinit var latestViewedProductData: ProductModel
    private var latestViewVisibilityFlag: Boolean = false

    override fun setProductData(productModel: ProductModel) {
        productData = productModel
    }

    override fun getProductData(): ProductModel {
        return productData
    }

    override fun setFlag(flag: Boolean) {
        latestViewVisibilityFlag = flag
    }

    override fun getFlag(): Boolean {
        return latestViewVisibilityFlag
    }

    override fun getLatestViewedProductData(): ProductModel {
        val mostRecentId = recentViewedRepository.findMostRecent()
        latestViewedProductData = convertIdToModel(mostRecentId)
        return latestViewedProductData
    }

    override fun updateLatestViewedProducts() {
        recentViewedRepository.add(productData.id)
    }

    override fun compareNowAndRecent() {
        if (latestViewedProductData.id == productData.id) {
            latestViewVisibilityFlag = true
        }
    }

    override fun putInCart(product: ProductModel, productCount: Int) {
        cartProductRepository.add(product.id, productCount, true)
    }

    private fun convertIdToModel(id: Int): ProductModel {
        val product = productRepository.find(id)
        return product.toUiModel()
    }

    override fun navigateRecentViewedDetail() {
        view.startRecentViewedDetail(latestViewedProductData)
    }

    override fun navigateNextStep(itemId: Int) {
        when (itemId) {
            R.id.close -> {
                view.handleBackButtonClicked()
            }
        }
    }
}
