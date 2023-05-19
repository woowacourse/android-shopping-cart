package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.domain.repository.BasketRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.model.UiRecentProduct
import woowacourse.shopping.ui.productdetail.ProductDetailContract.Presenter
import woowacourse.shopping.ui.productdetail.ProductDetailContract.View

class ProductDetailPresenter(
    view: View,
    private val basketRepository: BasketRepository,
    private val product: UiProduct,
    private val recentProduct: UiRecentProduct?,
) : Presenter(view) {

    init {
        view.showProductDetail(product)
        view.showLastViewedProductDetail(recentProduct?.product)
    }

    override fun inquiryProductCounter() {
        view.showProductCounter(product)
    }

    override fun inquiryLastViewedProduct() {
        recentProduct?.let { view.navigateToProductDetail(it) }
    }

    override fun addBasketProductCount(count: Int) {
        basketRepository.addProductCount(product.toDomain(), count)
        view.navigateToHome(product, count)
    }
}
