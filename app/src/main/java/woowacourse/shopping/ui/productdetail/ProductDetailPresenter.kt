package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.domain.BasketProduct
import woowacourse.shopping.domain.repository.BasketRepository
import woowacourse.shopping.ui.mapper.toDomain
import woowacourse.shopping.ui.model.UiProduct
import kotlin.concurrent.thread

class ProductDetailPresenter(
    override val view: ProductDetailContract.View,
    private val basketRepository: BasketRepository,
    private val product: UiProduct
) : ProductDetailContract.Presenter {

    override fun initProductData() {
        view.initBindingData(product)
    }

    // 추후 basket pid 로 받아오는 로직 만들어서 넣어야함
    override fun addBasketProduct(): Thread =
        thread {
            basketRepository.add(BasketProduct(product = product.toDomain()))
            view.showBasket()
        }
}
