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

    override fun addBasketProduct() {
        basketRepository.plusProductCount(product.toDomain())
        view.navigateToBasketScreen()
    }

    override fun inquiryLastViewedProduct() {
        recentProduct?.let { view.navigateToProductDetail(it) }
    }
}
