package woowacourse.shopping

import woowacourse.shopping.data.cart.repository.CartRepository
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.productdetail.ProductDetailContract
import woowacourse.shopping.productdetail.ProductDetailPresenter

fun ProductDetailPresenter(
    product: ProductUiModel = ProductUiModel(name = "아메리카노"),
    view: ProductDetailContract.View,
    latestViewedProduct: ProductUiModel? = ProductUiModel(name = "카푸치노"),
    repository: CartRepository,
): ProductDetailPresenter {

    return ProductDetailPresenter(
        product,
        view,
        latestViewedProduct,
        repository
    )
}
