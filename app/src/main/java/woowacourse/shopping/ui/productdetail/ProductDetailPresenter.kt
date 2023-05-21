package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.domain.BasketProduct
import woowacourse.shopping.domain.Count
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

    init {
        currentProduct.basketCount =
            basketRepository.getByProductId(currentProduct.id)?.count?.value ?: 0
        if (previousProduct != null) {
            previousProduct?.basketCount =
                basketRepository.getByProductId(requireNotNull(previousProduct).id)?.count?.value
                    ?: 0
        }
    }

    override fun initProductData() {
        view.updateBindingData(currentProduct, previousProduct)
    }

    override fun setBasketDialog() {
        view.showBasketDialog(
            currentProduct,
            ::minusProductCount,
            ::plusProductCount,
            ::updateBasketProduct
        )
        view.updateProductCount(currentProduct.basketCount)
    }

    private fun minusProductCount() {
        if (currentProduct.basketCount - 1 >= 0) currentProduct.basketCount -= 1
        view.updateProductCount(currentProduct.basketCount)
    }

    private fun plusProductCount() {
        currentProduct.basketCount += 1
        view.updateProductCount(currentProduct.basketCount)
    }

    private fun updateBasketProduct() {
        thread {
            basketRepository.overWriteUpdate(
                BasketProduct(
                    count = Count(currentProduct.basketCount),
                    product = currentProduct.toDomain()
                )
            )
            view.showBasket()
        }
    }

    override fun selectPreviousProduct() {
        currentProduct = previousProduct ?: throw IllegalStateException(NO_PREVIOUS_PRODUCT_ERROR)
        previousProduct = null
        view.updateBindingData(currentProduct, previousProduct)
    }

    companion object {
        private const val NO_PREVIOUS_PRODUCT_ERROR = "이전 아이템이 없는데 접근하고 있습니다."
    }
}
