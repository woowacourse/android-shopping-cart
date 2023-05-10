package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.repository.ProductRepository

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val productRepository: ProductRepository,
) : ProductDetailContract.Presenter {
    override fun loadProduct(productId: Long) {
        productRepository.findById(productId)?.run {
            view.setProduct(ProductDetailUIState.from(this))
        }
    }
}
