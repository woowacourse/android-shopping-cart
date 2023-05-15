package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.domain.repository.BasketRepository
import woowacourse.shopping.ui.mapper.toDomain
import woowacourse.shopping.ui.model.UiProduct
import kotlin.concurrent.thread

class ProductDetailPresenter(
    override val view: ProductDetailContract.View,
    private val basketRepository: BasketRepository,
    private val product: UiProduct
) : ProductDetailContract.Presenter {

    override fun addBasketProduct() {
        thread {
            basketRepository.add(product.toDomain())
            view.showBasket()
        }
    }
}
