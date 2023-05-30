package woowacourse.shopping.view.productdetail

import com.shopping.domain.RecentProduct
import com.shopping.repository.ProductRepository
import com.shopping.repository.RecentProductsRepository
import woowacourse.shopping.model.uimodel.ProductUIModel
import woowacourse.shopping.model.uimodel.RecentProductUIModel
import woowacourse.shopping.model.uimodel.mapper.toDomain
import woowacourse.shopping.model.uimodel.mapper.toUIModel

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val recentProductsRepository: RecentProductsRepository,
    private val productRepository: ProductRepository
) : ProductDetailContract.Presenter {

    private lateinit var product: ProductUIModel

    override fun loadProduct(productId: Int) {
        productRepository.getProductById(
            productId,
            onSuccess = {
                product = it.toUIModel()
                view.setProductDetailView(product)
                view.setRecentProductView(product)
            },
            onFailure = {}
        )
    }

    private fun getLatestRecentProduct(): RecentProduct? {
        return recentProductsRepository.getFirst()
    }

    override fun isRecentProductExist(): Boolean {
        return recentProductsRepository.getAll().isNotEmpty()
    }

    override fun setRecentProductView(product: ProductUIModel): RecentProductUIModel {
        val latestRecentProduct =
            getLatestRecentProduct() ?: throw IllegalStateException(DATA_ERROR_MESSAGE)
        if ((latestRecentProduct.product.id == product.id)) {
            view.hideLatestProduct()
        } else {
            view.showLatestProduct()
        }
        recentProductsRepository.insert(RecentProduct(product.toDomain()))
        return latestRecentProduct.toUIModel()
    }

    override fun showDialog(dialog: CountSelectDialog) {
        dialog.show(product)
    }

    companion object {
        private const val DATA_ERROR_MESSAGE = "빈값입니다."
    }
}
