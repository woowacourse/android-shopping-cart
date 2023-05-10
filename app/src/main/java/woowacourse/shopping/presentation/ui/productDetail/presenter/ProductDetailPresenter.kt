package woowacourse.shopping.presentation.ui.productDetail.presenter

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val productRepository: ProductRepository,
) : ProductDetailContract.Presenter {
    override lateinit var product: Product

    override fun getProduct(id: Long) {
        // product = productRepository.getProduct(id)
    }

    override fun putRecentlyViewed(id: Long) {
        TODO("Not yet implemented")
    }
}
