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
    private lateinit var recentViewedProductData: ProductModel
    private var visibilityFlag: Boolean = false

    override fun setProductData(productModel: ProductModel) {
        productData = productModel
    }

    override fun getProductData(): ProductModel {
        return productData
    }

    override fun setFlag(flag: Boolean) {
        visibilityFlag = flag
    }

    override fun getFlag(): Boolean {
        return visibilityFlag
    }

    override fun getRecentViewedProductData(): ProductModel {
        val mostRecentId = recentViewedRepository.findMostRecent()
        recentViewedProductData = convertIdToModel(mostRecentId)
        return recentViewedProductData
    }

    override fun updateRecentViewedProducts() {
        recentViewedRepository.add(productData.id)
    }

    override fun compareNowAndRecent() {
        if (recentViewedProductData.id == productData.id) {
            visibilityFlag = true
        }
    }

    override fun putInCart(product: ProductModel) {
        cartProductRepository.add(product.id, 1, true)
        view.startCartActivity()
    }

    private fun convertIdToModel(id: Int): ProductModel {
        val product = productRepository.find(id)
        return product.toUiModel()
    }

    override fun navigateRecentViewedDetail() {
        view.startRecentViewedDetail(recentViewedProductData)
    }

    override fun navigateNextStep(itemId: Int) {
        when (itemId) {
            R.id.close -> {
                view.handleBackButtonClicked()
            }
        }
    }
}
