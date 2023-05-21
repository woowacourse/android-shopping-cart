package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.domain.BasketProduct
import woowacourse.shopping.domain.repository.BasketRepository
import woowacourse.shopping.ui.mapper.toDomain
import woowacourse.shopping.ui.model.UiProduct
import kotlin.concurrent.thread

class ProductDetailPresenter(
    override val view: ProductDetailContract.View,
    private val basketRepository: BasketRepository,
    private var currentProduct: UiProduct,
    private var previousProduct: UiProduct?
) : ProductDetailContract.Presenter {

    override fun initProductData() {
        view.updateBindingData(currentProduct, previousProduct)
    }

    override fun selectPreviousProduct() {
        currentProduct = previousProduct ?: throw IllegalStateException(NO_PREVIOUS_PRODUCT_ERROR)
        previousProduct = null
        view.updateBindingData(currentProduct, previousProduct)
    }

    // 추후 basket pid 로 받아오는 로직 만들어서 넣어야함
    override fun addBasketProduct(): Thread =
        thread {
            basketRepository.add(BasketProduct(product = currentProduct.toDomain()))
            view.showBasket()
        }

    companion object {
        private const val NO_PREVIOUS_PRODUCT_ERROR = "이전 아이템이 없는데 접근하고 있습니다."
    }
}
