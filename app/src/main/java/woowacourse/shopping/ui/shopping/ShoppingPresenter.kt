package woowacourse.shopping.ui.shopping

import woowacourse.shopping.domain.repository.DomainProductRepository
import woowacourse.shopping.domain.repository.DomainRecentProductRepository
import woowacourse.shopping.ui.mapper.toDomain
import woowacourse.shopping.ui.mapper.toUi
import woowacourse.shopping.ui.model.UiProduct
import kotlin.concurrent.thread

class ShoppingPresenter(
    override val view: ShoppingContract.View,
    private val productRepository: DomainProductRepository,
    private val recentProductRepository: DomainRecentProductRepository
) : ShoppingContract.Presenter {
    override fun fetchProducts() {
        val products =
            productRepository.getPartially(TOTAL_LOAD_PRODUCT_SIZE_AT_ONCE).map { it.toUi() }
        view.updateProducts(products)
        view.updateMoreButtonVisibility(checkHasNext(products))
    }

    private fun checkHasNext(products: List<UiProduct>): Boolean =
        products.size == TOTAL_LOAD_PRODUCT_SIZE_AT_ONCE

    override fun fetchRecentProducts() {
        view.updateRecentProducts(
            recentProductRepository.getPartially(RECENT_PRODUCT_SIZE).map { it.toUi() }
        )
    }

    override fun inquiryRecentProduct(product: UiProduct) {
        view.showProductDetail(product)
        thread { recentProductRepository.add(product.toDomain()) }
    }

    companion object {
        private const val RECENT_PRODUCT_SIZE = 10
        private const val LOAD_PRODUCT_SIZE_AT_ONCE = 20
        private const val PRODUCT_SIZE_FOR_HAS_NEXT = 1
        private const val TOTAL_LOAD_PRODUCT_SIZE_AT_ONCE =
            LOAD_PRODUCT_SIZE_AT_ONCE + PRODUCT_SIZE_FOR_HAS_NEXT
    }
}
