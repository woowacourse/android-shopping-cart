package woowacourse.shopping.productdetail

import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toDomain
import woowacourse.shopping.common.model.mapper.ProductMapper.toView
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.repository.RecentProductRepository
import java.time.LocalDateTime

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    productModel: ProductModel,
    recentProductModel: ProductModel?,
    private val recentProductRepository: RecentProductRepository
) : ProductDetailContract.Presenter {
    private val product: Product
    private val recentProduct: Product?

    init {
        product = productModel.toDomain()
        recentProduct = recentProductModel?.toDomain()
        view.setupProductDetail(product.toView())
        view.setupRecentProductDetail(recentProduct?.toView())
    }

    override fun setupCartProductDialog() {
        view.showCartProductDialog(product.toView())
    }

    override fun openProduct(productModel: ProductModel) {
        updateRecentProduct(productModel)
        view.showProductDetail(productModel, null)
    }

    private fun updateRecentProduct(productModel: ProductModel) {
        val recentProduct = RecentProduct(LocalDateTime.now(), productModel.toDomain())
        recentProductRepository.modifyRecentProduct(recentProduct)
    }
}
