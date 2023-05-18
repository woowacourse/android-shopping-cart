package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.domain.repository.BasketRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.model.UiProduct

class ProductDetailPresenter(
    view: ProductDetailContract.View,
    private val basketRepository: BasketRepository,
    private val product: UiProduct,
) : ProductDetailContract.Presenter(view) {

    init {
        view.showProductImage(product.imageUrl)
        view.showProductName(product.name)
        view.showProductPrice(product.price.value)
    }

    override fun addBasketProduct() {
        basketRepository.plusProductCount(product.toDomain())
        view.navigateToBasketScreen()
    }
}
